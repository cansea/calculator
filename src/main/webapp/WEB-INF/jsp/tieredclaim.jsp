<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
	<!-- Access the bootstrap Css like this, Spring boot will handle the resource mapping automcatically -->
	<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css">
	

	<!-- 
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
	<c:url value="/css/main.css" var="jstlCss" />
	<link href="${jstlCss}" rel="stylesheet" />

</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Claim Calculator</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="/">Product claim</a></li>
					<li class="active"><a href="/tiered/claim/calculator">Tiered claim</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="starter-template">
			<h1>Tiered claim calculator</h1>
			<nav aria-label="breadcrumb">
			  <ol class="breadcrumb">
			    <li class="breadcrumb-item active" aria-current="page"><h4>Tiered discount rates</h4></li>
			  </ol>
			</nav>
			<table class="table table-bordered" style="margin-top: -20px;">
			  <thead>
			    <tr>
			      <th scope="col">Tier No</th>
			      <th scope="col">Min Value</th>
			      <th scope="col">Max Value</th>
			      <th scope="col">Discount rate</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach var="tier" items="${tiers}" varStatus="varstatus">
			    <tr>
			      <th scope="row">${tier.tierNo}</th>
			      <td>
				      <c:choose>
				      <c:when test="${tier.minValue == 0}">0</c:when>
				      <c:otherwise>$<fmt:formatNumber value="${tier.minValue}" pattern="#,#00.00#"/></c:otherwise>
				      </c:choose>
			      </td>
			      <td>
				      <c:if test="${!empty tier.maxValue}">$<fmt:formatNumber value="${tier.maxValue}" pattern="#,#00.00#"/></c:if>
			      </td>
			      <td><fmt:formatNumber value="${tier.discountRate * 100}" pattern="###.00#"/>%</td>
			    </tr>
			  	</c:forEach>
			  </tbody>
			</table>
			
			<nav aria-label="breadcrumb">
			  <ol class="breadcrumb">
			    <li class="breadcrumb-item active" aria-current="page"><h4>Tiered Claim</h4></li>
			  </ol>
			</nav>
			<div class="row">
			    <div class='col-sm-6'>
			      <div class="form-group">
			        <label>Start Date</label>
			        <div class='input-group date' id='datetimepicker1'>
			          <input type='text' class="form-control input-date" id="dtStart"/>
			          <span class="input-group-addon">
			            <span class="glyphicon glyphicon-calendar"></span>
			          </span>
			        </div>
			       </div>
			     </div>
			     <div class='col-sm-6'>
			       <div class="form-group">
			        <label>End Date</label>
			        <div class='input-group date' id='datetimepicker2'>
			          <input type='text' class="form-control input-date" id="dtEnd"/>
			          <span class="input-group-addon">
			            <span class="glyphicon glyphicon-calendar"></span>
			          </span>
			        </div>
			      </div>
			    </div>
			</div>
			<nav aria-label="breadcrumb">
			  <ol class="breadcrumb">
			    <li class="breadcrumb-item active" aria-current="page">Product Sales</li>
			  </ol>
			</nav>
			<table class="table table-bordered" style="margin-top: -20px;">
			  <thead>
			    <tr>
			      <th scope="col">Product Code</th>
			      <th scope="col">Transaction Date</th>
			      <th scope="col">SaleAmount</th>
			    </tr>
			  </thead>
			  <tbody id="salesTableBoday">
			    <tr>
			      <td colspan="3">No record.</td>
			    </tr>
			  </tbody>
			</table>
			
			<nav aria-label="breadcrumb">
			  <ol class="breadcrumb">
			    <li class="breadcrumb-item active" aria-current="page">Tiered Claim Results</li>
			  </ol>
			</nav>
			<table class="table table-bordered" style="margin-top: -20px;">
			  <thead>
			    <tr>
			      <th scope="col">Tier No</th>
			      <th scope="col">Tier Value</th>
			      <th scope="col">Tier Discount Rate</th>
			      <th scope="col">Tiered Claim Amount</th>
			    </tr>
			  </thead>
			  <tbody id="claimTableBoday">
			    <tr>
			      <td colspan="3"><b>Total Claim</b></td>
			      <td>0</td>
			    </tr>
			  </tbody>
			</table>
		</div>
	</div>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
	<script type="text/javascript" src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript">
		$(function() {
		  $('#datetimepicker1').datetimepicker({
	         format:'DD/MM/YYYY'
	      }).on('dp.change', function(e){ $('#dtStart').change();})
		  $('#datetimepicker2').datetimepicker({
	         format:'DD/MM/YYYY'
	      }).on('dp.change', function(e){ $('#dtEnd').change(); })
		});
		
	    $('#dtStart').change(function() {
	    	//console.log($(this).val());
	    	claim.getTieredSales();
	    	claim.getTieredClaim();
	    });
	    
	    $('#dtEnd').change(function() {
	    	//console.log($(this).val());
	    	claim.getTieredSales();
	    	claim.getTieredClaim();
	    });
	    
	    function claim() { }
	    claim.getTieredSales = function(){
	   		$.ajax( {  
		    	type : 'GET',
		    	async: false,
		        url : "/tiered/claim/calculator/sales",  
		        data: {
		        	startDate: $('#dtStart').val(),
		        	endDate: $('#dtEnd').val()
		        },
		        dataType:"json",
		        headers: {
		            "Accept" : "text/plain; charset=utf-8",
		            "Content-Type": "application/json; charset=utf-8"
		        },
		        success : function(msg) { 
		        	claim.buildSalesTable(msg.sales, msg.total);
		        }
		    });
	    }
	    
	    claim.getTieredClaim = function(){
	   		$.ajax( {  
		    	type : 'GET',
		    	async: false,
		        url : "/tiered/claim/calculator/calc",  
		        data: {
		        	startDate: $('#dtStart').val(),
		        	endDate: $('#dtEnd').val()
		        },
		        dataType:"json",
		        headers: {
		            "Accept" : "text/plain; charset=utf-8",
		            "Content-Type": "application/json; charset=utf-8"
		        },
		        success : function(msg) { 
		        	claim.buildClaimTable(msg.tiers, msg.claim);
		        }
		    });
	    }
	    
	    claim.buildSalesTable = function(sales, total){
			var salesHtml = '<!-- Sales -->';
       		if(sales.length > 0){
	       		for(var i = 0;i < sales.length;i++){
	       			salesHtml = salesHtml + '<tr>';
	       			salesHtml = salesHtml + '  <th scope="row">' + sales[i].productCode + '</th>';
	       			salesHtml = salesHtml + '  <td>' + sales[i].transactionDate + '</td>';
	       			salesHtml = salesHtml + '  <td>$' + sales[i].saleAmount.toFixed(2) + '</td>';
	       			salesHtml = salesHtml + '</tr>';
	       		}
	       		salesHtml = salesHtml + '<tr>';
			    salesHtml = salesHtml + '  <td colspan="2"><b>Total Amount</b></td>';
			    salesHtml = salesHtml + '  <td><b>$' + total.amount + '</b></td>';
			    salesHtml = salesHtml + '</tr>';
       		}else {
       			salesHtml = salesHtml + '<tr>';
			    salesHtml = salesHtml + '  <td colspan="3">No record.</td>';
			    salesHtml = salesHtml + '</tr>';
       		}
       		
       		$("#salesTableBoday").html(salesHtml);
	    }
	    
	    claim.buildClaimTable = function(tiers, claim){
			var claimHtml = '<!-- Tiered Claim -->';
       		for(var i = 0;i < tiers.length;i++){
       			claimHtml = claimHtml + '<tr>';
       			claimHtml = claimHtml + '  <th scope="row">' + tiers[i].tierNo + '</th>';
       			claimHtml = claimHtml + '  <td>$' + tiers[i].tierValue + '</td>';
       			claimHtml = claimHtml + '  <td>' + tiers[i].tierDiscountRate + '%</td>';
       			claimHtml = claimHtml + '  <td>$' + tiers[i].tieredClaimAmount + '</td>';
       			claimHtml = claimHtml + '</tr>';
       		}
       		claimHtml = claimHtml + '<tr>';
			claimHtml = claimHtml + '  <td colspan="3"><b>Total Claim</b></td>';
			claimHtml = claimHtml + '  <td><b>$' + claim.total + '</b></td>';
			claimHtml = claimHtml + '</tr>';
       		$("#claimTableBoday").html(claimHtml);
	    }
	</script>
</body>
</html>