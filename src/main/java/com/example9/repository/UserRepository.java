package com.example9.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example9.domain.User;

/**
 * ユーザー情報を扱うリポジトリ.
 * 
 * @author mayumiono
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));
		return user;
	};

	/**
	 * メールアドレスでユーザ情報を検索・取得する.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報(該当なしの場合null)
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id, name, email, password, zipcode, address, telephone FROM users WHERE email=:email;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		// 該当データなしの場合、NullPointerException発生
		try {
			User user = template.queryForObject(sql, param, USER_ROW_MAPPER);
			return user;
		} catch (Exception e) {
			return null;
		}

	}
	
	/**
	 * ユーザ登録を行う.
	 * 
	 * @param user ユーザ情報
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "INSERT INTO users (name, email, password, zipcode, address, telephone)"
				   + " VALUES (:name, :email, :password, :zipcode, :address, :telephone)";
		template.update(sql, param);
	}

}
