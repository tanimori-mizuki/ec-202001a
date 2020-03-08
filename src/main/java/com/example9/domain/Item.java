package com.example9.domain;

import java.util.List;

/**
 * 商品を表すドメイン.
 * 
 * @author yuuki
 *
 */
public class Item {

	/** ID */
	private Integer id;
	/** 名前 */
	private String name;
	/** 説明 */
	private String description;
	/** Mの価格 */
	private Integer priceM;
	/** Lの価格 */
	private Integer priceL;
	/** 画像のパス */
	private String imagePath;
	/** 削除フラグ */
	private Boolean deleted;
	/** トッピング */
	private List<Topping> toppingList;
	/** 口コミ評価平均値 */
	private double aveEvaluation;
	/** 口コミ件数 */
	private Integer countEvaluation;

	public double getAveEvaluation() {
		return aveEvaluation;
	}

	public void setAveEvaluation(double aveEvaluation) {
		this.aveEvaluation = aveEvaluation;
	}

	public Integer getCountEvaluation() {
		return countEvaluation;
	}

	public void setCountEvaluation(Integer countEvaluation) {
		this.countEvaluation = countEvaluation;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", priceM=" + priceM + ", priceL="
				+ priceL + ", imagePath=" + imagePath + ", deleted=" + deleted + ", toppingList=" + toppingList
				+ ", aveEvaluation=" + aveEvaluation + ", countEvaluation=" + countEvaluation + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriceM() {
		return priceM;
	}

	public void setPriceM(Integer priceM) {
		this.priceM = priceM;
	}

	public Integer getPriceL() {
		return priceL;
	}

	public void setPriceL(Integer priceL) {
		this.priceL = priceL;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public List<Topping> getToppingList() {
		return toppingList;
	}

	public void setToppingList(List<Topping> toppingList) {
		this.toppingList = toppingList;
	}

	/**
	 * 評価平均値を星へ変換
	 * 
	 * @return 星
	 */
	public String getStars() {
		if (0 <= aveEvaluation && aveEvaluation < 1) {
			return "☆☆☆☆☆";
		} else if (1 <= aveEvaluation && aveEvaluation < 2) {
			return "★☆☆☆☆";
		} else if (2 <= aveEvaluation && aveEvaluation < 3) {
			return "★★☆☆☆";
		} else if (3 <= aveEvaluation && aveEvaluation < 4) {
			return "★★★☆☆";
		} else if (4 <= aveEvaluation && aveEvaluation < 5) {
			return "★★★★☆";
		} else if (5 == aveEvaluation) {
			return "★★★★★";
		} else {
			return "☆☆☆☆☆";
		}
	}
}
