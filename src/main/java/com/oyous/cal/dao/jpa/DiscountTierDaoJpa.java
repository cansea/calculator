package com.oyous.cal.dao.jpa;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.oyous.cal.dao.DiscountTierDao;
import com.oyous.cal.model.DiscountTier;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve DiscountTier objects.
 *
 * @author <a href="mailto:joeone.qian@gmail.com">Joe Qian</a>
*/
@Repository
public class DiscountTierDaoJpa extends GenericDaoJpa<DiscountTier, Integer> implements DiscountTierDao{
	public List<DiscountTier> getDiscountTiers() {
		StringBuffer hql = new StringBuffer();
		hql.append("from DiscountTier d order by d.tierNo");
		List<DiscountTier> list = this.entityManager.createQuery(hql.toString()).getResultList();
		return list;
	}
}
