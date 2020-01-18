/**
 * 
 */
package com.oyous.cal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oyous.cal.dao.SalesDao;
import com.oyous.cal.model.Sales;
import com.oyous.cal.model.SalesPK;
import com.oyous.cal.service.SalesManager;

/**
 * Implementation of SalesManager interface.
 *
 * @author <a href="mailto:joeone.qian@gmail.com">Joe Qian</a>
 */
@Service
public class SalesManagerImpl extends GenericManagerImpl<Sales, SalesPK> implements SalesManager {
	private SalesDao salesDao;
	
	@Autowired
	public void setSalesDao(SalesDao salesDao) {
		this.dao = salesDao;
		this.salesDao = salesDao;
	}
	
	public List<Sales> getTieredSales(String startDate, String endDate){
		return salesDao.getTieredSales(startDate, endDate);
	}

	public List<Sales> getProductSales(String startDate, String endDate) {
		return salesDao.getProductSales(startDate, endDate);
	}

	public Double getSalesAmount(String startDate, String endDate) {
		return salesDao.getSalesAmount(startDate, endDate);
	}

}
