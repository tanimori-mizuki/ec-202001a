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
		 * 商品を一覧検索します.
		 * @return　Itemリスト
		 */
		public List<Item>showList(){
			return itemRepository.findAll();
		}
		
		
		/**
		 * 曖昧検索を行います.
		 * @param name　名前
		 * @return　Itemリスト
		 */
		public List<Item> searchByName(String name) {
			return itemRepository.findByLikeName(name);
		}
	
}
