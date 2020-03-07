package com.example9.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Order;
import com.example9.service.ShowCartListService;

/**
 * カート内の商品表示を担うコントローラ.
 * @author fujiiyuuki
 *
 */
@Controller
@RequestMapping("/show_cart_list")
public class ShowCartListController {
	
	@Autowired
	private ShowCartListService showCartListService;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * カート内の商品一覧を表示する.
	 * @param model リクエストスコープ
	 * @return　カート内画面
	 */
	@RequestMapping("")
	public String toCartList(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		// 非ログインユーザーがカートを表示する際の処理
		if (userId == null) {
			userId = session.getId().hashCode();
		}
		
		List <Order> orderList = showCartListService.showCartList(userId);
		
		if (orderList == null) {
			model.addAttribute("noOrderMessage", "カートに商品がありません");
			return "cart_list";
		}
		
		Order order = orderList.get(0);
			
		order.setTotalPrice(order.getCalcTotalPrice() + order.getTax());
		model.addAttribute("tax", order.getTax());
		model.addAttribute("order", order);
		
		return "cart_list";
	}
}
