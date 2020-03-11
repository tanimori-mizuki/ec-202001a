package com.example9;

import java.math.BigDecimal;

/**
 * 評価点数→星５つ表示への変換処理を行う.
 * 
 * @author mayumiono
 *
 */
public class ConvertEvaluationIntoStars {

	/**
	 * 評価点数（Double型）→星５つ表示への変換処理.
	 * 
	 * @param evaluation 評価点数
	 * @return 星表示
	 */
	public String getStars(Double evaluation) {
		String stars;
		if (0 <= evaluation && evaluation < 1) {
			stars = "☆☆☆☆☆";
		} else if (1 <= evaluation && evaluation < 2) {
			stars = "★☆☆☆☆";
		} else if (2 <= evaluation && evaluation < 3) {
			stars = "★★☆☆☆";
		} else if (3 <= evaluation && evaluation < 4) {
			stars = "★★★☆☆";
		} else if (4 <= evaluation && evaluation < 5) {
			stars = "★★★★☆";
		} else if (5 == evaluation) {
			stars = "★★★★★";
		} else {
			stars = "☆☆☆☆☆";
		}
		return stars;
	}

	/**
	 * 評価点数（BigDecimal型）→星５つ表示への変換処理.
	 * 
	 * @param evaluation 評価点数
	 * @return 星表示
	 */
	public String getStars(BigDecimal evaluation) {
		String stars;
		BigDecimal zero = BigDecimal.valueOf(0);
		BigDecimal one = BigDecimal.valueOf(1);
		BigDecimal two = BigDecimal.valueOf(2);
		BigDecimal three = BigDecimal.valueOf(3);
		BigDecimal four = BigDecimal.valueOf(4);
		BigDecimal five = BigDecimal.valueOf(5);
		if (evaluation.compareTo(zero) >= 0 && evaluation.compareTo(one) == -1) {
			stars = "☆☆☆☆☆";
		} else if (evaluation.compareTo(one) >= 0 && evaluation.compareTo(two) == -1) {
			stars = "★☆☆☆☆";
		} else if (evaluation.compareTo(two) >= 0 && evaluation.compareTo(three) == -1) {
			stars = "★★☆☆☆";
		} else if (evaluation.compareTo(three) >= 0 && evaluation.compareTo(four) == -1) {
			stars = "★★★☆☆";
		} else if (evaluation.compareTo(four) >= 0 && evaluation.compareTo(five) == -1) {
			stars = "★★★★☆";
		} else if (evaluation.compareTo(five) >= 0) {
			stars = "★★★★★";
		} else {
			stars = "☆☆☆☆☆";
		}
		return stars;
	}

}
