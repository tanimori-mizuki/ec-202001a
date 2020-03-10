package com.example9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.form.AddToCartForm;
import com.example9.service.AddToCartService;

/**
 * ショッピングカートに商品を追加するコントローラ.
 * 
 * @author yuuki
 *
 */
@Controller
@RequestMapping("/add_to_cart")
public class AddToCartController {

	@Autowired
	private AddToCartService addToCartService;

	/**
	 * カートに商品を追加する.
	 * 
	 * @param form 追加する商品情報
	 * @return カート内一覧画面
	 */
	@RequestMapping("")
	public String cartAdd(AddToCartForm form) {

		// cartAddサービスにformを渡す
		addToCartService.addToCart(form);

		return "redirect:/show_cart_list";
	}

}
