package com.example9.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example9.domain.Topping;

/**
 * トッピングテーブルを扱うリポジトリ.
 * 
 * @author mayumiono
 *
 */
@Repository
public class ToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private RowMapper<Topping> TOPPING_ROW_MAPPER = (rs, i) -> {
		Topping topping = new Topping();
		topping.setId(rs.getInt("id"));
		topping.setName(rs.getString("name"));
		topping.setPriceM(rs.getInt("price_m"));
		topping.setPriceL(rs.getInt("price_l"));
		return topping;
	};

	/**
	 * トッピング情報を全件取得する.
	 * 
	 * @return トッピング一覧情報
	 */
	public List<Topping> findAll() {
		String sql = "SELECT id, name, price_m, price_l FROM toppings;";
		List<Topping> topingList = template.query(sql, TOPPING_ROW_MAPPER);
		return topingList;
	}

}
