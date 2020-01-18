/**
 * 
 */
package com.oyous.cal.service;

import java.util.List;

import com.oyous.cal.model.Sales;
import com.oyous.cal.model.SalesPK;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:joeone.qian@gmail.com">Joe Qian</a>
 */
public interface SalesManager extends GenericManager<Sales, SalesPK> {
	List<Sales> getTieredSales(String startDate, String endDate);
	List<Sales> getProductSales(String startDate, String endDate);
	Double getSalesAmount(String startDate, String endDate);
}
