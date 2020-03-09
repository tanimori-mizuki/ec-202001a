package com.example9.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Order;
import com.example9.domain.User;
import com.example9.form.SerchHistoryForm;
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

	@ModelAttribute
	public SerchHistoryForm setUpForm() {
		return new SerchHistoryForm();
	}

	// 1ページに表示する注文情報は5件
	private static final int VIEW_SIZE = 5;

	/**
	 * 注文履歴一覧の表示.
	 * 
	 * @param form  日付絞り込み情報
	 * @param page  ページ番号
	 * @param model リクエストスコープ
	 * @return 注文履歴一覧画面
	 */
	@RequestMapping("")
	public String showList(SerchHistoryForm form, Integer page, Model model) {

		if (session.getAttribute("user") == null) {
			// ログイン前の場合は、ログインページへ遷移
			return "forward:/login";
		}

		// セッションスコープからユーザーID取得
		User user = (User) session.getAttribute("user");
		Integer userId = user.getId();

		// 絞り込み日付の最小値・最大値を変数格納
		Date minDate = form.getMinDate();
		Date maxDate = form.getMaxDate();

		// 絞り込み日付欄の未入力確認（未入力：true, 入力あり：false）
		Boolean minDateIsEmpty = "".equals(form.getMinYear()) && "".equals(form.getMinMonth())
				&& "".equals(form.getMinDay());
		Boolean maxDateIsEmpty = "".equals(form.getMaxYear()) && "".equals(form.getMaxMonth())
				&& "".equals(form.getMaxDay());

		// 最大値のみ入力した場合は、最小値＝西暦始まりとする
		if (minDateIsEmpty && !maxDateIsEmpty) {
			form.setMinYear("0000");
			form.setMinMonth("1");
			form.setMinDay("1");
			minDate = form.getMinDate();
		}

		// 最小値のみ入力した場合は最大値＝本日とする
		LocalDate today = LocalDate.now();
		if (!minDateIsEmpty && maxDateIsEmpty) {
			form.setMaxYear(String.valueOf(today.getYear()));
			form.setMaxMonth(String.valueOf(today.getMonthValue()));
			form.setMaxDay(String.valueOf(today.getDayOfMonth()));
			maxDate = form.getMaxDate();
		}

		// 入力値をDateに変換できなかった場合は不整値とみなす
		if (form.getMinYear() != null && !"".equals(form.getMinYear()) && minDate == null) {
			model.addAttribute("dayError", "正しい日付を入力してください");
			return "order_history";
		}
		if (form.getMaxYear() != null && !"".equals(form.getMaxYear()) && maxDate == null) {
			model.addAttribute("dayError", "正しい日付を入力してください");
			return "order_history";
		}

		// 最小日付が本日よりも大きな値の場合、エラー
		try {
			LocalDate minLocalDate = LocalDate.parse(String.valueOf(minDate), DateTimeFormatter.ISO_DATE);
			if (minLocalDate.compareTo(today) > 0) {
				model.addAttribute("dayError", "左欄には、本日以前の日付を入力してください");
				return "order_history";
			}
		} catch (DateTimeParseException e) {
		}

		// 最小日付よりも最大日付が小さな値の場合、エラー
		if (maxDate != null && minDate != null && maxDate.compareTo(minDate) < 0) {
			model.addAttribute("dayError", "右欄は、左欄以降の日付を入力するか、空欄にしてください");
			return "order_history";
		}

		// ページング番号からも検索できるよう、最大・最小日付を保存しておく
		session.setAttribute("minYear", form.getMinYear());
		session.setAttribute("minMonth", form.getMinMonth());
		session.setAttribute("minDay", form.getMinDay());
		session.setAttribute("maxYear", form.getMaxYear());
		session.setAttribute("maxMonth", form.getMaxMonth());
		session.setAttribute("maxDay", form.getMaxDay());

		// ページング機能追加
		if (page == null) {
			// ページ数の指定が無い場合は1ページ目を表示させる
			page = 1;
		}

		List<Order> orderList = null;
		try {
			orderList = showOrderHistoryService.getOrderHistoryList(userId, minDate, maxDate);
		} catch (Exception e) {
			// 注文履歴がnullの場合は、その旨のメッセージをリクエストスコープに格納する
			String nonOrderMessage = "注文履歴がありません";
			model.addAttribute("nonOrderMessage", nonOrderMessage);
			return "order_history";
		}

		// 表示させたいページ数、ページサイズ、注文リストを渡し１ページに表示させる注文リストを絞り込み
		Page<Order> orderPage = showOrderHistoryService.showListPaging(page, VIEW_SIZE, orderList);
		model.addAttribute("orderPage", orderPage);

		// ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき10件表示させる場合→1,2,3がpageNumbersに入る
		List<Integer> pageNumbers = calcPageNumbers(model, orderPage);
		model.addAttribute("pageNumbers", pageNumbers);

		// 注文履歴詳細画面から注文履歴一覧画面に戻る際、元のページに戻れるよう、ページ番号を保存しておく
		session.setAttribute("pageNumForDetailPage", page);

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
	 * @param model     モデル
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
