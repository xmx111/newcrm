<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
Runtime lRuntime = Runtime.getRuntime();
out.println("*** BEGIN MEMORY STATISTICS ***<br/>");
out.println("Free  Memory: "+lRuntime.freeMemory()/1024/1024+"M<br/>");
out.println("Max   Memory: "+lRuntime.maxMemory()/1024/1024+"M<br/>");
out.println("Total Memory: "+lRuntime.totalMemory()/1024/1024+"M<br/>");
out.println("Available Processors : "+lRuntime.availableProcessors()+"<br/>");
out.println("*** END MEMORY STATISTICS ***");
%> 