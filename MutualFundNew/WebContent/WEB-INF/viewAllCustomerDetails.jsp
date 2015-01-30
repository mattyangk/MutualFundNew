<%@ page import="databeans.CustomerBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp" />

<script src="js/bootstrap-table.js"></script>
<link rel="stylesheet" href="css/bootstrap-table.css">

<script>
	$(document).ready(function() {
		$('table').bootstrapTable({

		});
	});

	function amountSorter(a, b) {
		
		a = + a.replace(/,/g, "");
		b = + b.replace(/,/g, "");
		
		re = /^\d+\.{0,1}(\d{1,3}){0,1}$/;
		if (!re.test(a)) 
			a = Number.MAX_VALUE;
		if (!re.test(b)) 
			b = Number.MAX_VALUE;
		
		if (a > b)
			return 1;
		if (a < b)
			return -1;
		return 0;

	}


</script>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">All Customers Account's Information</h1>
	<jsp:include page="error.jsp" />
	
	<table data-height="600" data-sort-name="name" data-sort-order="desc"
		data-search="true" data-pagination="true">

		<thead>
			<tr>
				<th data-align="center" data-sortable="true">User Name</th>
				<th data-align="center" data-sortable="true">Name</th>
				<th data-align="right" data-sorter="amountSorter" data-sortable="true">Cash($)</th>
			</tr>

		</thead>


		<tbody>

			<c:forEach var="customer" items="${customersList}">
				<tr>
					<td><a href="showCustomerInfo.do?customername=${customer.username}">${customer.username}</a></td>
					<td>${customer.firstname} ${customer.lastname}</td>
					<td align="right"><fmt:formatNumber value="${customer.cash/100}"
					type="number" maxFractionDigits="2" minFractionDigits="2"/></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</div>