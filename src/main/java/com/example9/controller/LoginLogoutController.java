package com.example9.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	 * ログインページを表示する .
	 * 
	 * @param model リクエストスコープ
	 * @param error ログイン失敗の場合true
	 * @return ログイン画面
	 */
	@RequestMapping("")
	public String toLoginPage(Model model, @RequestParam(required = false) String error) {
		if (error != null) {
			// ログイン失敗の場合はエラーメッセージを表示する
			model.addAttribute("inputError", "メールアドレスまたはパスワードが不正です");
		}
		return "login_page";
	}

	/**
	 * ショッピングカートの「注文へ進む」ボタンが押された時にのみ使用するメソッド.
	 * 
	 * @param request クライアントからのリクエスト情報
	 * @return ログイン画面
	 */
	@RequestMapping("/referer")
	public String makeReferer(HttpServletRequest request) {
		// 「注文へ進む」ボタン→ログイン画面→ログイン成功という順でアクセスした場合
		// 次に商品一覧ではなく、注文確認画面へ遷移させたいため、リファラ情報を格納しておく
		session.setAttribute("referer", request.getHeader("REFERER"));
		return "/login_page";
	}

	/**
	 * ログイン成功時に必要な処理を記述. ログイン処理成功後に必要な処理を行い、任意のページに遷移する.
	 * 
	 * @param loginUser ログインユーザー情報
	 * @param model     リクエストスコープ
	 * @return 商品一覧画面（「注文へ進む」ボタンから当パスに遷移してきた場合は、return:注文確認画面）
	 */
	@RequestMapping("/after_login")
	public String afterLogin(@AuthenticationPrincipal LoginUser loginUser, Model model) {

		try {
			// principalに格納されているログイン情報を、sessionにも格納する
			// （本来はこのようなことはしないが、今回はSpringSecurity導入前に作成した他メソッドにおいて
			// sessionからユーザー情報を取り出す記述を多くしており、修正が困難であるため）
			User userForSession = loginService.login(loginUser.getUser().getEmail());
			session.setAttribute("user", userForSession);
			session.setAttribute("userId", userForSession.getId());
		} catch (Exception e) {
			// ログイン前であれば何もしない
		}

		// ログイン前のカートの中身をログイン後のカートに反映する
		addToCartService.addToCartAfterLogin();

		// ログイン成功後、リファラ情報を取り出す
		String url = (String) session.getAttribute("referer");
		System.out.println(url);

		// 「注文へ進む」ボタンからのリクエストがきた場合のみ、ログイン後に注文確認画面へ遷移する.
		if ("http://localhost:8080/show_cart_list".equals(url)) {
			return "forward:/confirm";
		}

		// 通常は商品一覧画面に遷移する.
		return "forward:/";
	}

	// ログイン処理はspring securityが行うためコメントアウト.
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
