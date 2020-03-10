package com.example9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.service.DeleteFromCartService;

/**
 * カートから商品を削除するコントローラ.
 * 
 * @author yuuki
 *
 */
@Controller
@RequestMapping("/delete_from_cart")
public class DeleteFromCartController {

	@Autowired
	private DeleteFromCartService deleteFromCartService;

	/**
	 * 引数の注文商品IDに該当する商品を削除する.
	 * 
	 * @param orderItemId 注文商品ID
	 * @return カート内一覧画面
	 */
	@RequestMapping("")
	public String cartDelete(Integer orderItemId) {
		deleteFromCartService.cartDelete(orderItemId);
		return "redirect:/show_cart_list";
	}
}
