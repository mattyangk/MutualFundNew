<%@page import="util.ConvertUtil"%>
<%@ page import="databeans.PositionAndFundBean"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	PositionAndFundBean[] funds = (PositionAndFundBean[]) request
			.getAttribute("positionAndFunds");
	DecimalFormat df = new DecimalFormat("#,##0.000");
	DecimalFormat df2 = new DecimalFormat("#,##0.00");
%>
<jsp:include page="header.jsp" />
<script src="js/validate.js"></script>


<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Sell Fund</h1>
	<jsp:include page="error.jsp" />

	<script>

	<%if (funds != null && funds.length > 0) {%>
	$(document).ready ( function () {
		
		$("#fund_id").text("<%=funds[0].getFund_id()%>");
        $("#symbol").text("<%=funds[0].getSymbol()%>");
		$("#shares").text("<%=df.format(ConvertUtil
						.convertShareLongToDouble(funds[0].getShares()))%>");
		$("#ashares").text("<%=df.format(ConvertUtil
						.convertShareLongToDouble(funds[0]
								.getAvailable_shares()))%>");
		$("#price").text("<%=df2.format(ConvertUtil
						.convertAmountLongToDouble(funds[0].getPrice()))%>");
		
		
		$("select").change(function() {
			var str = "";
			
			$("select option:selected").each(function() {
				str += $(this).text() + " ";
			});
			
			<%for (int i = 0; i < funds.length; i++) {%>
				var name = "<%=funds[i].getName()%>";
				if(str.trim() == name.trim()){
					//alert (str);
					$("#fund_id").text("<%=funds[i].getFund_id()%>");
			        $("#symbol").text("<%=funds[i].getSymbol()%>");
			        $("#shares").text("<%=df.format(ConvertUtil
							.convertShareLongToDouble(funds[i].getShares()))%>");
					$("#ashares").text("<%=df.format(ConvertUtil
							.convertShareLongToDouble(funds[i]
									.getAvailable_shares()))%>");
					$("#price").text("<%=df2.format(ConvertUtil
							.convertAmountLongToDouble(funds[i].getPrice()))%>");
												}
	<%}%>
		});
						});
	<%}%>
		
	</script>

	<p class="shareInputFeedback" style="color: red"></p>

	<c:if test="${not empty positionAndFunds}">

		<form method="POST" action="sellFund.do">
			<table class="table">
				<tr>
					<td>Available Balance</td>
					<td><fmt:formatNumber value="${customer.balance / 100}"
							type="currency" /></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>

				<tr>
					<td>Fund Name</td>
					<td>Fund ID</td>
					<td>Ticker</td>
					<td>Shares</td>
					<td>Available Shares</td>
					<td>Latest Price</td>
					<td>Shares For Sale</td>
				</tr>
				<tr>
					<td><select id="select" name="fundname">
							<c:choose>
								<c:when test="${not empty positionAndFunds }">
									<c:forEach var="sell" items="${positionAndFunds}">
										<option value="${sell.name}">${sell.name}</option>
									</c:forEach>
								</c:when>
							</c:choose>
					</select></td>
					<td id="fund_id"></td>
					<td id="symbol"></td>
					<td id="shares"></td>
					<td id="ashares"></td>
					<td id="price"></td>

					<td align="left"><input type="text" name="share"
						class="form-control" style="width: 100px"
						value="${form.share}" /></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td colspan="2" align="left"><input type="submit"
						name="button" class="btn btn-success" value="Sell Shares" /></td>
					
				</tr>

			</table>
		</form>

	</c:if>
</div>
