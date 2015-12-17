package com.ufo.core.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.ufo.core.service.IService;

/** 
* 类名称：CommonController 
* 类描述： 提供如,验证码
* 
* 创建人：Duzj
* 创建时间：2012-9-7 下午4:57:51 
* @version 
* 
*/
@SuppressWarnings("rawtypes")
@Controller("_commonController")
public class CommonController extends AbstractController {
    //随机小写字母+数字 
    private final static char[] randomChars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'z', 'x',
            'c', 'v', 'b', 'n', 'm', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'q', 'w', 'e', 'r', 't', 'y', 'u',
            'i', 'o', 'p' };
    /** 
    *DEFAULTVAL_VALIDATECODE 默认session中验证码保存的key
    */
    private static final String DEFAULTVAL_VALIDATECODE = "validateCode";
    private Random random = new Random();

    private Color getRandColor(int fc, int bc) {//给定范围获得随机颜色  
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /** 
     * 参数:t=验证码类型(1-数字,2-小写字母,3大写字母,4大小写字母,5字母数字) ;l=验证码长度;s=session中验证码保存的key;w=生成图片宽度;h=生成图片高度
     * 示例:
     * <img border="0"  src="${ctx}/validateCode.htm?t=1&l=4&s=validateCode&w=80&h=30" />
    * @throws IOException
    */
    @RequestMapping("/validateCode.htm")
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置不缓存图片  
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        //指定生成的相应图片  
        response.setContentType("image/jpeg");
        //验证码类型
        int type = ServletRequestUtils.getIntParameter(request, "t", 1);
        //随机编码 
        int length = ServletRequestUtils.getIntParameter(request, "l", 4);
        String code = random(type, length);
        RequestAttributes attr = RequestContextHolder.currentRequestAttributes();

        String sessionkey = ServletRequestUtils.getStringParameter(request, "s", DEFAULTVAL_VALIDATECODE);
        attr.setAttribute(sessionkey, code, RequestAttributes.SCOPE_SESSION);

        Integer width = ServletRequestUtils.getIntParameter(request, "w", 80);
        Integer height = ServletRequestUtils.getIntParameter(request, "h", 30);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = image.createGraphics();
        //绘制背景  
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        for (int i = 0; i < length * 10; i++) {
            g.setColor(getRandColor(0, 255));
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(length * 2);
            int y2 = random.nextInt(length * 2);
            g.drawLine(x1, y1, x1 + x2, y1 + y2);
        }
        //定义字体样式  
        int fontHeight = height - 2;//字体的高度  
        Font myFont = new Font("黑体", Font.BOLD, fontHeight);
        //设置字体  
        g.setFont(myFont);
        int x = 0, y = 0;
        x = width / (length);//每个字符的宽度  
        y = height - 4;
        for (int j = 0; j < length; j++) {
            char str = code.charAt(j);
            g.setColor(getRandColor(0, 255));
            g.drawString(String.valueOf(str), (j) * x, y);
        }
        g.dispose();
        ImageIO.write(image, "png", response.getOutputStream());
    }

    /** 
     * (1-数字,2-小写字母,3大写字母,4大小写字母,5字母数字,6小写字母数字 ,7中文)
    * @param type
    * @param length
    * @return
    */
    private static String random(int type, int length) {
        if (1 == type) {
            return RandomStringUtils.randomNumeric(length);
        } else if (2 == type) {
            return RandomStringUtils.random(length, (int) 'a', (int) 'z', true, true);
        } else if (3 == type) {
            return RandomStringUtils.random(length, (int) 'A', (int) 'Z', true, true);
        } else if (4 == type) {
            return RandomStringUtils.randomAlphabetic(length);
        } else if (5 == type) {
            return RandomStringUtils.randomAlphanumeric(length);
        } else if (6 == type) {
            return RandomStringUtils.random(length, randomChars);
        } else if (7 == type) {
            return RandomStringUtils.random(length, 19968, 40908, false, false);
        } else {
            return RandomStringUtils.random(length);
        }
    }

    public static void main(String[] args) {
        System.out.println((int) '一');
        System.out.println(0x9fcc);
        System.out.println(random(1, 30));
        System.out.println(random(2, 30));
        System.out.println(random(3, 30));
        System.out.println(random(4, 30));
        System.out.println(random(5, 30));
        System.out.println(random(6, 30));
        System.out.println(random(7, 16));
    }

    /** 
     * 参数: s=session中验证码保存的key
     * 示例:
     * $.get('${ctx}/getValidateCode.json?s=validateCode',function(result){
     *  //todo your code
     * });
    * @param s
    * @return
    */
    @RequestMapping("/getValidateCode.json")
    @ResponseBody
    public Object getValidateCode(String s) {
        String key = StringUtils.isBlank(s) ? DEFAULTVAL_VALIDATECODE : s;
        RequestAttributes attr = RequestContextHolder.currentRequestAttributes();
        Object obj = attr.getAttribute(key, RequestAttributes.SCOPE_SESSION);
        return JsonSuccess(obj);
    }

    @Override
    protected IService getEntityService() {
        // TODO Auto-generated method stub
        return null;
    }
}
