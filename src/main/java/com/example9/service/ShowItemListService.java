package com.example9.service;

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
	 * 検索数を6つに制限したSQL文を発行します.
	 * @param number　開始位置
	 * @return　商品一覧
	 */
	public List<Item>ShowListpaging(Integer number){
		return itemRepository.findByAllLimit(number);
	}
	
	/**
	 * オートコンプリート用にJavaScriptの配列の中身を文字列で作る.
	 * 
	 * @param itemList 商品一覧
	 * @return　オートコンプリート用JavaScriptの配列の文字列
	 */
	public StringBuilder getItemListForAutocomplete(List <Item> itemList) {
		StringBuilder itemListForAutocomplete = new StringBuilder();
		for (int i = 0; i < itemList.size(); i++) {
			if (i != 0) {
				itemListForAutocomplete.append(",");
			}
			Item item = itemList.get(i);
			itemListForAutocomplete.append("\"");
			itemListForAutocomplete.append(item.getName());
			itemListForAutocomplete.append("\"");		
		}
		return itemListForAutocomplete;
	}
	
	

}
