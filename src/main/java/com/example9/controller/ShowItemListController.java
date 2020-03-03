package com.example9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Item;
import com.example9.service.ShowItemListService;

@Controller
@RequestMapping("/item")
public class ShowItemListController {
	
	@Autowired
	private ShowItemListService showItemListService;

	/**
	 * 商品一覧表示を行います.
	 * @param model リクエストスコープ
	 * @return　商品一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Item>itemList = showItemListService.showList();
		model.addAttribute("itemList", itemList);
		return "item_list_curry";
	}
	
	/**
	 * 商品の曖昧検索を行います.
	 * @param name　名前
	 * @param model リクエストスコープ
	 * @return　商品一覧画面
	 */
	@RequestMapping("/searchResult")
	public String serchByLikeName(String code,Model model) {
		List<Item>itemList = null;
		if(code == null) {
			itemList = showItemListService.showList();
		} else {
			itemList = showItemListService.searchByLikeName(code);
			if(itemList == null) {
				itemList = showItemListService.showList();
			}
			model.addAttribute("itemList", itemList);
		}
		return "item_list_curry";
	}
	
}
