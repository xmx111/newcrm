package com.ufo.core.web.converter;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
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
public class TimestampPropertyEdit extends PropertyEditorSupport {

    private String format = DateUtils.FT_DATE_TIME;
    private Class cls = Timestamp.class;

    public TimestampPropertyEdit(Class cls) {
        this.cls = cls;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setAsText(String value) {
        if (cls.equals(java.sql.Timestamp.class)) {
            setValue(DateUtils.toDateTime(value, format));
        } else {
            setValue(DateUtils.parseToTimestamp(value, format));
        }
    }

    public String getAsText() {
        return DateUtils.format((Timestamp) getValue(), format);
    }
}
