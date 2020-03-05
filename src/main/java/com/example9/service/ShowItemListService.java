package com.example9.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example9.domain.Item;
import com.example9.repository.ItemRepository;

/**
 * 商品一覧を表示するサービスクラスです.
 * 
 * @author mizuki
 *
 */
@Service
@Transactional
public class ShowItemListService {

	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 商品一覧表示を行います.
	 * @return　商品一覧
	 */
	public List<Item>showList(){
		return itemRepository.findAll();
	}
	
	/**
	 * 商品の曖昧検索を行います.
	 * @param name　名前
	 * @return　検索商品
	 */
	public List<Item>searchByLikeName(String name){
		return itemRepository.findByLikeName(name);
	}
	

	/**
	 * 商品一覧を金額の降順で表示します.
	 * @return　金額降順商品一覧
	 */
	public List<Item>getSortedItemList(String searchConditionNumber){
		List<Item>itemList = null;
		if("0".equals(searchConditionNumber)) {
			 itemList = itemRepository.sortFindAll("price_m");
		} else if("1".equals(searchConditionNumber)) {
			itemList = itemRepository.sortFindAll("price_m DESC");
		}
		return itemList;
	}
	
	/**
	 * 3個のItemオブジェクトを1セットにしてリストで返す.
	 * 
	 * @param itemList 商品リスト
	 * @return　3個1セットの商品リスト
	 */
	public List <List<Item>> getThreeItemList(List <Item> itemList){
		List<List<Item>>itemListList = new ArrayList<>();
		List <Item> threeItemList = new ArrayList<>(); 
		int i = 1 ;
		
		while (i <= itemList.size()) {
			threeItemList.add(itemList.get(i-1));
			
			if (i  % 3 == 0 || i == itemList.size()) {
				itemListList.add(threeItemList);
				threeItemList = new ArrayList<>(); 
			}
			i++;
		}
		return itemListList;
	}
}
