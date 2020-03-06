package com.example9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Review;
import com.example9.service.ReviewService;

/**
 * 口コミを操作するコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	/**
	 * 口コミを表示する.
	 * 
	 * @param itemId 商品ID
	 * @param model  リクエストスコープ
	 * @return 口コミ表示ページ
	 */
	@RequestMapping("")
	public String show(Integer itemId, Model model) {
		List<Review> reviewList = reviewService.getReviews(itemId);
		model.addAttribute("reviewList", reviewList);
		// 評価平均値を算出
		Integer sumEvaluations = 0;
		for (Review review : reviewList) {
			sumEvaluations += review.getEvaluation();
		}
		double aveEvaluation = sumEvaluations / reviewList.size();
		model.addAttribute("aveEvaluation", aveEvaluation);
		return "review_show";
	}

}
