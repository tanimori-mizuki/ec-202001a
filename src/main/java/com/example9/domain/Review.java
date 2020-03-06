package com.example9.domain;

import java.sql.Date;

/**
 * 口コミを表すドメイン.
 * 
 * @author mayumiono
 *
 */
public class Review {

	/** 口コミID */
	private Integer reviewId;
	/** 注文ID */
	private Integer orderId;
	/** ユーザーID */
	private Integer userId;
	/** ユーザー名 */
	private String userName;
	/** 注文商品ID */
	private Integer orderItemId;
	/** 口コミコメント */
	private String review;
	/** 評価(0-5点) */
	private Integer evaluation;
	/** 商品ID */
	private Integer itemId;
	/** 商品名 */
	private String itemName;
	/** 注文日 */
	private Date orderDate;
	/** 商品画像 */
	private String imagePath;
	/** 商品特徴 */
	private String description;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", orderId=" + orderId + ", userId=" + userId + ", userName=" + userName
				+ ", orderItemId=" + orderItemId + ", review=" + review + ", evaluation=" + evaluation + ", itemId="
				+ itemId + ", itemName=" + itemName + ", orderDate=" + orderDate + ", imagePath=" + imagePath
				+ ", description=" + description + "]";
	}



}
