package com.oyous.cal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "discount_tier", catalog = "public")
public class DiscountTier implements java.io.Serializable{
	private static final long serialVersionUID = -7407406725107182686L;
	
	private Integer tierNo;
	private Double minValue;
	private Double maxValue;
	private Float discountRate;
	
	public DiscountTier() {}
	
	@Id
	@GeneratedValue
	@Column(name = "tier_no", unique = true, nullable = false)
	public Integer getTierNo() {
		return tierNo;
	}
	
	public void setTierNo(Integer tierNo) {
		this.tierNo = tierNo;
	}
	
	@Column(name = "min_value")
	public Double getMinValue() {
		return minValue;
	}
	
	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}
	
	@Column(name = "max_value")
	public Double getMaxValue() {
		return maxValue;
	}
	
	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}
	
	@Column(name = "discount_rate")
	public Float getDiscountRate() {
		return discountRate;
	}
	
	public void setDiscountRate(Float discountRate) {
		this.discountRate = discountRate;
	}
	
	
}
