<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<html>
<head>
    <title>${systemTitle}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="${ctx}/resources/css/index.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery.cookie.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            /* $('a').click(function(){
             $.removeCookie("JSESSIONID",{path:"/${ctx}"});
             }); */
        });
        function test(){
            alert('正在努力的开发中.....请稍候查看!');
        }

    </script>
</head>
<body>
<div class="wrapper">
    <div class="head">
        <img style="margin-top: 15px" src="${ctx}/resources/images/logos/icon--2.png" >
    </div>
    <div class="content">
        <div class="content_left">
            <li class="icon">
                <a href="${ucenter}" target="_blank" >
                    <img src="${ctx}/resources/images/logos/icon--4.png">
                </a>
            </li>
            <li class="icon">
                <a href="${procurement}" target="_blank" >
                    <img src="${ctx}/resources/images/logos/icon--5.png">
                </a>
            </li>
            <li class="icon">
                <a href="${sales}" target="_blank" >
                    <img src="${ctx}/resources/images/logos/icon--6.png">
                </a>
            </li>
            <li class="icon">
                <a href="${orderproces}" target="_blank" >
                    <img src="${ctx}/resources/images/logos/icon--7.png">
                </a>
            </li>
            <li class="icon">
                <a href="${settle}" target="_blank" >
                    <img src="${ctx}/resources/images/logos/icon--8.png">
                </a>
            </li>
        </div>
        <div class="content_right"></div>
        <div class="font"><img src="${ctx}/resources/images/login/foot.png"/><span>湖南体运通信息技术有限公司 Copyright © 2011 HUNAN SPORTSEXP INFORMATION TECHNOLOGY CO.LTD.</span></div>
    </div>
</div>
</body>
</html>