package com.example9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example9.domain.Item;
import com.example9.domain.Topping;
import com.example9.repository.ItemRepository;
import com.example9.repository.ToppingRepository;

/**
 * 商品詳細表示をするサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class ShowItemDetailService {

	@Autowired
	private ToppingRepository toppingRepository;

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * トッピング情報を全件取得.
	 * 
	 * @return トッピング一覧
	 */
	public List<Topping> getAllToppings() {
		return toppingRepository.findAll();
	}

	/**
	 * 商品情報詳細を取得.
	 * @param id　商品ID
	 * @return　商品情報詳細
	 */
	public Item getAnItem(Integer id) {
		return itemRepository.findById(id);
	}

}
