package com.example9.form;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;

/**
 * 注文内容確認の入力フォーム
 * 
 * @author suzukikunpei
 *
 */
public class OrderForm {

	/** 名前 */
	@NotBlank(message = "お名前を入力して下さい")
	private String name;

	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力して下さい")
	private String email;

	/** 郵便番号 */
	private String zipcode;

	/** 住所 */
	@NotBlank(message = "住所を入力して下さい")
	private String address;

	/** 電話番号 */
	@NotBlank(message = "電話番号を入力して下さい")
	private String telephone;

	/** 配達時間 */
	// @NotBlank(message = "配達日時を入力して下さい")
	private Timestamp deliveryTime;

	/** 支払方法 */
	private String paymentMethod;

	/** カード番号 */
	private Integer card_number;
	/** 有効期限（年） */
	private Integer card_exp_year;
	/** 有効期限（月） */
	private Integer card_exp_month;
	/** カード名義人 */
	private Integer card_name;
	/** セキュリティコード */
	private Integer card_cvv;

	public Integer getCard_number() {
		return card_number;
	}

	public void setCard_number(Integer card_number) {
		this.card_number = card_number;
	}

	public Integer getCard_exp_year() {
		return card_exp_year;
	}

	public void setCard_exp_year(Integer card_exp_year) {
		this.card_exp_year = card_exp_year;
	}

	public Integer getCard_exp_month() {
		return card_exp_month;
	}

	public void setCard_exp_month(Integer card_exp_month) {
		this.card_exp_month = card_exp_month;
	}

	public Integer getCard_name() {
		return card_name;
	}

	public void setCard_name(Integer card_name) {
		this.card_name = card_name;
	}

	public Integer getCard_cvv() {
		return card_cvv;
	}

	public void setCard_cvv(Integer card_cvv) {
		this.card_cvv = card_cvv;
	}

	public Integer getPaymentMethodInteger() {
		return Integer.parseInt(paymentMethod);
	}

	@Override
	public String toString() {
		return "OrderForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", deliveryTime=" + deliveryTime + ", paymentMethod=" + paymentMethod
				+ ", card_number=" + card_number + ", card_exp_year=" + card_exp_year + ", card_exp_month="
				+ card_exp_month + ", card_name=" + card_name + ", card_cvv=" + card_cvv + "]";
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
