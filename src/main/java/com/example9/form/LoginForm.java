package com.example9.form;

import javax.validation.constraints.NotBlank;

/**
 * ログインフォームへの入力情報
 * 
 * @author mayumiono
 *
 */
public class LoginForm {

	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	private String email;
	/** パスワード */
	@NotBlank(message = "パスワードを入力してください")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginForm [email=" + email + ", password=" + password + "]";
	}

}
