package com.ufo.config.sys.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* 类名称：ProjectModelType 
* 类描述：项目模块 
* 
* 创建人：Duzj
* 创建时间：2012-12-22 上午9:28:27 
* @version 
* 
*/
public enum ProjectModel {
    
    CONFIG, //系统配置
    CALENDAR, //日历管理
	ITEM,//商品管理
	COURSEFOUL,//球场违约规则管理
	RIGHTS,//卡及价格权益管理
	MEMBER,//会员信息管理
    SUPPLIERS, //供应商管理
	ORDER, //订单管理
    MONEY;//钱账户管理
    ;
    private static Map<ProjectModel, Integer> typeMap = new HashMap<ProjectModel, Integer>();
    private static Map<Integer, ProjectModel> valueMap = new HashMap<Integer, ProjectModel>();
    private static Map<ProjectModel, String> nameMap = new HashMap<ProjectModel, String>();
    static {
        typeMap.put(ProjectModel.CONFIG, 1);
        typeMap.put(ProjectModel.CALENDAR, 2); //日历管理
        
        typeMap.put(ProjectModel.ITEM, 11);//商品配置
        typeMap.put(ProjectModel.COURSEFOUL, 12);//球场违约规则配置
        typeMap.put(ProjectModel.RIGHTS, 13);//权益配置
        typeMap.put(ProjectModel.MEMBER, 14);//会员管理
        typeMap.put(ProjectModel.SUPPLIERS, 15);//供应商管理
        typeMap.put(ProjectModel.ORDER, 16);//订单管理
        typeMap.put(ProjectModel.MONEY, 17);//订单管理
        

        nameMap.put(ProjectModel.CONFIG, "系统配置");
        nameMap.put(ProjectModel.CALENDAR, "日历管理");
        
        nameMap.put(ProjectModel.ITEM, "商品管理");
        nameMap.put(ProjectModel.COURSEFOUL, "违约规则管理");
        nameMap.put(ProjectModel.RIGHTS, "权益管理");
        nameMap.put(ProjectModel.MEMBER, "会员管理");
        nameMap.put(ProjectModel.SUPPLIERS, "供应商管理");
        nameMap.put(ProjectModel.ORDER, "订单管理");
        nameMap.put(ProjectModel.MONEY, "钱账户管理");

        for (ProjectModel state : typeMap.keySet()) {
            valueMap.put(typeMap.get(state), state);
        }
    }

    public Integer value() {
        return typeMap.get(this);
    }

    public static ProjectModel parse(int value) {
        if (!valueMap.containsKey(value)) {
            throw new IllegalArgumentException(value + " is not a valid CardStatus");
        }
        return valueMap.get(value);
    }

    public String toName() {
        return nameMap.get(this);
    }

    public Integer getValue() {
        return value();
    }

    public String getName() {
        return toName();
    }

    public static List<ProjectModel> list() {
        final ArrayList<ProjectModel> list = new ArrayList<ProjectModel>(typeMap.keySet());
        Collections.sort(list, new java.util.Comparator<ProjectModel>() {
            @Override
            public int compare(ProjectModel o1, ProjectModel o2) {
                return o1.value().compareTo(o2.value());
            }

        });
        return list;
    }
}
