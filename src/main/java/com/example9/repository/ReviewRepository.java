package com.example9.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example9.domain.Review;

/**
 * 口コミ情報を扱うリポジトリ.
 * 
 * @author mayumiono
 *
 */
@Repository
public class ReviewRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/** 口コミのローマッパー */
	private RowMapper<Review> ROW_MAPPER = (rs, i) -> {
		Review review = new Review();
		review.setReviewId(rs.getInt("id"));
		review.setOrderId(rs.getInt("order_id"));
		review.setUserId(rs.getInt("user_id"));
		review.setUserName(rs.getString("user_name"));
		review.setOrderItemId(rs.getInt("order_item_id"));
		review.setReview(rs.getString("review"));
		review.setEvaluation(rs.getInt("evaluation"));
		review.setItemId(rs.getInt("item_id"));
		review.setItemName(rs.getString("item_name"));
		review.setOrderDate(rs.getDate("order_date"));
		review.setImagePath(rs.getString("image_path"));
		review.setDescription(rs.getString("description"));
		return review;
	};

	/**
	 * 引数の商品IDに該当する口コミ一覧を取得する.
	 * 
	 * @param itemId 商品ID
	 * @return 口コミ一覧(該当口コミなしの場合、null)
	 */
	public List<Review> findByItemId(Integer itemId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT A.id, A.order_id, A.user_id, D.name AS user_name, A.order_item_id, A.review, A.evaluation, A.item_id, B.name AS item_name, B.image_path, B.description, C.order_date");
		sql.append(" FROM reviews AS A RIGHT OUTER JOIN items AS B ON A.item_id=B.id");
		sql.append(" JOIN orders AS C ON A.order_id=C.id ");
		sql.append(" JOIN users AS D ON A.user_id=D.id ");
		sql.append(" WHERE item_id=:itemId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);
		List<Review> reviewList = template.query(sql.toString(), param, ROW_MAPPER);
		if (reviewList.size() == 0) {
			return null;
		}
		return reviewList;
	}

}
