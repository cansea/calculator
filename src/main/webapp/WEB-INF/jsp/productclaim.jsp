<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
	<!-- Access the bootstrap Css like this, Spring boot will handle the resource mapping automcatically -->
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
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
					<li class="active"><a href="/">Product claim</a></li>
					<li><a href="/tiered/claim/calculator">Tiered claim</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="starter-template">
		  <h1>Product claim calculator</h1>
		  
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
			
			<table class="table table-bordered">
			  <thead>
			    <tr>
			      <th scope="col">Product Code</th>
			      <th scope="col">Discount Rate%</th>
			      <th scope="col">Formulae for claim calculation</th>
			      <th scope="col">Subtotal</th>
			    </tr>
			  </thead>
			  <tbody id="claimTableBoday">
			    <tr>
			      <td colspan="4">No record.</td>
			    </tr>
			  </tbody>
			</table>
		</div>
	</div>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
	    	claim.getProductSales();
	    });
	    
	    $('#dtEnd').change(function() {
	    	//console.log($(this).val());
	    	claim.getProductSales();
	    });
	    
	    function claim() { }
	    claim.getProductSales = function(){
	   		$.ajax( {  
		    	type : 'GET',
		    	async: false,
		        url : "/product/claim/calculator/sales",  
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
		        	claim.buildTable(msg.sales);
		        }
		    });
	    }
	    
	    claim.buildTable = function(sales){
			var salesHtml = '<!-- Sales -->';
			if(sales.length > 0){
	       		for(var i = 0;i < sales.length;i++){
	       			salesHtml = salesHtml + '<tr>';
	       			salesHtml = salesHtml + '  <th scope="row">' + sales[i].productCode + '<input type="hidden" id="saleAmount' + sales[i].productCode + '" value="' + sales[i].saleAmount + '"></th>';
	       			salesHtml = salesHtml + '  <td><input class="txtNumber" min="1" max="100" type="number" id="discount' + sales[i].productCode + '"></td>';
	       			salesHtml = salesHtml + '  <td>SaleAmount * Discount Rate/100</td>';
	       			salesHtml = salesHtml + '  <td><span id="subtotal' + sales[i].productCode + '">0</span></td>';
	       			salesHtml = salesHtml + '</tr>';
	       		}
	   			salesHtml = salesHtml + '<tr>';
	   			salesHtml = salesHtml + '  <td colspan="3"><b>Total Claim</b></td>';
	   			salesHtml = salesHtml + '  <td><span id="totalClaim">0</span></td>';
	   			salesHtml = salesHtml + '</tr>';
   			}else {
       			salesHtml = salesHtml + '<tr>';
			    salesHtml = salesHtml + '  <td colspan="4">No record.</td>';
			    salesHtml = salesHtml + '</tr>';
   			}
       		$("#claimTableBoday").html(salesHtml);
       		
       		$(".txtNumber").on('keyup keypress blur change', function(e){
       			var productCode = $(this).attr("id").replace("discount", "");
       			var subtotal = $("#saleAmount" + productCode).val() * $("#discount" + productCode).val() / 100;
       			var total = 0.00;
       			for(var i = 0;i < sales.length;i++)total = total + (sales[i].saleAmount * $("#discount" + sales[i].productCode).val() / 100);
       			$("#subtotal" + productCode).html("$" + subtotal.toFixed(2));
       			$("#totalClaim").html("<b>$" + total.toFixed(2) + "</b>");
       		});
	    }
	</script>
</body>
</html>