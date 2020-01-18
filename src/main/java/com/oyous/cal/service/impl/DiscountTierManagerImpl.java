/**
 * 
 */
package com.oyous.cal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oyous.cal.dao.DiscountTierDao;
import com.oyous.cal.model.DiscountTier;
import com.oyous.cal.service.DiscountTierManager;

/**
 * Implementation of DiscountTierManager interface.
 *
 * @author <a href="mailto:joeone.qian@gmail.com">Joe Qian</a>
 */
@Service
public class DiscountTierManagerImpl extends GenericManagerImpl<DiscountTier, Integer> implements DiscountTierManager {
	private DiscountTierDao discountTierDao;

	@Autowired
	public void setDiscountTierDao(DiscountTierDao discountTierDao) {
		this.discountTierDao = discountTierDao;
		this.dao = discountTierDao;
	}

	public List<DiscountTier> getDiscountTiers() {
		return discountTierDao.getDiscountTiers();
	}
}
