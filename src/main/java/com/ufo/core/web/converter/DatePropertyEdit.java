package com.ufo.core.web.converter;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import com.ufo.core.utils.DateUtils;

/** 
* 类名称：DatePropertyEdit 
* 类描述： 自定绑定日期
* 
* 
* 创建人：Duzj
* 创建时间：2012-4-19 上午10:19:13 
* @version 
* 
*/
@SuppressWarnings("rawtypes")
public class DatePropertyEdit extends PropertyEditorSupport {

    private String format = DateUtils.FT_DATE;
    private Class cls = Date.class;

    public DatePropertyEdit(Class cls) {
        this.cls = cls;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setAsText(String value) {
        if (cls.equals(java.sql.Date.class)) {
            setValue(DateUtils.toDate(value, format));
        } else {
            setValue(DateUtils.parseToDate(value, format));
        }
    }

    public String getAsText() {
        return DateUtils.format((Date) getValue(), format);
    }
}
