package com.example9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Item;
import com.example9.service.ShowItemListService;

@Controller
@RequestMapping("")
public class ShowItemListController {

	@Autowired
	private ShowItemListService showItemListService;
	
	/**
	 * 商品一覧を表示します.
	 * @param model　リクエストスコープ
	 * @return　商品一覧表示画面
	 */
	@RequestMapping("")
	public String showList(Model model) {
		List<Item>itemList = showItemListService.showList();
		model.addAttribute("itemList", itemList);
		return "";
	}
	
	/**
	 * 曖昧検索を行います.
	 * @param name 名前
	 * @param model　リクエストスコープ
	 * @return　商品検索結果画面
	 */
	@RequestMapping("")
	public String searchByName(String name,Model model){
		List<Item>serchItemList = showItemListService.searchByName(name);
		model.addAttribute("serchItemList", serchItemList);
		return "";
	}
}
