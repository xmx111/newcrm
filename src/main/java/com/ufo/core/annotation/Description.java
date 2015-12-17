/** 
* 文件名：Description.java 
* 
* 版本信息： 
* 日期：2012-2-5 
* Copyright  Corporation 2012 
* 版权所有 
* 
*/
package com.ufo.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* 类名称：Description 
* 类描述： 
* 
* 
* 创建人：Duzj
* 创建时间：2012-2-5 下午8:26:22 
* @version 
* 
*/
@Documented
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {

    public String[] value() default "";

    public String[] code() default "";
}
