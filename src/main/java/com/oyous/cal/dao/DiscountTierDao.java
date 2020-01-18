/**
 * 
 */
package com.oyous.cal.dao;

import java.util.List;

import com.oyous.cal.model.DiscountTier;

/**
 * Discount Tier Data Access Object (GenericDao) interface.
 * 
 * @author <a href="mailto:joeone.qian@gmail.com">Joe Qian</a>
 *
 */
public interface DiscountTierDao extends GenericDao<DiscountTier, Integer> {
	List<DiscountTier> getDiscountTiers();
}
