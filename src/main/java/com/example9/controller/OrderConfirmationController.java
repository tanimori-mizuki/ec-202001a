package com.example9.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example9.domain.CheckedCreditCard;
import com.example9.domain.Order;
import com.example9.domain.User;
import com.example9.form.OrderForm;
import com.example9.service.CheckCreditCardService;
import com.example9.service.OrderConfirmationService;
import com.example9.service.UserRegisterService;

/**
 * 注文確認画面を処理するコントローラ.
 * 
 * @author suzukikunpei
 *
 */
@Controller
@RequestMapping("/confirm")
public class OrderConfirmationController {

	@Autowired
	private UserRegisterService userRegisterService;

	@Autowired
	private CheckCreditCardService checkCreditCardService;

	/**
	 * ユーザー情報を受け取るフォーム.
	 * 
	 * @param id ユーザID
	 * @return ユーザ情報
	 */
	@ModelAttribute
	public OrderForm setupOrderForm(Integer id, Model model) {
		// ユーザIDを取得
		Integer userId = (Integer) session.getAttribute("userId");

		// ログインされていない場合、空のインスタンスを返す。
		if (userId == null) {
			return new OrderForm();
		}

		// ログインしている場合、ログイン時に入力した内容を取得し、userInfoに格納する。
		User userInfo = userRegisterService.showUser(userId);

		// userInfoをorderFormにセットする
		OrderForm orderForm = new OrderForm();
		orderForm.setName(userInfo.getName());
		orderForm.setEmail(userInfo.getEmail());
		orderForm.setZipcode(userInfo.getZipcode());
		orderForm.setAddress(userInfo.getAddress());
		orderForm.setTelephone(userInfo.getTelephone());
		orderForm.setPaymentMethod("1");

		// クレジットカード情報入力欄の有効期限年リストを作成する
		List<Integer> yearList = new ArrayList<>();
		LocalDate date = LocalDate.now();
		int topOfYear = date.getYear();
		int endOfYear = topOfYear + 20;
		for (int i = topOfYear; i <= endOfYear; i++) {
			yearList.add(i);
		}
		model.addAttribute("yearList", yearList);
		return orderForm;
	}

	@Autowired
	private OrderConfirmationService orderConfirmationService;

	@Autowired
	private HttpSession session;

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * 注文「確認」画面を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @return 注文した商品一覧
	 */
	@RequestMapping("")
	public String toOrderConfirmation(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		
		// ログインしていない状態であればログイン画面へ遷移する
		if (userId == null) {
			return "forward:/login/referer";
		}

		List<Order> orderList = orderConfirmationService.showOrderList(userId);
		
		// ログイン後URLを直打ちされて、カートの中身がない場合、NullPointerExceptionになってしまうので対策
		if (orderList == null) {
			return "forward:/";
		}
		
		Order order = orderList.get(0);
		
		
		
		order.setTotalPrice(order.getCalcTotalPrice() + order.getTax());
		model.addAttribute("tax", order.getTax());
		model.addAttribute("order", order);

		return "order_confirm";
	}

	/**
	 * 注文「確認」画面に表示されている商品を注文する.
	 * 
	 * @return 注文確認画面
	 */
	@RequestMapping("/orderAfterConfirm")
	public String toOrderConfirm(@Validated OrderForm form, BindingResult result, Model model) {

		// クレジットカード情報を確認する
		int paymentMethod = Integer.parseInt(form.getPaymentMethod());
		CheckedCreditCard checkedCard = new CheckedCreditCard();
		if (paymentMethod == 2) {
			checkedCard = checkCreditCardService.checkCardInfo(form);
		}
		if ("error".equals(checkedCard.getStatus())) {
			model.addAttribute("creditCard", "クレジットカード情報が不正です");
		}
		if (result.hasErrors() || "error".equals(checkedCard.getStatus())) {
			return "order_confirm";
		}

		if ("error".equals(checkedCard.getStatus())) {
			model.addAttribute("creditCard", "クレジットカード情報が不正です");
		}

		if (result.hasErrors() || "error".equals(checkedCard.getStatus())) {
			return "order_confirm";
		}
		
		Order updateOrder = new Order();

		// 現在時刻を取得
		LocalDateTime nowTime = LocalDateTime.now();
		// 現在時刻より1時間進んだ時間を取得
		LocalDateTime oneHourLater = nowTime.plusHours(1);
		// 「yyyy-MM-dd HH:mm:ss」にフォーマット
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedOneHourLater = oneHourLater.format(dtf);

		// LocalTimeDateの表示のうち、ミリ秒を削除
		String deliver = formattedOneHourLater.substring(0, 19);
		LocalDateTime allowedDeliveryDate = LocalDateTime.parse(deliver, dtf);

		// ユーザが選んだ日付(String)をLocalDateTime型に変換する
		String deliveryTime = form.getDeliveryDate() + " " + form.getDeliveryHour();
		LocalDateTime selectedDate = LocalDateTime.parse(deliveryTime, dtf);

		// 注文確認画面で選択した配達時間が「現在時刻 + 1時間」より前であれば、エラーを表示する
		if (selectedDate.isBefore(allowedDeliveryDate)) {
			result.rejectValue("deliveryDate", null, "配達日時は現在時刻より1時間以上先を選択して下さい");
			return "order_confirm";
		}

		// ユーザが選んだ日付(String)をTimeStamp型に変換する
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh");
		java.util.Date parsedDate = null;

		try {
			parsedDate = dateFormat.parse(deliveryTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// String型からTimeStamp型へ変換
		Timestamp timeStampDeliveryTime = new java.sql.Timestamp(parsedDate.getTime());
		// TimeStamp型に変換したパラメータをドメインに移す
		updateOrder.setDeliveryTime(timeStampDeliveryTime);

		// formからのパラメータをドメインに移す
		updateOrder.setDestinationName(form.getName());
		updateOrder.setDestinationEmail(form.getEmail());
		updateOrder.setDestinationZipcode(form.getZipcode());
		updateOrder.setDestinationAddress(form.getAddress());
		updateOrder.setDestinationTel(form.getTelephone());
		updateOrder.setPaymentMethod(Integer.parseInt(form.getPaymentMethod()));

		// 注文日に関するオブジェクト生成(注文履歴確認で使用する)
		LocalDate now = LocalDate.now();
		Date orderDate = java.sql.Date.valueOf(now);
		updateOrder.setOrderDate(orderDate);

		// userId取得する
		Integer userId = (Integer) session.getAttribute("userId");
		updateOrder.setUserId(userId);

		// 合計金額を取得する
		List<Order> orderList = orderConfirmationService.showOrderList(userId);
		Order order = orderList.get(0);
		updateOrder.setTotalPrice(order.getCalcTotalPrice());

		orderConfirmationService.updateOrder(updateOrder);

		return "forward:/order";
	}
}
