package com.oyous.cal.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SalesPK implements Serializable {
	private static final long serialVersionUID = -2429949163005874537L;

	@Column(name = "product_code")
	private String productCode;
	
	@Column(name = "transaction_date")
    private Date transactionDate;
    
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
}
