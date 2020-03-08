package com.example9.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example9.domain.Item;
import com.example9.domain.Review;
import com.example9.domain.User;
import com.example9.form.ReviewForm;
import com.example9.service.ReviewService;
import com.example9.service.ShowItemDetailService;

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

	@Autowired
	private ShowItemDetailService showItemDetailService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public ReviewForm setUpForm() {
		return new ReviewForm();
	}

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

		if (reviewList == null) {
			model.addAttribute("nonReview", "口コミがありません。投稿お待ちしております！");
			model.addAttribute("itemId", itemId);
			return "review_show";
		}

		model.addAttribute("reviewList", reviewList);
		// 評価平均値を算出
		Integer sumEvaluations = 0;
		for (Review review : reviewList) {
			sumEvaluations += review.getEvaluation();
		}
		double aveEvaluation = sumEvaluations / reviewList.size();
		model.addAttribute("aveEvaluation", aveEvaluation);
		// 評価平均値を星へ変換
		String stars;
		if (0 <= aveEvaluation && aveEvaluation < 1) {
			stars = "☆☆☆☆☆";
		} else if (1 <= aveEvaluation && aveEvaluation < 2) {
			stars = "★☆☆☆☆";
		} else if (2 <= aveEvaluation && aveEvaluation < 3) {
			stars = "★★☆☆☆";
		} else if (3 <= aveEvaluation && aveEvaluation < 4) {
			stars = "★★★☆☆";
		} else if (4 <= aveEvaluation && aveEvaluation < 5) {
			stars = "★★★★☆";
		} else if (5 == aveEvaluation) {
			stars = "★★★★★";
		} else {
			stars = "☆☆☆☆☆";
		}
		model.addAttribute("stars", stars);
		return "review_show";
	}

	/**
	 * 投稿フォームページを表示する.
	 * 
	 * @param orderId     注文ID
	 * @param orderItemId 注文商品ID
	 * @param model       リクエストスコープ
	 * @return 口コミ投稿フォーム画面
	 */
	@RequestMapping("/form")
	public String toFormPage(Integer orderId, Integer orderItemId, Integer itemId, Model model) {
		Item item = showItemDetailService.getAnItem(itemId);
		model.addAttribute("item", item);
		User user = (User) session.getAttribute("user");
		model.addAttribute("userName", user.getName());
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderItemId", orderItemId);
		return "review_post";
	}

	/**
	 * 口コミを投稿する.
	 * 
	 * @param form   投稿内容
	 * @param result 入力値チェック
	 * @param flash  フラッシュスコープ
	 * @param model  リクエストスコープ
	 * @return 投稿完了画面へのリダイレクト
	 */
	@RequestMapping("/post")
	public String post(@Validated ReviewForm form, BindingResult result, RedirectAttributes flash, Model model) {

		if (result.hasErrors()) {
			Item item = showItemDetailService.getAnItem(Integer.parseInt(form.getItemId()));
			model.addAttribute("item", item);
			User user = (User) session.getAttribute("user");
			model.addAttribute("userName", user.getName());
			model.addAttribute("orderId", form.getOrderId());
			model.addAttribute("orderItemId", form.getOrderItemId());
			return "review_post";
		}

		Review review = new Review();
		review.setOrderId(Integer.parseInt(form.getOrderId()));
		User user = (User) session.getAttribute("user");
		review.setUserId(user.getId());
		review.setAuthorName(form.getAuthorName());
		review.setOrderItemId(Integer.parseInt(form.getOrderItemId()));
		review.setEvaluation(Integer.parseInt(form.getEvaluation()));
		review.setReview(form.getReview());
		review.setItemId(Integer.parseInt(form.getItemId()));
		reviewService.postReview(review);
		flash.addFlashAttribute("itemId", form.getItemId());
		return "redirect:/review/posted";
	}

	/**
	 * 口コミ投稿完了画面を表示する.
	 * 
	 * @return 口コミ投稿完了画面
	 */
	@RequestMapping("/posted")
	public String post() {
		return "review_post_finished";
	}
}
