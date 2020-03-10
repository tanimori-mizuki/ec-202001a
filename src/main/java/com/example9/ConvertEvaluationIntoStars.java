package com.example9;

/**
 * 評価点数→星５つ表示への変換処理を行う.
 * 
 * @author mayumiono
 *
 */
public class ConvertEvaluationIntoStars {

	/**
	 * 評価点数→星５つ表示への変換処理.
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

}
