package com.example9.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * ユーザ登録への入力情報.
 * 
 * @author suzukikunpei
 *
 */
public class UserRegisterForm {

	/** 名前 */
	@NotBlank(message = "お名前を入力して下さい")
	private String name;

	/** メールアドレス */
	@Email(message = "アドレスが不正です")
	@NotBlank(message = "メールアドレスを入力して下さい")
	private String email;

	/** 郵便番号 */
	@NotBlank(message = "郵便番号を入力して下さい")
	@Pattern(regexp = "^[0-9]{7}$", message = "郵便番号はハイフン無の7桁で入力して下さい")
	private String zipcode;

	/** 住所 */
	@NotBlank(message = "住所を入力して下さい")
	private String address;

	/** 電話番号 */
	@NotBlank(message = "電話番号を入力して下さい")
	@Pattern(regexp = "^[0-9]+$", message = "電話番号は数値を入力して下さい")
	private String telephone;

	/** パスワード */
	@NotBlank(message = "パスワードを入力して下さい")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[!-~]{8,20}$", message = "パスワード形式が不正です")
	private String password;

	/** 確認用パスワード */
	@NotBlank(message = "確認用パスワードを入力して下さい")
	private String confirmationPassword;

	@Override
	public String toString() {
		return "UserRegisterForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", password=" + password + ", confirmationPassword="
				+ confirmationPassword + "]";
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

}
