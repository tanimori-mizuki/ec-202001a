package com.example9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example9.domain.User;
import com.example9.form.LoginForm;
import com.example9.repository.UserRepository;

/**
 * ログイン処理をするサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * ログインする.
	 * 
	 * @param loginForm ログインフォームへの入力情報
	 * @return ユーザー情報（パスワード/メールアドレス不正の場合null）
	 */
	public User login(LoginForm loginForm) {

		User user = userRepository.findByEmail(loginForm.getEmail());

		if (user == null) {
			return null;
		}

//		// DB上のハッシュ化パスワードとフォームから来た平文パスワードを照合する
//		if (!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
//			return null;
//		}

		return user;
	}
}
