package com.oyous.cal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sales", catalog = "public")
public class Sales implements java.io.Serializable{
	private static final long serialVersionUID = -1593898206958059697L;
	
	@EmbeddedId
	private SalesPK id;
	
	@Column(name = "sale_amount")
    private double saleAmount;
	
	@Transient
    private String productDisplayCode;
	
	public Sales() {}
	
	public Sales(String productCode, double saleAmout) {
		this.productDisplayCode = productCode;
		this.saleAmount = saleAmout;
	}
	
	public SalesPK getId() {
		return id;
	}

	public void setId(SalesPK id) {
		this.id = id;
	}

	public double getSaleAmount() {
		return saleAmount;
	}
	
	public void setSaleAmount(double saleAmount) {
		this.saleAmount = saleAmount;
	}

	public String getProductDisplayCode() {
		return productDisplayCode;
	}

	public void setProductDisplayCode(String productDisplayCode) {
		this.productDisplayCode = productDisplayCode;
	}

}
