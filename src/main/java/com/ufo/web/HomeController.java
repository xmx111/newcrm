package com.ufo.web;


import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ufo.config.sys.entity.Manager;
import com.ufo.config.sys.service.impl.ManagerService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ufo.common.data.init.InitDataServiceHelper;
import com.ufo.config.sys.service.interfaces.IRoleService;
import com.ufo.core.cache.EnumsCache;
import com.ufo.core.service.IService;
import com.ufo.core.utils.DateUtils;
import com.ufo.core.utils.RequestUtils;
import com.ufo.core.web.AbstractController;

@SuppressWarnings("rawtypes")
@Controller("homeController")
@RequestMapping("/")
public class HomeController extends AbstractController {
	
    @Autowired
    private IRoleService roleService;
	
    @Autowired
    private InitDataServiceHelper initDataServiceHelper;

    @Override
    protected IService getEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping("index.htm")
    public String index(HttpServletRequest request, ModelMap map) {
        return redirect("/main/index.htm");
    }

    @RequestMapping("login.htm")
    public String login(String redirect, HttpServletRequest request, Boolean error, ModelMap map) {
        if (error != null && error){
            if (request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null){
                AuthenticationException e = (AuthenticationException)request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                map.put("error", e.getMessage());
            }
        }
        map.put("redirect", redirect);
        return toView("login");
    }

    @RequestMapping("500.htm")
    public String _500(ModelMap map) {
        return toView("500");
    }
 
    @RequestMapping("accessDenied.htm")
    public String accessDenied(ModelMap map) {
        map.put("redirect", RequestUtils.removeAttribute("redirect"));
        map.put("message", RequestUtils.removeAttribute("message"));
        return toView("accessDenied");
    }

    @RequestMapping("server/date.json")
    @ResponseBody
    public String date() {
        return DateUtils.format(new Date(), DateUtils.FT_LONG_DATE_TIME);
    }
    
    @RequestMapping("loading.htm")
    public String loading(ModelMap map) {
        return toView("loading");
    }
    
    @RequestMapping("changepwd.htm")
    public String changepwd(String redirect, ModelMap map) {
       //map.put("redirect", redirect);
        return toView("changepwd");
    }
    
    @RequestMapping("pub/data/enums.json")
    @ResponseBody
    public Object enums(){
        String ret = "{";
        for (String key : EnumsCache.getTokenRepository().getAll().keySet()){
            if (key.equals("booleanLabel"))
                continue;
            ret += "\"" + key + "\":{";
            Map<? extends Serializable, String> map = EnumsCache.getTokenRepository().getEnums(key);

            List<Enum> list = new ArrayList<Enum>((Set<Enum>)map.keySet());
            Collections.sort(list, new Comparator<Enum>() {
                @Override
                public int compare(Enum o1, Enum o2) {
                    if (o1.ordinal() < o2.ordinal())
                        return -1;
                    else if (o1.ordinal() > o2.ordinal())
                        return 1;
                    else
                        return 0;
                }
            });
            for (Enum enumKey : list){
//                enumDataMap.put(enumObj, enumDataMapTmp.get(enumObj));
//            }
//
//            for (Object enumKey : map.keySet()){
//                if (enumKey instanceof Enum){
                    ret += "\"" + ((Enum)enumKey).name() + "\":\"" + map.get(enumKey) + "\",";
//                }
            }
            ret = ret.substring(0, ret.length() - 1) + "},";
        }
        ret = ret.substring(0, ret.length() - 1) + "}";
        return ret;
    }
    
    @RequestMapping("pub/grid/export.exp")
    @ResponseBody
    public Object export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filename = request.getParameter("fileName");
        filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        String exportDatas = request.getParameter("exportDatas");
        OutputStream os = response.getOutputStream();

        HSSFWorkbook wb = new HSSFWorkbook();//创建Excel工作簿对象   
        HSSFSheet sheet = wb.createSheet(filename);//创建Excel工作表对象     
        String[] rows = exportDatas.split("\n");
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            if (StringUtils.isNotBlank(row)) {
                logger.trace("Row {}: {}", i, row);
                // 创建Excel的sheet的一行
                HSSFRow hssfRow = sheet.createRow(i);
                String[] cells = row.split("\t");
                for (int j = 0; j < cells.length; j++) {
                    String cell = cells[j];
                    // 创建一个Excel的单元格
                    HSSFCell hssfCell = hssfRow.createCell(j);
                    hssfCell.setCellValue(cell);
                }
            }
        }
        wb.write(os);
        IOUtils.closeQuietly(os);
        return null;
    }

    @RequestMapping("init/auth.json")
    @ResponseBody
    public Object initAuth(){
        initDataServiceHelper.initAuthority(roleService.findOne(1));
        return true;
    }
}