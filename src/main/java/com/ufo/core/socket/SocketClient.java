package com.ufo.core.socket;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.charset.MalformedInputException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnection.FlushMode;
import org.xsocket.connection.IConnectionTimeoutHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.IIdleTimeoutHandler;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

public class SocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);
    /** 
    *host 主机地址
    */
    private String host;
    /** 
     * 端口
    *port 
    */
    private int port;
    /** 
     * 编码格式
     *encodIng 
     */
    private String encodIng = "utf-8";

    private IDataService dataService;

    private INonBlockingConnection nbc;

    public SocketClient() {
    }

    public SocketClient(String host, int port, IDataService dataService) throws IOException {
        this.host = host;
        this.port = port;
        this.dataService = dataService;
    }

    public IDataService getDataService() {
        return dataService;
    }

    public void setDataService(IDataService dataService) {
        this.dataService = dataService;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getEncodIng() {
        return encodIng;
    }

    public void setEncodIng(String encodIng) {
        this.encodIng = encodIng;
    }

    public void init() throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("connectioning ..." + host + ":" + port);
        }
        //新建连接
        nbc = new NonBlockingConnection(host, port, new Handler(dataService));
        //设置编码格式   
        nbc.setEncoding(encodIng);
        //设置同步
        nbc.setFlushmode(FlushMode.ASYNC);
        //设置是否自动清空缓存   
        nbc.setAutoflush(true);
        //60秒空闲超时
        nbc.setIdleTimeoutMillis(60 * 1000);
    }

    public class Handler implements IDataHandler, IDisconnectHandler, IConnectionTimeoutHandler, IIdleTimeoutHandler {
        private Set<Integer> verSet;
        private IDataService dataService;

        public Handler(IDataService dataService) {
            verSet = new HashSet<Integer>(5);
            this.dataService = dataService;
        }

        @Override
        public boolean onData(INonBlockingConnection nbc) throws IOException, BufferUnderflowException, ClosedChannelException,
                MaxReadSizeExceededException {
            int version = nbc.getReadBufferVersion();
            if (verSet.contains(version)) {
                return true;
            } else {
                verSet.add(version);
            }
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                StringBuffer buf = new StringBuffer();
                int length = 0;
                while (nbc.read(buffer) > 0) {
                    buffer.flip();
                    length += buffer.limit();
                    buf.append(new String(buffer.array(), 0, buffer.limit(), encodIng));
                    buffer.clear();
                }
                if (logger.isInfoEnabled()) {
                    logger.info("receive msg==" + buf.toString());
                }
                if (buf.length() != 0 && dataService != null) {
                    dataService.parse(nbc, buf.toString(), length);
                }
            } catch (MalformedInputException e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
            return true;
        }

        @Override
        public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
            this.verSet.clear();
            return true;
        }

        @Override
        public boolean onConnectionTimeout(INonBlockingConnection nbc) throws IOException {
            dataService.parse(nbc, "", 0);
            return false;
        }

        @Override
        public boolean onIdleTimeout(INonBlockingConnection nbc) throws IOException {
            dataService.parse(nbc, "", 0);
            return false;
        }

    }

    /** 
     * 向服务端写入数据
    * @param content
     * @return 
    * @throws IOException
    */
    public void write(String content) throws IOException {
        if (!nbc.isOpen() || content == null) {
            return;
        }
        ByteBuffer src = ByteBuffer.wrap(content.getBytes(encodIng));
        if (logger.isInfoEnabled()) {
            logger.info("send byte length is: " + src.limit());
            logger.info("send char length is: " + content.length());
            logger.info("send msg is: " + new String(src.array(), 0, src.limit(), encodIng));
        }
        nbc.write(src);
        nbc.flush();
    }

    /** 
    * 关闭连接
    */
    public void close() {
        try {
            if (nbc.isOpen()) {
                nbc.close();
                nbc = null;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String getConnectionInfo() {
        if (nbc != null) {
            return nbc.getRemoteAddress().getHostAddress() + ":" + nbc.getRemotePort();
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return "SocketClient [host=" + host + ", port=" + port + ", encodIng=" + encodIng + "]";
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketClient client = new SocketClient("localhost", 20000, new IDataService() {

            @Override
            public void parse(INonBlockingConnection nbc, String str, int length) {
                System.out.println(str);
            }

            @Override
            public boolean finsh() {
                return false;
            }
        });
        client.init();
        client.write("testffffffffffffffff   ");
        Thread.sleep(5000);
    }
}
