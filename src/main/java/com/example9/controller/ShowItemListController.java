package com.example9.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Item;
import com.example9.form.SortConditionNumberForm;
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
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public SortConditionNumberForm setUpSortConditionNumberForm() {
		SortConditionNumberForm form = new SortConditionNumberForm();
		form.setSortConditionNumber("0");
		return form;
	}
	
	
	/**
	 * 商品一覧表示を行います.
	 * @param model リクエストスコープ
	 * @return　商品一覧画面
	 */
	@RequestMapping("")
	public String showList(Model model) {
		
		List<Item>itemList = showItemListService.showList();	
		session.setAttribute("itemList", itemList);
		
		List<List<Item>>itemListList = getThreeItemList(itemList);
	
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
			session.setAttribute("itemList", itemList);
			List<List<Item>>itemListList = getThreeItemList(itemList);
			model.addAttribute("itemListList", itemListList);
		}
		return "item_list_curry";
	}
	
	@RequestMapping("/sortShowList")
	public String sortShowList(SortConditionNumberForm form, Model model) {
		@SuppressWarnings("unchecked")
		List <Item> itemList = (List<Item>) session.getAttribute("itemList");
		itemList = sortItemList(itemList, form);
		List<List<Item>>itemListList = getThreeItemList(itemList);
		model.addAttribute("itemListList", itemListList);
		return "item_list_curry";
	}
	
	
	
	/**
	 * ItemListをソートする.
	 * 
	 * @param itemList ソートしたいItemList
	 * @param form 並び替えフォーム
	 * @return ソート済みのitemList
	 */
	private List <Item> sortItemList(List<Item> itemList,SortConditionNumberForm form){
		Comparator<Item> sortCondition = null;
		
		if ("0".equals(form.getSortConditionNumber())) {
			// Mサイズの価格の昇順でソートするComparator
			sortCondition = new Comparator<Item>() {
				@Override
				public int compare(Item item1, Item item2) {
					return item1.getPriceM().compareTo(item2.getPriceM());
				}
			};
		} else if ("1".equals(form.getSortConditionNumber())) {
			// Mサイズの価格の降順でソートするComparator
			sortCondition = new Comparator<Item>() {
				@Override
				public int compare(Item item1, Item item2) {
					return item2.getPriceM().compareTo(item1.getPriceM());
				}
			};
		}
		
		Collections.sort(itemList,sortCondition);
		return itemList;
	}
	
	
	
	/**
	 * 3個のItemオブジェクトを1セットにしてリストで返す.
	 * 
	 * @param itemList 商品リスト
	 * @return　3個1セットの商品リスト
	 */
	private List <List<Item>> getThreeItemList(List <Item> itemList){
		List<List<Item>>itemListList = new ArrayList<>();
		List <Item> threeItemList = new ArrayList<>(); 
		
		for (int i = 1; i <= itemList.size(); i++) {
			threeItemList.add(itemList.get(i-1));
			
			if (i  % 3 == 0 || i == itemList.size()) {
				itemListList.add(threeItemList);
				threeItemList = new ArrayList<>(); 
			}
		}
		return itemListList;
	}
}
