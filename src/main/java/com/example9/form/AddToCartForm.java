package com.example9.form;

import java.util.Arrays;

public class AddToCartForm {
	/** 主キー */
	private Integer itemId;
	/** サイズ */
	private Character size;
	/** 数量 */
	private Integer quantity;
	/** トッピングid */
	private String [] toppingId;
	
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Character getSize() {
		return size;
	}
	public void setSize(Character size) {
		this.size = size;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String[] getToppingId() {
		return toppingId;
	}
	public void setToppingId(String[] toppingId) {
		this.toppingId = toppingId;
	}
	
	@Override
	public String toString() {
		return "ShoppingCartAddForm [itemId=" + itemId + ", size=" + size + ", quantity=" + quantity + ", toppingId="
				+ Arrays.toString(toppingId) + "]";
	}
}
