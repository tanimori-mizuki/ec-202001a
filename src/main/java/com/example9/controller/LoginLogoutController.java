package com.example9.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.LoginUser;
import com.example9.domain.User;
import com.example9.form.LoginForm;
import com.example9.service.AddToCartService;
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
	private AddToCartService addToCartService;

//	@Autowired
//	private ShowCartListService showCartListService;

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
	 * ショッピングカートの「注文へ進む」ボタンが押された時にのみ使用するメソッド.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/referer")
	public String makeReferer(HttpServletRequest request) {
		session.setAttribute("referer", request.getHeader("REFERER"));
		return "/login";
	}
	
	/**
	 * ログイン成功時に必要な処理を記述.
	 * ログイン処理成功後に必要な処理を行い、任意のページに遷移する.
	 * 
	 * 
	 * @param request リファラ情報
	 * @return
	 */
	@RequestMapping("/after_login")
	public String afterLogin(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		
		try {
			if (loginUser.getUser() != null) {
				LoginForm loginForm = new LoginForm();
				loginForm.setEmail(loginUser.getUsername());
				loginForm.setPassword(loginUser.getPassword());
				User userForSession = loginService.login(loginForm);
				session.setAttribute("user", userForSession);
				session.setAttribute("userId", userForSession.getId());
			}
		} catch (Exception e) {

		}
		
		// ログイン前のカートの中身をログイン後のカートに反映する
		addToCartService.addToCartAfterLogin();
		
		// ログイン成功後、リファラ情報を取り出す
		String url = (String) session.getAttribute("referer");
		System.out.println(url);
		
		// 「注文へ進む」ボタンからのリクエストがきた場合のみ、ログイン後に注文確認画面へ遷移する.
		if ("http://localhost:8080/show_cart_list".equals(url) ) {
			return "forward:/confirm";
		}
		
		// 通常は商品一覧画面に遷移する.
		return "forward:/";
	}
	
	// ログイン処理はspring securityが行うためコメントアウト.
	/**
	 * ログインする.
	 * 
	 * @param loginForm ログインフォーム入力情報
	 * @return 商品一覧ページ(メールアドレス/パスワード不正の場合、ログイン画面)
	 */
//	@RequestMapping("/input")
//	public String login(LoginForm loginForm, Model model, HttpServletRequest request) {
//
//		User user = loginService.login(loginForm);
//
//		if (user == null) {
//			model.addAttribute("inputError", "メールアドレスまたはパスワードが不正です");
//			return "/login";
//		}

		// セッションにログインユーザの情報を保存する
//		session.setAttribute("user", user);
//		session.setAttribute("userId", user.getId());

		// ログイン前のカートの中身をログイン後のカートに反映する
//		addToCartService.addToCartAfterLogin();

		// ログイン成功後、リファラ情報を取り出す
//		String url = (String) session.getAttribute("referer");

		// もしショッピングカートから遷移してきた場合、注文確認画面にフォワードする
//		List<Order> orderList = showCartListService.showCartList(user.getId());
//
//		if ("http://localhost:8080/show_cart_list".equals(url) && orderList != null) {
//			return "redirect:/confirm";
//		}
//
//		return "forward:/";
//	}

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
