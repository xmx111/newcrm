package com.ufo.common.data.init;

import java.util.ArrayList;
import java.util.List;

import com.ufo.config.sys.entity.Authority;
import com.ufo.config.sys.entity.Authority.AuthorityTypeEnum;
import com.ufo.config.sys.entity.Authority.SysTypeEnum;

public class AuthorityResources {

    public static List<Authority> getAll(){
        List<Authority> list = createUCenter();
        list.addAll(createBasedata());
        list.addAll(createProject());
        list.addAll(createEnginee());
        list.addAll(createSystem());
        return list;
    }

    /**
     * 基础数据
     */
    public static List<Authority> createBasedata(){
        List<Authority> list = new ArrayList<Authority>();
        Authority basedata = getMenuAuthority("basedata", "基础数据", null, SysTypeEnum.BASEDATA);
        basedata.setSequence(1);
        
        Authority publicity = getMenuAuthority("basedata_publicity", "宣传资料管理", null, SysTypeEnum.BASEDATA_PUBLICITY);
        publicity.setSequence(1);
        setAuthorityChild(publicity, getCRUDAuthority("basedata_publicity.intro", "企业介绍", "basedata/publicity/edit.htm?type=0", SysTypeEnum.BASEDATA_PUBLICITY));
        setAuthorityChild(publicity, getCRUDAuthority("basedata_publicity.culture", "企业文化", "basedata/publicity/edit.htm?type=1", SysTypeEnum.BASEDATA_PUBLICITY));
        setAuthorityChild(publicity, getCRUDAuthority("basedata_publicity.resources", "企业资源库", "basedata/publicity/edit.htm?type=2", SysTypeEnum.BASEDATA_PUBLICITY));
        setAuthorityChild(publicity, getCRUDAuthority("basedata_publicity.news", "新闻资讯", "basedata/news/index.htm", SysTypeEnum.BASEDATA_PUBLICITY));
        
        setAuthorityChild(basedata, publicity);
        setAuthorityChild(basedata, getCRUDAuthority("basedata.region", "业务区域管理", "basedata/region/index.htm", SysTypeEnum.BASEDATA));
        setAuthorityChild(basedata, getCRUDAuthority("basedata.material", "材料基础信息管理", "basedata/material/index.htm", SysTypeEnum.BASEDATA));
        list.add(basedata);

        return list;
    }

    /**
     * 项目管理
     */
    public static List<Authority> createProject(){
        List<Authority> list = new ArrayList<Authority>();
        Authority project = getMenuAuthority("project", "项目管理", null, SysTypeEnum.PROJECT);
        project.setSequence(2);
        setAuthorityChild(project, getCRUDAuthority("project.contract", "装修合同信息管理", "project/contract/index.htm", SysTypeEnum.PROJECT));
        setAuthorityChild(project, getCRUDAuthority("project.engineering.engineeringinfo", "工程关联数据管理", "project/engineering/engineeringinfo/index.htm", SysTypeEnum.PROJECT));
        setAuthorityChild(project, getCRUDAuthority("project.drawing", "工程设计图纸管理", "project/drawing/index.htm", SysTypeEnum.PROJECT));
        setAuthorityChild(project, getCRUDAuthority("project.quote", "工程报价信息管理", "project/quote/index.htm", SysTypeEnum.PROJECT));
        list.add(project);

        return list;
    }

    /**
     * 工程管理
     */
    public static List<Authority> createEnginee(){
        List<Authority> list = new ArrayList<Authority>();
        Authority enginee = getMenuAuthority("engineering", "工程管理", null, SysTypeEnum.PROJECT);
        enginee.setSequence(3);
        setAuthorityChild(enginee, getCRUDAuthority("engineering.order", "工程材料信息管理", "engineering/order/index.htm", SysTypeEnum.PROJECT));
        Authority special = getMenuAuthority("engineering.special", "特殊信息采集数据", "engineering/special/index.htm", SysTypeEnum.PROJECT);
        setAuthorityChild(special, getNotMenuAuthority("engineering.special.index", "特殊信息采集数据[列表查询]", null, SysTypeEnum.PROJECT));
        setAuthorityChild(special, getNotMenuAuthority("engineering.special.delete", "特殊信息采集数据[删除数据]", null, SysTypeEnum.PROJECT));
        setAuthorityChild(special, getNotMenuAuthority("engineering.special.details", "特殊信息采集数据[查看详情]", null, SysTypeEnum.PROJECT));
        setAuthorityChild(enginee, special);
        list.add(enginee);

        return list;
    }

    /**
     * 用户中心
     */
    public static List<Authority> createUCenter(){
        List<Authority> list = new ArrayList<Authority>();
        Authority ucenter = getMenuAuthority("ucenter", "用户中心", null, SysTypeEnum.UCENTER);
        ucenter.setSequence(4);
        setAuthorityChild(ucenter, getCRUDAuthority("ucenter.supplier", "供货商信息管理", "ucenter/supplier/index.htm", SysTypeEnum.UCENTER));
        setAuthorityChild(ucenter, getCRUDAuthority("ucenter.employee", "员工基本信息管理", "ucenter/employee/index.htm", SysTypeEnum.UCENTER));
        setAuthorityChild(ucenter, getCRUDAuthority("ucenter.custom", "客户基本信息管理", "ucenter/custom/index.htm", SysTypeEnum.UCENTER));
        list.add(ucenter);

        return list;
    }

    /**
     * 系统管理
     */
    public static List<Authority> createSystem(){
        List<Authority> list = new ArrayList<Authority>();
        Authority system = getMenuAuthority("system", "系统管理", null, SysTypeEnum.SYSTEM);
        setAuthorityChild(system, getCRUDAuthority("system.role", "系统角色管理", "config/sys/role/index.htm", SysTypeEnum.SYSTEM));
        setAuthorityChild(system, getCRUDAuthority("system.authority", "权限配置管理", "config/sys/authority/index.htm", SysTypeEnum.SYSTEM));
        setAuthorityChild(system, getCRUDAuthority("system.manager", "用户管理", "config/sys/manager/index.htm", SysTypeEnum.SYSTEM));
        system.setSequence(5);
        list.add(system);

        return list;
    }

    private static void setAuthorityChild(Authority parent, Authority child){
        List<Authority> childs = parent.getChildren() != null ? parent.getChildren() : new ArrayList<Authority>();
        child.setParent(parent);
        child.setSequence(childs.size() + 1);
        childs.add(child);
        parent.setChildren(childs);
    }

    private static Authority getAuthority(String code, String name){
        Authority authority = new Authority();
        authority.setCode(code);
        authority.setName(name);
        return authority;
    }

    private static Authority getNotMenuAuthority(String code, String name, String url, SysTypeEnum sysType){
        Authority ret = new Authority();
        ret.setCode(code);
        ret.setName(name);
        ret.setUrl(url);
        ret.setSysType(sysType);
        ret.setType(AuthorityTypeEnum.AUTH);
        return ret;
    }


    private static Authority getMenuAuthority(String code, String name, String url, SysTypeEnum sysType){
        Authority authority = new Authority();
        authority.setCode(code);
        authority.setName(name);
        authority.setUrl(url);
        authority.setSysType(sysType);
        authority.setType(AuthorityTypeEnum.MENU);
        return authority;
    }

    private static Authority getCRUDAuthority(String code, String name, String url, SysTypeEnum sysType){
        Authority ret = getMenuAuthority(code, name, url, sysType);
        List<Authority> list = new ArrayList<Authority>();
        Authority authority = new Authority();
        authority.setCode(code + ".index");
        authority.setName("[" + name + "]列表查询");
        authority.setParent(ret);
        authority.setSequence(1);
        list.add(authority);
        authority = new Authority();
        authority.setCode(code + ".delete");
        authority.setName("[" + name + "]删除数据");
        authority.setParent(ret);
        authority.setSequence(2);
        list.add(authority);
        authority = new Authority();
        authority.setCode(code + ".edit");
        authority.setName("[" + name + "]编辑数据");
        authority.setParent(ret);
        authority.setSequence(3);
        list.add(authority);
        authority = new Authority();
        authority.setCode(code + ".add");
        authority.setName("[" + name + "]新增数据");
        authority.setParent(ret);
        authority.setSequence(4);
        list.add(authority);
        ret.setChildren(list);
        return ret;
    }

    private static Authority getNotCRUDAuthority(String code, String name, String url, SysTypeEnum sysType){
        Authority ret = getNotMenuAuthority(code, name, url, sysType);
        List<Authority> list = new ArrayList<Authority>();
        Authority authority = new Authority();
        authority.setCode(code + ".index");
        authority.setName("[" + name + "]列表查询");
        authority.setParent(ret);
        authority.setSequence(1);
        list.add(authority);
        authority = new Authority();
        authority.setCode(code + ".delete");
        authority.setName("[" + name + "]删除数据");
        authority.setParent(ret);
        authority.setSequence(2);
        list.add(authority);
        authority = new Authority();
        authority.setCode(code + ".edit");
        authority.setName("[" + name + "]编辑数据");
        authority.setParent(ret);
        authority.setSequence(3);
        list.add(authority);
        authority = new Authority();
        authority.setCode(code + ".add");
        authority.setName("[" + name + "]新增数据");
        authority.setParent(ret);
        authority.setSequence(4);
        list.add(authority);
        ret.setChildren(list);
        return ret;
    }

    public static List<String> getTelList(){
        List<String> list = new ArrayList<String>();
        list.add("member");
        list.add("member.member.registTabs");
        list.add("member.member");
        list.add("member.member.index");
        list.add("member.member.add");
        list.add("member.member.edit");
        list.add("member.member.delete");
        list.add("member.memberCard");
        list.add("member.memberCard.index");
        list.add("member.memberCard.add");
        list.add("member.memberCard.edit");
        list.add("member.memberCard.delete");
        list.add("member.memberContact");
        list.add("member.memberContact.index");
        list.add("member.memberContact.add");
        list.add("member.memberContact.edit");
        list.add("member.memberContact.delete");
        list.add("member.friend");
        list.add("member.friend.index");
        list.add("member.friend.add");
        list.add("member.friend.edit");
        list.add("member.friend.delete");
        list.add("order");
        list.add("order.index");
        list.add("order.add");
        list.add("order.edit");
        list.add("order.delete");
        list.add("order.coursesDetail");
        list.add("order.orderDetailIndex");
        list.add("order.detailOrder");
        list.add("order.saveCacelOrderFax");
        list.add("order.sendCancelOrderFax");
        list.add("member.call.index");
        list.add("order.customCenter.index");
        list.add("order.customCenter.add");
        list.add("order.customCenter.edit");
        list.add("order.customCenter.pay");
        list.add("order.customCenter.cancel");
        list.add("order.customCenter.staycancel");
        list.add("order.customCenter.query");
        list.add("order.customCenter.notEnd");

        list.add("member.mending.index");
        list.add("order.mending.index");
        list.add("order.mending.add");
        list.add("order.mending.query");
        return list;
    }

    public static List<String> getOrderList(){
        List<String> list = new ArrayList<String>();
        list.add("order");
        list.add("order.indexContact");
        list.add("order.indexStay");
        list.add("order.indexAlready");
        list.add("order.indexQuery");
        return list;
    }
}
