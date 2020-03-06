package com.example9.form;

/**
 * 口コミ投稿を表すフォーム.
 * 
 * @author mayumiono
 *
 */
public class ReviewForm {

	/** 注文ID */
	private Integer orderId;
	/** 投稿者名 */
	private String authorName;
	/** 注文商品ID */
	private Integer orderItemId;
	/** 口コミコメント */
	private String review;
	/** 評価（0-5点） */
	private Integer evaluation;
	/** 商品ID */
	private Integer itemId;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "ReviewForm [orderId=" + orderId + ", authorName=" + authorName + ", orderItemId=" + orderItemId
				+ ", review=" + review + ", evaluation=" + evaluation + ", itemId=" + itemId + "]";
	}

}
