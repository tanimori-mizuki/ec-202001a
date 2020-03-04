package com.example9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.service.DeleteFromCartService;

@Controller
@RequestMapping("/delete_from_cart")
public class DeleteFromCartController {
	
	@Autowired
	private DeleteFromCartService deleteFromCartService;
	
	@RequestMapping("")
	public String cartDelete(Integer orderItemId) {
		deleteFromCartService.cartDelete(orderItemId);
		return "redirect:/show_cart_list";
	}
}
