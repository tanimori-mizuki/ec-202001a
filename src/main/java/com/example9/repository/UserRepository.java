package com.example9.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example9.domain.User;
import com.example9.form.LoginForm;

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
	public User findByEmail(LoginForm loginForm) {
		String sql = "SELECT id, name, email, password, zipcode, address, telephone FROM users WHERE email=:email;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", loginForm.getEmail());
		// 該当データなしの場合、NullPointerException発生
		try {
			User user = template.queryForObject(sql, param, USER_ROW_MAPPER);
			return user;
		} catch (Exception e) {
			return null;
		}

	}

}
