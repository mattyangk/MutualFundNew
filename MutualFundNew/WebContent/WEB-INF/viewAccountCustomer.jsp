<%@ page import="databeans.CustomerBean"%>
<%@ page import="databeans.CustomerFundsInfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp" />


<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Customer Account Information</h1>
	<jsp:include page="error.jsp" />

	<c:if test="${not empty customer}">
		<table class="table">

			<tr>
				<td>User ID:</td>
				<td>${customer.customer_id}</td>
			</tr>
			<tr>
				<td>User Name:</td>
				<td>${customer.username}</td>

			</tr>
			<tr>
				<td>First Name:</td>
				<td>${customer.firstname}</td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td>${customer.lastname}</td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><br>${customer.addr_line1}<br>
					${customer.addr_line2 }</td>

			</tr>
			<tr>
				<td>City:</td>
				<td>${customer.city}</td>
			</tr>
			<tr>
				<td>State:</td>
				<td>${customer.state}</td>
			</tr>
			<tr>
				<td>Zip:</td>
				<td>${customer.zip}</td>
			</tr>

			<tr>
				<td>Cash:</td>
				<td><fmt:formatNumber value="${customer.cash/100}"
						type="currency" /></td>
			</tr>
			<tr>
				<td>Available Balance:</td>
				<td><fmt:formatNumber value="${customer.balance/100}"
						type="currency" /></td>
			</tr>
			<tr>
				<td>Last Trading Date:</td>
				<c:choose>
					<c:when test="${empty latestDay}">
						<td>Not Available</td>
					</c:when>
					<c:otherwise>
						<td>${latestDay}</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table>


		<table class="table">

			<thead>

				<tr>
					<td>Fund List</td>
			</thead>
			<c:choose>
				<c:when test="${empty fundInfo}">
					<tr>
						<td>You don't have any fund now.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tbody>

						<tr>

							<th>Fund Name</th>

							<th>Ticker</th>

							<th style="text-align: right">Shares</th>

							<th style="text-align: right">Available Shares</th>
							<th style="text-align: right">Latest Price($)</th>
							<th style="text-align: right">Total Value($)</th>
						<tr>


							<c:forEach items="${fundInfo}" var="fund">

								<tr>

									<td>${fund.fund_name}</td>

									<td>${fund.fund_symbol}</td>

									<td align="right"><fmt:formatNumber
											value="${fund.shares/1000}" type="number"
											maxFractionDigits="3" minFractionDigits="3" /></td>
									<td align="right"><fmt:formatNumber
											value="${fund.available_shares/1000}" type="number"
											maxFractionDigits="3" minFractionDigits="3" /></td>
									<td align="right"><fmt:formatNumber
											value="${fund.price/100}" type="number" maxFractionDigits="2"
											minFractionDigits="2" /></td>
									<td align="right"><fmt:formatNumber
											value="${fund.total/100}" type="number" maxFractionDigits="2"
											minFractionDigits="2" /></td>

								</tr>

							</c:forEach>
					</tbody>
				</c:otherwise>
			</c:choose>

		</table>
	</c:if>


</div>