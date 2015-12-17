package com.ufo.core.web.converter;

import java.beans.PropertyEditorSupport;
import java.sql.Time;

import org.apache.commons.lang.StringUtils;

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
public class TimePropertyEdit extends PropertyEditorSupport {

    private String format = DateUtils.FT_SORT_TIME;

    public void setFormat(String format) {
        this.format = format;
    }

    public void setAsText(String value) {
        try {
            if (StringUtils.isNotBlank(value)) {
                setFormat(value.length() > 7 ? DateUtils.FT_TIME : DateUtils.FT_SORT_TIME);
                setValue(DateUtils.toTime(value, format));
            }
        } catch (Exception e) {
            setValue(null);
        }
    }

    public String getAsText() {
        return DateUtils.format((Time) getValue(), format);
    }
}
