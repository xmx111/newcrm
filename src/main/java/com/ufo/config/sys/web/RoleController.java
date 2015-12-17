package com.ufo.config.sys.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ufo.config.sys.entity.Authority;
import com.ufo.config.sys.entity.Role;
import com.ufo.config.sys.service.interfaces.IAuthorityService;
import com.ufo.config.sys.service.interfaces.IRoleService;
import com.ufo.core.annotation.Description;
import com.ufo.core.dto.TreeDTO;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.utils.JsonUtils;
import com.ufo.core.utils.NumberUtils;
import com.ufo.core.utils.StringUtils;
import com.ufo.core.web.PersistableController;

@Controller("configSysRoleController")
@RequestMapping("/config/sys/role/")
@Description(code = "system.role", value = "角色设置")
@Secured("system.role")
public class RoleController extends PersistableController<Role, Integer> {
    
    /***
     * jsp file path
     */
    private static final String VIEWPATH = "/config/sys/role/";

    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private IAuthorityService authorityService;

    @Override
    public IBaseSpringDataService<Role, Integer> getEntityService() {
        return roleService;
    }
    
    @RequestMapping("index.htm")
    @Description("列表查询")
    @Secured("system.role.index")
    public String index(HttpServletRequest request) {
        return this.toView("role-index");
    }

    @RequestMapping("inputTabs.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "system.role.add", "system.role.edit" })
    public String inputTabs(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE)
            map.put("id", id);
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("role-inputTabs");
    }

    @RequestMapping("edit.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "system.role.add", "system.role.edit" })
    public String edit(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE){
            map.put("dto", detail(id));
            map.put("id", id);
        }
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("role-inputBasic");
    }
    
    @RequestMapping("findByPage.json")
    @Description("列表查询")
    @Secured("system.role.index")
    @ResponseBody
    public Object findByPage(HttpServletRequest request) {
        return super.findByPage(request);
    }
    
    @RequestMapping("update.json")
    @Description("编辑数据")
    @Secured("system.role.edit")
    @ResponseBody
    public Object update(Role dto) {
        return super.save(dto);
    }
    
    @RequestMapping("save.json")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "system.role.add", "system.role.edit" })
    @ResponseBody
    public Object save(Role dto) {
        return super.save(dto);
    }

    @RequestMapping("delete.json")
    @Description("删除数据")
    @Secured("system.role.delete")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        return super.delete(request);
    }
    
    @RequestMapping("buildValidateRules.json")
    @ResponseBody
    public Object buildValidateRules() {
        return super.buildValidateRules();
    }
    
    /** 
     * 重写方法 
     * @see com.ufo.core.web.AbstractBaseController#getModelPath() 
     */
     @Override
     protected String getModelViewPath() {
        
         return VIEWPATH;
     }
     
     @RequestMapping("/roleAuthorize.htm")
     @Secured({ "system.role.add", "system.role.edit" })
     public String authorize(Integer id, Boolean own, ModelMap map) {
         Role dto = new Role();
         Map<Integer, Authority> mapAuth = new HashMap<Integer, Authority>();
         if (NumberUtils.isNotEmpty(id)) {
             dto = roleService.findOne(id);
             for (Authority authority : authorityService.listByRole(dto)){
                 mapAuth.put(authority.getId(), authority);
             }
         }
         map.put("dto", dto);
         
         List<Authority> roots = new ArrayList<Authority>();
         List<Authority> list = new ArrayList<Authority>();
         Iterable<Authority> iterable = authorityService.findAll();
         
         if (iterable != null){
        	 Iterator<Authority> it = iterable.iterator();
             while (it.hasNext()){
            	 Authority auth = it.next();
                 Authority parent = auth.getParent();
                 if (parent == null || NumberUtils.isEmpty(parent.getId())) {
                     roots.add(auth);
                 }
            	 
             }
         }
         
         list.addAll(roots);
         Collections.sort(list, new Comparator<Authority>() {
             @Override
             public int compare(Authority o1, Authority o2) {
                 return o1.getId().compareTo(o2.getId());
             }
         });
         map.put("tree", JsonUtils.toJson(toTree(list, mapAuth)).replaceAll("\"pid\":", "\"pId\":"));
         return toView("role-authorize");
     }
     
     @RequestMapping("/setRoleAuthorize.json")
     @Secured({ "system.role.add", "system.role.edit" })
     @ResponseBody
     public Object setRoleAuthorize(Integer roleId, String authIds){
         Role role = this.getEntityService().findOne(roleId);
         if (authIds == null){
             return role;
         }
         role.getAuthorities().clear();
         for (Integer authId : StringUtils.splitToInteger(authIds, ",")){
             role.getAuthorities().add(authorityService.findOne(authId));
         }
         return this.getEntityService().save(role);
     }

     private List<TreeDTO> toTree(List<Authority> list, Map<Integer, Authority> map) {
    	 List<TreeDTO> tree = new ArrayList<TreeDTO>();
    	 for (Authority auth : list){
    		 TreeDTO dto = new TreeDTO();
    		 dto.setName(auth.getName());
    		 dto.setId(auth.getId());
    		 dto.setPId(auth.getParent() == null ? null : auth.getParent().getId());
    		 dto.setOpen(true);
    		 if (map.get(auth.getId()) != null)
    		         dto.setChecked(true);
    		 tree.add(dto);
    		 if (auth.getHasChildren()) {
                 tree.addAll(toTree(auth.getChildren(), map));
             }
    	 }
		 return tree;
     }
    
}
