package com.example9.domain;

import java.util.List;

import com.example9.ConvertEvaluationIntoStars;

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

	/**
	 * 口コミ評価平均×１０の数値を算出.
	 * 
	 * ※商品一覧で評価順にソートする際のComparatorにおいて、Integer型で扱う必要があるため
	 * 
	 * @return 口コミ評価平均×１０
	 */
	public Integer getAveEvaluationTenfold() {
		Integer aveEvaluationTefold = (int) (this.aveEvaluation * 10);
		return aveEvaluationTefold;
	}

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
		ConvertEvaluationIntoStars convertEvaluationIntoStars = new ConvertEvaluationIntoStars();
		String stars = convertEvaluationIntoStars.getStars(aveEvaluation);
		return stars;
	}
}
