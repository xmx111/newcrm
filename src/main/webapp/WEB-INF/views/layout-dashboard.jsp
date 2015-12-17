<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<div class="row">
	<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">&nbsp;</div>
</div>
<div class="row">
	<sec:authorize ifAnyGranted="order.indexContact">
		<div class="col-md-12">
			<div class="portlet box blue tasks-widget" id="portlet-tasks">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-tasks"></i>待办任务<span
							class="badge badge-warning"></span>
					</div>
				</div>

				<div class="portlet-body">
					<div class="scroller"
						data-always-visible="1" data-rail-visible="0">

                        <table id="todo-table" class="table table-hover">
                            <thead>
                            <tr align="center">
                                <td width="10%">订单号</td>
                                <td width="14%">球场</td>
                                <td width="8%">会员姓名</td>
                                <td width="10%">下单时间</td>
                                <td width="10%">下场时间</td>
                                <td width="10%">订单状态</td>
                                <td width="10%">支付类型</td>
                                <td width="10%">支付状态</td>
                                <td width="8%">操作员</td>
                                <td width="10%">会员电话</td>
                            </tr>
                            </thead>
                            <tbody id="todo-tbody" style="text-align: center;">
                            </tbody>
                        </table>
					</div>
				</div>

				<div class="scroller-footer">
					<div class="pull-right">
						<a href="${ctx}/order/indexContact.htm"
							data-toggle="modal-ajaxify">查看全部... <i
							class="m-icon-swapright m-icon-gray"></i> </a> &nbsp;
					</div>
				</div>
			</div>
		</div>
	</sec:authorize>
</div>
<script type="text/javascript">
    $('.scroller').height($('.page-content').height() - 158);
    var rows = parseInt(($('.scroller').height() - 40) / 36);
    function refTodo(){
        var todoParam = "";
        <sec:authorize ifAnyGranted="order.indexContact">
        todoParam += "&orderStatus=2";
        </sec:authorize>
        <sec:authorize ifAnyGranted="order.indexStay">
        todoParam += "&orderStatus=3";
        </sec:authorize>
        <sec:authorize ifAnyGranted="order.indexAlready">
        todoParam += "&orderStatus=4";
        </sec:authorize>
        <sec:authorize ifAnyGranted="order.customCenter.cancel">
        todoParam += "&orderStatus=10";
        </sec:authorize>
        <sec:authorize ifAnyGranted="order.customCenter.pay">
        todoParam += "&payStatus=UNPAY&payStatus=PAYFAILED";
        </sec:authorize>
        if (todoParam != ""){
            $.ajax({
                type : 'POST',// 使用get方法访问后台
                dataType : 'json',// 返回json格式的数据
                url : '${ctx}/order/findToDoByPage.json?sidx=id&rows=' + rows + '&sord=desc'+ todoParam,
                success : function(msg) {// msg为返回的数据
                    if (!!msg.content){
                        $("#todo-tbody tr").remove();
                        for ( var i = 0; i < msg.content.length; i++) {
                            var id = msg.content[i].id;
                            var url = '${ctx}/order/orderDetailTodo.htm?id=' + id;
                            var str = "<tr><td><a  data-toggle=\"modal-ajaxify\" title='订单明细'  href="+url+">" + msg.content[i].orderNo
                                    + "</a></td><td><a  data-toggle=\"modal-ajaxify\" title='订单明细'  href="+url+">" + msg.content[i].courseName
                                    + "</a></td><td>" + msg.content[i].memberName
                                    + "</td><td>" + msg.content[i].createTime
                                    + "</td><td>" + msg.content[i].orderTime
                                    + "</td><td style='color: red'>" + msg.content[i].orderStatus.name
                                    + "</td><td style='color: red'>" + msg.content[i].payTypeName
                                    + "</td><td style='color: red'>" + msg.content[i].payStatusName
                                    + "</td><td>" + (!!msg.content[i].optNo ? "[" + msg.content[i].optNo + "]" : "") + msg.content[i].optName
                                    + "</td><td>" + msg.content[i].callNo + "</td></tr>";
                            $("#todo-tbody").append(str);
                        }
                    }
                }
            });
        }
    }

    refTodo();

    setInterval(refTodo, 10000);
</script>