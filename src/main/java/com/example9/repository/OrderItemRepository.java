package com.example9.repository;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example9.domain.OrderItem;

/**
 * order_itemsテーブルにアクセスするためのリポジトリ.
 * 
 * @author yuuki
 *
 */
@Repository
public class OrderItemRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private SimpleJdbcInsert insert;
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert 
		= new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName
			= simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
	
	/**
	 * OrderItemをデータベースに保存.
	 * 
	 * @param orderItem orderItemオブジェクト
	 * @return 自動採番されたidを含むorderItemオブジェクト
	 */
	public OrderItem insertOrderItem(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		
		if (orderItem.getId() == null) {
			Number key = insert.executeAndReturnKey(param);
			orderItem.setId(key.intValue());
		} else {
			String sql = "INSERT INTO order_items (item_id, order_id, quantity, size) "
						+"VALUES (:itemId, :orderId, :quantity, :size)";
			template.update(sql, param);
		}
		return orderItem;
	}
	
	
	/**
	 * 主キーを元にorderItemとそれに紐づいているorder_toppingをDBから削除する.
	 * 	  
	 * @param id 主キー
	 */
	public void deleteById(Integer id) {
		String sql = "WITH deleted AS (DELETE FROM order_items WHERE id = :id RETURNING id) " + 
					 "DELETE FROM order_toppings WHERE order_item_id IN (SELECT id FROM deleted);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
	
	
}
