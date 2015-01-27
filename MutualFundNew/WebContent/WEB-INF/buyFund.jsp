<%@ page import="databeans.FundPriceDetailBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<%@ page import="java.text.DecimalFormat" %>
<%@page import="util.ConvertUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	FundPriceDetailBean[] funds = (FundPriceDetailBean[]) request
			.getAttribute("funds");
	DecimalFormat df = new DecimalFormat("0.00");
%>

<script src="js/validate.js"></script>

<script>
<%
	if (funds != null && funds.length > 0) {
%>
$(document).ready ( function () {
	
	
	$("#price").text("<%=df.format(ConvertUtil.convertAmountLongToDouble(funds[0].getPrice()))%>");

	
	$("select").change(function() {
		var str = "";
		
		$("select option:selected").each(function() {
			str += $(this).text() + " ";
		});
		
		<%
		for (int i = 0; i < funds.length; i++) {
		%>
			var name = "<%=funds[i].getName()%>";
			if(str.trim() == name.trim()){
				//alert (str);
				
		        $("#price").text("<%=df.format(ConvertUtil.convertAmountLongToDouble(funds[i].getPrice()))%>");
		        
			}
		<% 
			}
		%>
		
	});
	}
);

<%
	}
%>


</script>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Buy Fund</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />

	<p class="amountInputFeedback" style="color: red"></p>
	<form method="POST" action="buyFund.do">
		<table class="table">
			<tr>
				<td>Fund Name</td>

				<td><select id="select" name="fundname">
						<%
							
							if (funds != null) {
								for (FundPriceDetailBean fund : funds) {
						%>
						<option value="<%=fund.getName()%>"><%=fund.getName()%>_<%=fund.getSymbol()%></option>
						<%
							}
							}
						%>
				</select></td>
			</tr>
			
			<tr>
				<td>Unit Price</td>
				<td id="price"></td>
			</tr>
			
			<tr>
				<td>Balance</td>
				<td><fmt:formatNumber value="${customer.balance}"
						type="currency" /></td>
			</tr>

			<tr>
			<td>Amount</td>
			<td><input type="text" name="amount" class="form-control"
				value="" style="width: 20%" /></td>
			</tr>

			<tr>
				<td colspan="2" align="left"><input type="submit" name="button"
					class="btn btn-success" value="Buy Fund" /></td>
			</tr>
		</table>
	</form>
</div>
