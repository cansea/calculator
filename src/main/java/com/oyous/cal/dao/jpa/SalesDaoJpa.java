/**
 * 
 */
package com.oyous.cal.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oyous.cal.dao.SalesDao;
import com.oyous.cal.model.Sales;
import com.oyous.cal.model.SalesPK;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Sales objects.
 *
 * @author <a href="mailto:joeone.qian@gmail.com">Joe Qian</a>
*/
@Repository
public class SalesDaoJpa extends GenericDaoJpa<Sales, SalesPK> implements SalesDao {
	public List<Sales> getTieredSales(String startDate, String endDate) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Sales s ");
		if(startDate != null && endDate != null) {
			hql.append("where s.id.transactionDate between '").append(startDate).append("' and '").append(endDate).append("' ");
		}else if(startDate != null && endDate == null) {
			hql.append("where s.id.transactionDate >= '").append(startDate).append("' ");
		}else if(startDate == null && endDate != null) {
			hql.append("where s.id.transactionDate <= '").append(endDate).append("' ");
		}
		hql.append("order by s.id.productCode, s.id.transactionDate");
		
		List<Sales> list = this.entityManager.createQuery(hql.toString()).getResultList();
		return list;
	}
	
	public List<Sales> getProductSales(String startDate, String endDate) {
		StringBuffer hql = new StringBuffer();
		hql.append("select new Sales(s.id.productCode, sum(s.saleAmount)) from Sales s ");
		if(startDate != null && endDate != null) {
			hql.append("where s.id.transactionDate between '").append(startDate).append("' and '").append(endDate).append("' ");
		}else if(startDate != null && endDate == null) {
			hql.append("where s.id.transactionDate >= '").append(startDate).append("' ");
		}else if(startDate == null && endDate != null) {
			hql.append("where s.id.transactionDate <= '").append(endDate).append("' ");
		}
		
		hql.append("group by s.id.productCode order by s.id.productCode");
		
		List<Sales> list = this.entityManager.createQuery(hql.toString()).getResultList();
		return list;
	}

	public Double getSalesAmount(String startDate, String endDate) {
		StringBuffer hql = new StringBuffer();
		hql.append("select sum(s.saleAmount) from Sales s ");
		if(startDate != null && endDate != null) {
			hql.append("where s.id.transactionDate between '").append(startDate).append("' and '").append(endDate).append("' ");
		}else if(startDate != null && endDate == null) {
			hql.append("where s.id.transactionDate >= '").append(startDate).append("' ");
		}else if(startDate == null && endDate != null) {
			hql.append("where s.id.transactionDate <= '").append(endDate).append("' ");
		}
		Double amount = (Double)this.entityManager.createQuery(hql.toString()).getResultList().get(0);
		return amount;
	}
}
