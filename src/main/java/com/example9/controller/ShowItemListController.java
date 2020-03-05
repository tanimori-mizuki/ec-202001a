package com.example9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Item;
import com.example9.service.ShowItemListService;

/**
 * 商品を一覧表示するコントローラーです.
 * @author mizuki
 *
 */
@Controller
@RequestMapping("/")
public class ShowItemListController {
	
	@Autowired
	private ShowItemListService showItemListService;

	/**
	 * 商品一覧表示を行います.
	 * @param model リクエストスコープ
	 * @return　商品一覧画面
	 */
	@RequestMapping("")
	public String showList(Model model) {
		
		List<Item>itemList = showItemListService.showList();
		List<List<Item>>itemListList = showItemListService.getThreeItemList(itemList);
	
		model.addAttribute("itemListList", itemListList);
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
		List<Item>itemList = showItemListService.searchByLikeName(code);
		if(itemList.size() == 0) {
			String message = "該当する商品がありません";
			model.addAttribute("message", message);
			return showList(model);
		} else {
			List<List<Item>>itemListList = showItemListService.getThreeItemList(itemList);
			model.addAttribute("itemListList", itemListList);
		}
		return "item_list_curry";
	}
	
	@RequestMapping("/sortShowList")
	public String sortShowList(String searchConditionNumber, Model model) {
		List<Item>itemList = showItemListService.getSortedItemList(searchConditionNumber);
		List<List<Item>>itemListList = showItemListService.getThreeItemList(itemList);
		model.addAttribute("itemListList", itemListList);
		return "item_list_curry";
	}
}
