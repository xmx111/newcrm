package com.ufo.core.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;

import com.ufo.core.common.Paginator;
import com.ufo.core.common.WebServiceResult;

public abstract class AbstractWebService extends AbstractBaseController {
    protected static final String DEFAULT_MEMBER_CODE = "nologin";
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Object success(Object data, Paginator paginator) {
        return JsonSuccess(data, paginator);
    }

    protected WebServiceResult success(Object data) {
        return JsonSuccess(data);
    }

    protected WebServiceResult failed(Exception ex) {
        return JsonFailed(ex);
    }

    protected WebServiceResult result(Object data, String msg) {
        return JsonResult(data, msg);
    }

    protected String memberCode(HttpServletRequest req) {
        return ServletRequestUtils.getStringParameter(req, "memberCode", DEFAULT_MEMBER_CODE);
    }
}
