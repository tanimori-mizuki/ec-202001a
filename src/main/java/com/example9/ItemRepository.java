package com.example9;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example9.domain.Item;

/**
 * 検索を行うリポジトリ―クラスです.
 * @author mizuki.tanimori
 *
 */
@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * Itemオブジェクトを生成するローマッパー
	 */
	public static final RowMapper<Item>ITEM_ROW_MAPPER = (rs,i) ->{
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};
	
	/**
	 * 全件検索を行います.
	 * @return　Itemリスト
	 */
	public List<Item> findAll(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,name,description,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_m");
		return template.query(sql.toString(), ITEM_ROW_MAPPER);
	}
}
