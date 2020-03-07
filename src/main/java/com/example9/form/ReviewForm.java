package com.example9.form;

import javax.validation.constraints.Size;

/**
 * 口コミ投稿を表すフォーム.
 * 
 * @author mayumiono
 *
 */
public class ReviewForm {

	/** 注文ID */
	private String orderId;
	/** 投稿者名 */
	@Size(min = 1, max = 20, message = "1-20文字で名前を入力してください")
	private String authorName;
	/** 注文商品ID */
	private String orderItemId;
	/** 口コミコメント */
	@Size(min = 1, max = 500, message = "1-500文字でコメントを入力してください")
	private String review;
	/** 評価（0-5点） */
	@Size(min = 1, max = 1, message = "点数を選択してください")
	private String evaluation;
	/** 商品ID */
	private String itemId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "ReviewForm [orderId=" + orderId + ", authorName=" + authorName + ", orderItemId=" + orderItemId
				+ ", review=" + review + ", evaluation=" + evaluation + ", itemId=" + itemId + "]";
	}

}
