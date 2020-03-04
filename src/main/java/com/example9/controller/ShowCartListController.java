package com.example9.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Order;
import com.example9.domain.OrderItem;
import com.example9.service.ShowCartListService;

@Controller
@RequestMapping("/show_cart_list")
public class ShowCartListController {
	
	@Autowired
	private ShowCartListService showCartListService;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("")
	public String toCartList(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		// 仮の対応　後で直す
		if (userId == null) {
			userId = 10000;
		}
		
		List <Order> orderList = showCartListService.showCartList(userId);
		
		
		if (orderList == null) {
			model.addAttribute("noOrderMessage", "カートに商品がありません");
			return "cart_list";
		}
		
		Order order = orderList.get(0);
		
		// 小計を表示するための処理
		List <OrderItem> orderItemList = order.getOrderItemList(); 
		List <Integer> orderItemSubTotalList = new ArrayList<>();
		for (OrderItem orderItem : orderItemList) {
			orderItem.setSubtotal(orderItem.getSubTotal());
			orderItemSubTotalList.add(orderItem.getSubTotal());
		}
		model.addAttribute("orderItemSubTotalList", orderItemSubTotalList);
		
		
		order.setTotalPrice(order.getCalcTotalPrice() + order.getTax());
		model.addAttribute("tax", order.getTax());
		model.addAttribute("order", order);
		
		return "cart_list";
	}
}
