package com.example9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.User;
import com.example9.form.LoginForm;
import com.example9.service.LoginService;

/**
 * ログイン・ログアウトをするコントローラ.
 * 
 * @author mayuimono
 *
 */
@Controller
@RequestMapping("/login")
public class LoginLogoutController {

	@Autowired
	private LoginService loginService;

	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/**
	 * ログインページを表示する
	 * 
	 * @return ログインページ
	 */
	@RequestMapping("")
	public String toLoginPage() {
		return "login";
	}

	/**
	 * ログインする.
	 * 
	 * @param loginForm ログインフォーム入力情報
	 * @param result    エラー確認
	 * @return 商品一覧ページ(メールアドレス/パスワード不正の場合、ログイン画面)
	 */
	@RequestMapping("/input")
	public String login(@Validated LoginForm loginForm, BindingResult result) {
		User user = loginService.login(loginForm);

		if (user == null) {
			result.rejectValue("inputError", null, "メールアドレス、またはパスワードが間違っています");
		}

		if (result.hasErrors()) {
			return "/login";
		}

		// ToDo：ShowItemListController- showList()のパスへ変更
		return "item_list_curry";
	}

}
