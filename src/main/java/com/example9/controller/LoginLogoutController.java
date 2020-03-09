package com.example9.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Order;
import com.example9.domain.User;
import com.example9.form.LoginForm;
import com.example9.service.AddToCartService;
import com.example9.service.LoginService;
import com.example9.service.ShowCartListService;

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
	private AddToCartService addToCartService;

	@Autowired
	private ShowCartListService showCartListService;

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
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("")
	public String toLoginPage() {
		return "login";
	}

	/**
	 * ログイン画面から商品一覧画面以外の任意のページに遷移するためのメソッド
	 * 
	 * @param request リファラ情報
	 * @return
	 */
	@RequestMapping("/referer")
	public String referer(HttpServletRequest request) {
		// どのURLからログインページに飛んできたかを保存しておく
		session.setAttribute("referer", request.getHeader("REFERER"));
		return "login";
	}

	/**
	 * ログインする.
	 * 
	 * @param loginForm ログインフォーム入力情報
	 * @return 商品一覧ページ(メールアドレス/パスワード不正の場合、ログイン画面)
	 */
	@RequestMapping("/input")
	public String login(LoginForm loginForm, Model model, HttpServletRequest request) {

		User user = loginService.login(loginForm);

		if (user == null) {
			model.addAttribute("inputError", "メールアドレスまたはパスワードが不正です");
			return "/login";
		}

		// セッションにログインユーザの情報を保存する
		session.setAttribute("user", user);
		session.setAttribute("userId", user.getId());

		// ログイン前のカートの中身をログイン後のカートに反映する
		addToCartService.addToCartAfterLogin();

		// ログイン成功後、リファラ情報を取り出す
		String url = (String) session.getAttribute("referer");

		// もしショッピングカートから遷移してきた場合、注文確認画面にフォワードする
		List<Order> orderList = showCartListService.showCartList(user.getId());

		if ("http://localhost:8080/show_cart_list".equals(url) && orderList != null) {
			return "redirect:/confirm";
		}

		return "forward:/";
	}

	/**
	 * ログアウトする.
	 * 
	 * @return 商品一覧ページ
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "forward:/";
	}

}
