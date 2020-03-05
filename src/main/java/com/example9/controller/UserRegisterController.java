package com.example9.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.User;
import com.example9.form.UserRegisterForm;
import com.example9.service.UserRegisterService;

/**
 * ユーザ登録をするコントローラ.
 * 
 * @author suzukikunpei
 *
 */

@Controller
@RequestMapping("/register")
public class UserRegisterController {

	@Autowired
	private UserRegisterService userRegisterService;

	@ModelAttribute
	public UserRegisterForm setUpUserRegisterForm() {
		return new UserRegisterForm();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * ユーザ登録のページを表示.
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("")
	public String toRegister() {
		return "register_user";
	}

	/**
	 * ユーザ情報を登録する.
	 * 
	 * @param form ユーザ情報フォーム
	 * @return ログイン画面へリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(@Validated UserRegisterForm form, BindingResult result) {

		User existUser = userRegisterService.checkEmail(form.getEmail());

		if (existUser != null) {
			result.rejectValue("email", null, "そのメールアドレスはすでに使われています");
		}

		if (!form.getConfirmationPassword().equals(form.getPassword())) {
			result.rejectValue("password", null, "パスワードと確認用パスワードが一致しません");
		}

		if (result.hasErrors()) {
			return "register_user";
		}

		// パスワードを暗号化
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String encode = bcrypt.encode(form.getPassword());
		// フォームからドメインにプロパティ値をコピー
		User user = new User();
		BeanUtils.copyProperties(form, user);
		// ハッシュ化したパスワードをドメインにコピー
		user.setPassword(encode);

		userRegisterService.insert(user);

		return "redirect:/login";
	}
}
