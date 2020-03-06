package com.example9.form;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
	@Pattern(regexp="^[0-9]+$", message="電話番号は数値を入力して下さい")
	private String telephone;

	/** 配達時間 */
	// @NotBlank(message = "配達日時を入力して下さい")
	private Timestamp deliveryTime;

	/** 支払方法 */
	private String paymentMethod;
	
	public Integer getPaymentMethodInteger() {
		return Integer.parseInt(paymentMethod);
	}


	@Override
	public String toString() {
		return "OrderForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", deliveryTime=" + deliveryTime + ", paymentMethod=" + paymentMethod
				+ "]";
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
