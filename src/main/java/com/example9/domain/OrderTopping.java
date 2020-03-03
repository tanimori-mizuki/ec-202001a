package com.example9.domain;

/**
 * 注文トッピングを表すドメインクラス.
 * 
 * @author yuuki
 *
 */
public class OrderTopping {
	/** 主キー */
	private Integer id; 
	/** トッピングid */
	private Integer toppingId;
	/** 注文id */
	private Integer orderItemId;
	/** トッピング */
	private Topping topping;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getToppingId() {
		return toppingId;
	}
	public void setToppingId(Integer toppingId) {
		this.toppingId = toppingId;
	}
	public Integer getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}
	public Topping getTopping() {
		return topping;
	}
	public void setTopping(Topping topping) {
		this.topping = topping;
	}
	@Override
	public String toString() {
		return "OrderTopping [id=" + id + ", toppingId=" + toppingId + ", orderItemId=" + orderItemId + ", topping="
				+ topping + "]";
	}
	
}
