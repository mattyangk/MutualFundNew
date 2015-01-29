<%@ page import="databeans.TransactionAndPriceBean"%>
<%@ page import="java.util.ArrayList"%>
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

	function shareSorter(a, b) {
		
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
	<h1 class="page-header">View Transaction History</h1>
	<jsp:include page="error.jsp" />
	
	<table data-height="600" data-sort-name="name" data-sort-order="desc"
		data-search="true" data-pagination="true">

		<thead>
			<tr>

				<th data-align="center" data-sortable="true">Date</th>
				<th data-align="center" data-sortable="true">Type</th>
				<th data-align="right" data-sortable="true"
					data-sorter="amountSorter">Amount($)</th>
				<th data-align="center" data-sortable="true">Fund</th>
				<th data-align="right" data-sortable="true"
					data-sorter="amountSorter">Price($)</th>
				<th data-align="right" data-sortable="true"
					data-sorter="shareSorter">Shares</th>
				<th data-align="center" data-sortable="true">Status</th>
			</tr>

		</thead>

		<tbody>

			<c:forEach var="transaction" items="${transactionsHistory}">
				<tr>
					<c:choose>
						<c:when
							test="${transaction.trasaction_type.equals('deposit') || transaction.trasaction_type.equals('request')}">
							<c:choose>
								<c:when test="${empty transaction.execute_date }">
									<td>(pending)</td>
								</c:when>
								<c:otherwise>
									<td><fmt:formatDate pattern="yyyy-MM-dd"
											value="${transaction.execute_date}" /></td>
								</c:otherwise>
							</c:choose>
							<td>${transaction.trasaction_type}</td>


							<c:if test="${(transaction.amount/100) ==0}">
								<td>--</td>
							</c:if>
							<c:if test="${(transaction.amount/100) !=0}">
								<td align="right"><fmt:formatNumber value="${transaction.amount/100}"
										type="number" maxFractionDigits="2" minFractionDigits="2" /></td>
							</c:if>

							<td>--</td>
							<td>--</td>
							<td>--</td>
							<c:if test="${transaction.is_success}">
								<td>Completed</td>
							</c:if>
							<c:if test="${!transaction.is_complete}">
								<td>Pending</td>
							</c:if>
							<c:if
								test="${transaction.is_complete && !transaction.is_success}">
								<td>Failed</td>
							</c:if>
						</c:when>
						
						<c:otherwise>

							<c:choose>
								<c:when test="${empty transaction.execute_date }">
									<td>(pending)</td>
								</c:when>
								<c:otherwise>
									<td><fmt:formatDate pattern="yyyy-MM-dd"
											value="${transaction.execute_date}" /></td>
								</c:otherwise>
							</c:choose>

							<td>${transaction.trasaction_type}</td>

							<c:choose>
								<c:when
									test="${!transaction.is_complete && transaction.trasaction_type.equals('sell')}">
									<td>(pending)</td>
								</c:when>
								<c:when
									test="${transaction.is_complete && !transaction.is_success && transaction.trasaction_type.equals('sell')}">
									<td>(failed)</td>
								</c:when>
								<c:otherwise>
									<td align="right"><fmt:formatNumber value="${transaction.amount/100}"
											type="number" maxFractionDigits="2" minFractionDigits="2" /></td>
								</c:otherwise>
							</c:choose>

							<td>${transaction.fund_name}</td>
							<c:if test="${transaction.price==0 }">
							<td>N/A</td>
							</c:if>
							<c:if test="${transaction.price!=0 }">
							<td align="right"><fmt:formatNumber value="${transaction.price/100}"
									type="number" maxFractionDigits="2" minFractionDigits="2" /></td>
							</c:if>

							<c:choose>
								<c:when
									test="${!transaction.is_complete && transaction.trasaction_type.equals('buy') }">
									<td>(pending)</td>
								</c:when>
								<c:when
									test="${transaction.is_complete && !transaction.is_success && transaction.trasaction_type.equals('buy') }">
									<td>(failed)</td>
								</c:when>
								<c:otherwise>
									<td align="right"><fmt:formatNumber value="${transaction.shares/1000}"
											type="number" maxFractionDigits="3" minFractionDigits="3" /></td>
								</c:otherwise>
							</c:choose>

							
							<c:if test="${transaction.is_success}">
								<td>Completed</td>
							</c:if>
							<c:if test="${!transaction.is_complete}">
								<td>Pending</td>
							</c:if>
							<c:if
								test="${transaction.is_complete && !transaction.is_success}">
								<td>Failed</td>
							</c:if>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</tbody>

	</table>
</div>