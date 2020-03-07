package com.example9.form;

/**
 * 並べ替え用フォーム.
 * 
 * @author yuuki
 *
 */
public class SortConditionNumberForm {
	/** 並び替え方法の番号 */
	String sortConditionNumber;

	public String getSortConditionNumber() {
		return sortConditionNumber;
	}

	public void setSortConditionNumber(String sortConditionNumber) {
		this.sortConditionNumber = sortConditionNumber;
	}

	@Override
	public String toString() {
		return "SortConditionNumberForm [sortConditionNumber=" + sortConditionNumber + "]";
	}

}
