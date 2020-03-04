package com.example9.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@Autowired
	private HttpSession session;

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
	public String login(@Validated LoginForm loginForm, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "/login";
		}

		User user = loginService.login(loginForm);

		if (user == null) {
			model.addAttribute("inputError", "メールアドレスまたはパスワードが不正です");
			return "/login";
		}

		session.setAttribute("user", user);
		// 以下2行はセッションスコープ内確認用（動作確認後削除）
		User user2 = (User) session.getAttribute("user");
		System.out.println(user2.toString());
		// ToDo：ShowItemListController- showList()のパスへ変更
		return "forward:/item/showList";

	}

	/**
	 * ログアウトする.
	 * 
	 * @return 商品一覧ページ
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		// ToDo：ShowItemListController- showList()のパスへ変更
		return "forward:/item/showListS";
	}

}
