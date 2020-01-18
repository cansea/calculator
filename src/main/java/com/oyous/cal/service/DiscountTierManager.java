/**
 * 
 */
package com.oyous.cal.service;

import java.util.List;

import com.oyous.cal.model.DiscountTier;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:joeone.qian@gmail.com">Joe Qian</a>
 */
public interface DiscountTierManager extends GenericManager<DiscountTier, Integer> {
	List<DiscountTier> getDiscountTiers();
}
