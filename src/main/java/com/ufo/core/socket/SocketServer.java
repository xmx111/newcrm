package com.ufo.core.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.Server;

public abstract class SocketServer implements IConnectHandler, IDataHandler, IDisconnectHandler {
    private static final String encoding = "utf-8";
    private Map<String, Long> sizeMap = new HashMap<String, Long>(5);
    private Map<String, StringBuffer> contentmap = new HashMap<String, StringBuffer>(5);
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    @Override
    public boolean onConnect(INonBlockingConnection nbc) throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
        InetAddress address = nbc.getRemoteAddress();
        if (logger.isInfoEnabled()) {
            String msg = nbc.getId() + "\t" + address.getHostAddress() + "\t" + nbc.getRemotePort();
            logger.info("connectioned===" + msg);
        }
        return true;
    }

    @Override
    public boolean onData(INonBlockingConnection nbc) throws IOException, BufferUnderflowException, ClosedChannelException,
            MaxReadSizeExceededException {
        String id = nbc.getId();
        Long total = sizeMap.get(id);
        total = total == null ? 0 : total;
        StringBuffer buf = contentmap.get(id);
        if (buf == null) {
            buf = new StringBuffer();
            contentmap.put(id, buf);
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int length = 0;
        while (nbc.read(buffer) > 0) {
            buffer.flip();
            length += buffer.limit();
            final String str = new String(buffer.array(), 0, buffer.limit(), encoding);
            buf.append(str);
        }
        total += length;
        sizeMap.put(id, total);
        if (logger.isInfoEnabled()) {
            logger.info(id + " 接收数所长度为:" + total);
        }
        handleClientRequest(nbc, buf.toString());
        return true;
    }

    /** 
     * 向客户端端写入数据
    * @param content
     * @return 
    * @throws IOException
    */
    protected void write(String content, INonBlockingConnection nbc) throws IOException {
        if (StringUtils.isBlank(content)) {
            return;
        }
        if (!nbc.isOpen() || content == null) {
            return;
        }
        ByteBuffer src = ByteBuffer.wrap(content.getBytes(encoding));
        if (logger.isInfoEnabled()) {
            logger.info("send byte length is: " + src.limit());
            logger.info("send char length is: " + content.length());
            logger.info("send msg is: " + new String(src.array(), 0, src.limit() > 100 ? 100 : src.limit(), encoding));
        }
        nbc.write(src);
        nbc.flush();
    }

    @Override
    public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
        String id = nbc.getId();
        if (logger.isInfoEnabled()) {
            logger.info("接收的数据总长度为:" + sizeMap.get(id));
            logger.info("disconnected === " + id);
        }
        sizeMap.remove(id);
        contentmap.remove(id);
        return true;
    }

    /** 
     * 处理客户端请求
    * @param nbc
    * @param header
    * @param content
    * @return
    */
    protected abstract Boolean handleClientRequest(INonBlockingConnection nbc, String content);

    /** 
     * test 
    * @param args
    * @throws IOException
    */
    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getLocalHost();
        Server server = new Server(address, 9999, new SocketServer() {
            @Override
            protected Boolean handleClientRequest(INonBlockingConnection nbc, String content) {
                System.out.println(content);
                return null;
            }
        });
        server.setFlushmode(org.xsocket.connection.IConnection.FlushMode.ASYNC);
        server.start();
        server.close();
    }
}
