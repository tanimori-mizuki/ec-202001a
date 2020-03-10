package com.example9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example9.ConvertEvaluationIntoStars;
import com.example9.domain.Review;
import com.example9.repository.ReviewRepository;

/**
 * 口コミに係る処理をするサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class ReviewService extends ConvertEvaluationIntoStars {

	@Autowired
	private ReviewRepository reviewRepository;

	/**
	 * 引数の商品IDに該当する口コミ一覧を取得する.
	 * 
	 * @param itemId 商品ID
	 * @return 口コミ一覧（該当なしの場合null）
	 */
	public List<Review> getReviews(Integer itemId) {
		return reviewRepository.findByItemId(itemId);
	}

	/**
	 * 新規口コミを投稿する.
	 * 
	 * @param review 口コミ
	 */
	public void postReview(Review review) {
		reviewRepository.insert(review);
	}

}
