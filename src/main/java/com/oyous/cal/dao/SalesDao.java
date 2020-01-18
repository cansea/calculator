package com.oyous.cal.dao;

import java.util.List;

import com.oyous.cal.model.Sales;
import com.oyous.cal.model.SalesPK;

/**
 * Sales Data Access Object (GenericDao) interface.
 * 
 * @author <a href="mailto:joeone.qian@gmail.com">Joe Qian</a>
 *
 */
public interface SalesDao extends GenericDao<Sales, SalesPK> {
	List<Sales> getTieredSales(String startDate, String endDate);
	List<Sales> getProductSales(String startDate, String endDate);
	Double getSalesAmount(String startDate, String endDate);
}
