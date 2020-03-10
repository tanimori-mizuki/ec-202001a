package com.example9.domain;

import org.springframework.security.core.authority.AuthorityUtils;


/**
 * ユーザー情報を表すドメイン.
 * 
 * @author mayumiono
 *
 */
@SuppressWarnings("serial")
public class LoginUser extends org.springframework.security.core.userdetails.User {

	/** ログイン情報 */
	private final User user;

	/**
	 * コンストラクタ
	 * 
	 * @param user
	 */
	public LoginUser(User user) {
		// スーパークラスのユーザーID、パスワードに値をセットする
		// 実際の認証はスーパークラスのユーザーID、パスワードで行われる
		super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
		this.user = user;
	}

	/**
	 *
	 * @return
	 */
	public User getUser() {
		return this.user;
	}
}

