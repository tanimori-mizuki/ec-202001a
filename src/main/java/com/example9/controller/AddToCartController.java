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
	
	@RequestMapping("")
	public String cartAdd(AddToCartForm form) {
		
		// cartAddサービスにformを渡す
		addToCartService.addToCart(form);
		
		return "redirect:/cart_list";
	}
}
