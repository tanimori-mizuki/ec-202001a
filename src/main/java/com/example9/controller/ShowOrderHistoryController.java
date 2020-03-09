package com.example9.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Order;
import com.example9.domain.User;
import com.example9.service.ShowOrderHistoryService;


/**
 * 注文履歴表示を行うコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/order-history")
public class ShowOrderHistoryController {

	@Autowired
	private ShowOrderHistoryService showOrderHistoryService;

	@Autowired
	private HttpSession session;

	// 1ページに表示する注文情報は5件
	private static final int VIEW_SIZE = 5;

	/**
	 * 注文履歴一覧の表示.
	 * 
	 * @param model リクエストスコープ
	 * @return 注文履歴一覧画面
	 */
	@RequestMapping("")
	public String showList(Model model, Integer page) {

		if (session.getAttribute("user") == null) {
			// ログイン前の場合は、ログインページへ遷移
			return "forward:/login";
		}

		// セッションスコープからユーザーID取得
		User user = (User) session.getAttribute("user");
		Integer userId = user.getId();

		// ページング機能追加
		if (page == null) {
			// ページ数の指定が無い場合は1ページ目を表示させる
			page = 1;
		}

		List<Order> orderList = null;
		try {
			orderList = showOrderHistoryService.getOrderHistoryList(userId);
//			model.addAttribute(orderList);
		} catch (Exception e) {
			// 注文履歴がnullの場合は、その旨のメッセージをリクエストスコープに格納する
			String nonOrderMessage = "注文履歴がありません";
			model.addAttribute("nonOrderMessage", nonOrderMessage);
			return "order_history";
		}

		// 表示させたいページ数、ページサイズ、従業員リストを渡し１ページに表示させる従業員リストを絞り込み
		Page<Order> orderPage = showOrderHistoryService.showListPaging(page, VIEW_SIZE, orderList);
		model.addAttribute("orderPage", orderPage);

		// ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき10件表示させる場合→1,2,3がpageNumbersに入る
		List<Integer> pageNumbers = calcPageNumbers(model, orderPage);
		model.addAttribute("pageNumbers", pageNumbers);

		return "order_history";
	}

	/**
	 * 引数の注文IDを有する注文について、詳細情報を表示する.
	 * 
	 * @param model   リクエストスコープ
	 * @param orderId 注文ID
	 * @return 注文履歴詳細画面
	 */
	@RequestMapping("/detail")
	public String showDetail(Model model, Integer orderId) {
		// 注文情報詳細の取得
		List<Order> orderList = showOrderHistoryService.getOrderHistoryDetail(orderId);
		Order order = orderList.get(0);
		model.addAttribute(order);

		return "order_history_detail";
	}

	/**
	 * ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき10件表示させる場合→1,2,3がpageNumbersに入る
	 * 
	 * @param model        モデル
	 * @param orderPage ページング情報
	 */
	private List<Integer> calcPageNumbers(Model model, Page<Order> orderPage) {
		int totalPages = orderPage.getTotalPages();
		List<Integer> pageNumbers = null;
		if (totalPages > 0) {
			pageNumbers = new ArrayList<Integer>();
			for (int i = 1; i <= totalPages; i++) {
				pageNumbers.add(i);
			}
		}
		return pageNumbers;
	}
}
