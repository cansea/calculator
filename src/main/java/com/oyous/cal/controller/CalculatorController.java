package com.oyous.cal.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.oyous.cal.model.DiscountTier;
import com.oyous.cal.model.Sales;
import com.oyous.cal.service.DiscountTierManager;
import com.oyous.cal.service.SalesManager;

@Controller
public class CalculatorController {
	
	@Autowired
	private SalesManager salesManager;
	@Autowired
	private DiscountTierManager discountTierManager;
	
	@RequestMapping({"/","/product/claim/calculator"})
	public String productClaimCalculator(Map<String, Object> model) {
		return "productclaim";
	}
	
	@RequestMapping("/tiered/claim/calculator")
    public String tieredClaimCalculator(Map<String, Object> model) {
		List<DiscountTier> tiers = discountTierManager.getDiscountTiers();
		List<Sales> sales = salesManager.getTieredSales(null, null);
		
		model.put("sales", sales);
		model.put("tiers", tiers);
        return "tieredclaim";
    }

    @RequestMapping(path = "/product/claim/calculator/sales")
    public @ResponseBody String productClaimCalculatorSales(HttpServletRequest request) throws ParseException {
    	SimpleDateFormat sdfS = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	
    	String startDate = request.getParameter("startDate");
    	String endDate = request.getParameter("endDate");
    	if(startDate != null) {
	    	if(startDate.equals(""))startDate = null;
	    	else startDate = sdfS.format(df.parse(startDate));
    	}
    	if(endDate != null) {
	    	if(endDate.equals(""))endDate = null;
	    	else endDate = sdfS.format(df.parse(endDate));
    	}
    	
    	JsonObject json = new JsonObject();
    	JsonArray listJson = new JsonArray();
    	List<Sales> sales = salesManager.getProductSales(startDate,endDate);
    	if(sales != null && sales.size() > 0) {
    		sales.forEach(item->System.out.println(item.getProductDisplayCode() + " -- " + item.getSaleAmount()));
    		sales.forEach(item->{
    			JsonObject jso = new JsonObject();
    			jso.addProperty("productCode", item.getProductDisplayCode());
    			jso.addProperty("saleAmount", item.getSaleAmount());
    			listJson.add(jso);
    		});
    	}
    	json.add("sales", listJson);
    	return json.toString(); 
    }
    
    @RequestMapping(path = "/tiered/claim/calculator/sales")
    public @ResponseBody String tieredClaimCalculatorSales(HttpServletRequest request) throws ParseException {
    	SimpleDateFormat sdfS = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	
    	String startDate = request.getParameter("startDate");
    	String endDate = request.getParameter("endDate");
    	if(startDate != null) {
	    	if(startDate.equals(""))startDate = null;
	    	else startDate = sdfS.format(df.parse(startDate));
    	}
    	if(endDate != null) {
	    	if(endDate.equals(""))endDate = null;
	    	else endDate = sdfS.format(df.parse(endDate));
    	}
    	
    	JsonObject json = new JsonObject();
    	JsonArray listJson = new JsonArray();
    	List<Sales> sales = salesManager.getTieredSales(startDate, endDate);
    	Double salesAmount = salesManager.getSalesAmount(startDate, endDate);
    	double totalSales = salesAmount == null ? 0 : salesAmount.doubleValue();
    	if(sales != null && sales.size() > 0) {
    		sales.forEach(item->System.out.println(item.getId().getProductCode() + " -- " + item.getSaleAmount()));
    		sales.forEach(item->{
    			JsonObject jso = new JsonObject();
    			jso.addProperty("productCode", item.getId().getProductCode());
    			jso.addProperty("transactionDate", df.format(item.getId().getTransactionDate()));
    			jso.addProperty("saleAmount", item.getSaleAmount());
    			listJson.add(jso);
    		});
    	}
    	json.add("sales", listJson);
    	
    	DecimalFormat doubleFormat = new DecimalFormat("#.00");
    	JsonObject jsoTotalSales = new JsonObject();
    	jsoTotalSales.addProperty("amount", totalSales == 0 ? "0" : doubleFormat.format(totalSales));
    	json.add("total", jsoTotalSales);
    	
    	return json.toString(); 
    }
    
    @RequestMapping(path = "/tiered/claim/calculator/calc")
    public @ResponseBody String tieredClaimCalculator(HttpServletRequest request) throws ParseException {
    	SimpleDateFormat sdfS = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	DecimalFormat doubleFormat = new DecimalFormat("#.00");
    	DecimalFormat floatFormat = new DecimalFormat("#.000");
    	
    	String startDate = request.getParameter("startDate");
    	String endDate = request.getParameter("endDate");
    	if(startDate != null) {
	    	if(startDate.equals(""))startDate = null;
	    	else startDate = sdfS.format(df.parse(startDate));
    	}
    	if(endDate != null) {
	    	if(endDate.equals(""))endDate = null;
	    	else endDate = sdfS.format(df.parse(endDate));
    	}
    	
    	JsonObject json = new JsonObject();
    	JsonArray listJson = new JsonArray();
    	double totalClaim = 0;
    	Double salesAmount = salesManager.getSalesAmount(startDate, endDate);
    	double totalSales = salesAmount == null ? 0 : salesAmount.doubleValue();
    	List<DiscountTier> tiers = discountTierManager.getDiscountTiers();
    	if(totalSales > 0 && tiers.size() > 0) {
    		double previousTierMaxValue = 0;
    		tiers.forEach(tier->System.out.println(tier.getTierNo() + " -- " + tier.getMinValue() + " -- " + tier.getDiscountRate()));
    		for(DiscountTier tier : tiers) {
    			JsonObject jso = new JsonObject();
    			float tierDiscountRate = tier.getDiscountRate().floatValue();
    			jso.addProperty("tierNo", tier.getTierNo());
				jso.addProperty("tierDiscountRate", doubleFormat.format(tierDiscountRate * 100));
				
    			if(tier.getMaxValue() != null) {
    				if(totalSales > tier.getMaxValue().doubleValue()) {
    					double tierValue = tier.getMaxValue().doubleValue() - previousTierMaxValue;
        				double tieredClaimAmount = tierValue * tierDiscountRate;
            			jso.addProperty("tierValue", doubleFormat.format(tierValue));
            			jso.addProperty("tieredClaimAmount", doubleFormat.format(tieredClaimAmount));
            			listJson.add(jso);
            			
            			totalClaim = totalClaim + tieredClaimAmount;
    				}else {
    					double tierValue = totalSales - previousTierMaxValue;
    					double tieredClaimAmount = tierValue * tierDiscountRate;
            			jso.addProperty("tierValue", doubleFormat.format(tierValue));
            			jso.addProperty("tieredClaimAmount", doubleFormat.format(tieredClaimAmount));
            			listJson.add(jso);
            			
            			totalClaim = totalClaim + tieredClaimAmount;
            			break;
    				}
    				previousTierMaxValue = tier.getMaxValue().doubleValue();
    			}else {
    				// last tier
    				if(totalSales >= tier.getMinValue().doubleValue()) {
    					double tierValue = totalSales - previousTierMaxValue;
    					double tieredClaimAmount = tierValue * tierDiscountRate;
    					jso.addProperty("tierValue", doubleFormat.format(tierValue));
            			jso.addProperty("tieredClaimAmount", doubleFormat.format(tieredClaimAmount));
            			listJson.add(jso);
            			
            			totalClaim = totalClaim + tieredClaimAmount;
    				}
    			}
    		}
    	}
    	json.add("tiers", listJson);
    	
    	JsonObject jsoTotalClaim = new JsonObject();
    	jsoTotalClaim.addProperty("total", totalClaim == 0 ? "0" : doubleFormat.format(totalClaim));
    	json.add("claim", jsoTotalClaim);
    	
    	return json.toString(); 
    }

}