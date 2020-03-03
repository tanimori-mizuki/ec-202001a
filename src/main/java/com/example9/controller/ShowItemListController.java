package com.example9.controller;

import java.util.ArrayList;
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
		
		List<List<Item>>itemListList = new ArrayList<>();
		
		List<Item>itemList1 = new ArrayList<Item>();
		itemList1.add(itemList.get(0));
		itemList1.add(itemList.get(1));
		itemList1.add(itemList.get(2));
		itemListList.add(itemList1);
		
		List<Item>itemList2 = new ArrayList<Item>();
		itemList2.add(itemList.get(3));
		itemList2.add(itemList.get(4));
		itemList2.add(itemList.get(5));
		itemListList.add(itemList2);
		
		List<Item>itemList3 = new ArrayList<Item>();
		itemList3.add(itemList.get(6));
		itemList3.add(itemList.get(7));
		itemList3.add(itemList.get(8));
		itemListList.add(itemList3);
		
		List<Item>itemList4 = new ArrayList<Item>();
		itemList4.add(itemList.get(9));
		itemList4.add(itemList.get(10));
		itemList4.add(itemList.get(11));
		itemListList.add(itemList4);
		
		List<Item>itemList5 = new ArrayList<Item>();
		itemList5.add(itemList.get(12));
		itemList5.add(itemList.get(13));
		itemList5.add(itemList.get(14));
		itemListList.add(itemList5);
		
		List<Item>itemList6 = new ArrayList<Item>();
		itemList6.add(itemList.get(15));
		itemList6.add(itemList.get(16));
		itemList6.add(itemList.get(17));
		itemListList.add(itemList6);
		
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
		List<List<Item>>itemListList = new ArrayList<>();

		List<Item>itemList = showItemListService.searchByLikeName(code);
		if(itemList.size() == 0) {
			return showList(model);
		} else {
			model.addAttribute("itemListList", itemListList);
		}
		return "item_list_curry";
	}
}
