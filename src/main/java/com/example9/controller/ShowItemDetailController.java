package com.example9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Item;
import com.example9.domain.Topping;
import com.example9.service.ShowItemDetailService;

/**
 * 商品詳細表示をするコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/item-detail")
public class ShowItemDetailController {

	@Autowired
	private ShowItemDetailService showItemDetailService;

	/**
	 * 引数の商品IDに該当する商品の詳細情報を表示する.
	 * 
	 * @param id    商品ID
	 * @param model リクエストスコープ
	 * @return 商品詳細画面
	 */
	@RequestMapping("")
	public String toItemDetail(Model model,Integer id) {
		
		Item item = showItemDetailService.getAnItem(id);
		List<Topping> toppingList = showItemDetailService.getAllToppings();
		model.addAttribute("toppingPriceM", toppingList.get(0).getPriceM());
		model.addAttribute("toppingPriceL", toppingList.get(0).getPriceL());
		item.setToppingList(toppingList);
		model.addAttribute("item", item);
		return "item_detail";
	}

}
