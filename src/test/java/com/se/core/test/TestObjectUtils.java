package com.ufo.core.test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.ufo.core.utils.DateUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ufo.core.utils.MockEntityUtils;

/**
 * 基于反射的用于生成测试Entity对象示例的辅助工具类
 */
public class TestObjectUtils {

    public static <X> X buildMockObject(Class<X> clazz) {
        return MockEntityUtils.buildMockObject(clazz);
    }

    public static class TestVO {
        private String str;
        private Date dt;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public Date getDt() {
            return dt;
        }

        public void setDt(Date dt) {
            this.dt = dt;
        }
    }

    public static void main(String[] args) {
        /*TestVO testVO = TestObjectUtils.buildMockObject(TestVO.class);
        System.out.println("Mock Entity: " + ReflectionToStringBuilder.toString(testVO, ToStringStyle.MULTI_LINE_STYLE));*/
        /*String abc="4,2";
        String[] arr= abc.split(",");
        List list = Arrays.asList(arr);
        System.out.println("sadfasdf:"+list.toString());*/
        System.out.println(DateUtils.compareDate("2014-10-12","2014-11-15"));
    }
}
