package com.example9.form;

import java.util.Date;


/**
 * 注文内容確認の入力フォーム
 * 
 * @author suzukikunpei
 *
 */
public class OrderForm {

	/** 名前 */
	private String name;

	/** メールアドレス */
	private String email;

	/** 郵便番号 */
	private String zipcode;

	/** 電話番号 */
	private String telephone;

	/** 配達時間 */
	private Date deliverlyTime;

	/** 支払方法 */
	private Integer paymentMethod;

	@Override
	public String toString() {
		return "OrderForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", telephone=" + telephone
				+ ", deliverlyTime=" + deliverlyTime + ", paymentMethod=" + paymentMethod + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getDeliverlyTime() {
		return deliverlyTime;
	}

	public void setDeliverlyTime(Date deliverlyTime) {
		this.deliverlyTime = deliverlyTime;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
