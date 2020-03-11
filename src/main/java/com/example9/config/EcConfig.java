package com.example9.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * SpringSecurityの各種設定をするコンフィグ.
 * @author mayumiono
 *
 */
@Configuration
class ExConfig extends WebSecurityConfigurerAdapter {
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// ログイン前にアクセス可とするパス群
				.mvcMatchers("/").permitAll()
				.mvcMatchers("/searchResult").permitAll()
				.mvcMatchers("/sortShowList").permitAll()
				.mvcMatchers("/review").permitAll()
				.mvcMatchers("/add_to_cart").permitAll()
				.mvcMatchers("/add_to_cart/**").permitAll()
				.mvcMatchers("/delete_from_cart").permitAll()
				.mvcMatchers("/delete_from_cart/**").permitAll()
				.mvcMatchers("/login").permitAll()
				.mvcMatchers("/login/referer").permitAll()
				.mvcMatchers("/showPage").permitAll()
				.mvcMatchers("/confirm").permitAll()
				.mvcMatchers("/confirm/**").permitAll()
				.mvcMatchers("/show_cart_list").permitAll()
				.mvcMatchers("/show_cart_list/**").permitAll()
				.mvcMatchers("/item-detail").permitAll()
				.mvcMatchers("/item-detail/**").permitAll()
				.mvcMatchers("/register").permitAll()
				.mvcMatchers("/register/**").permitAll()
				.mvcMatchers("/css/**").permitAll()
				.mvcMatchers("/img_curry/**")
				.permitAll().mvcMatchers("/js/**").permitAll()
				// 上記以外のパスは、ログイン以前のアクセス不可とする
				.anyRequest().authenticated().and();
		// LOGIN
		http.formLogin()
				.loginPage("/login") // ログイン画面を表示するパス
				.loginProcessingUrl("/login_input") // ログイン可否判定するパス（HTMLの入力フォームでth:action()内に指定）
				.failureUrl("/login?error=true") // ログイン失敗時に遷移させるパス
				.defaultSuccessUrl("/login/after_login", true) //ログイン成功時に遷移させるパス
				.usernameParameter("email") //ログインユーザー名（ログイン画面のHTML上の<input name="**">とそろえる）
				.passwordParameter("password")//ログインパスワード（ログイン画面のHTML上の<input name="**">とそろえる）
			.and()
		// LOGOUT
				.logout() 
					.logoutRequestMatcher(new AntPathRequestMatcher("/login/logout")) //ログアウト処理をするパス
					.logoutSuccessUrl("/") //ログアウト成功時に遷移させるパス
//                .deleteCookies("JSESSIONID")
					.invalidateHttpSession(true).permitAll();
		//　デフォルトの設定ではログイン前後でセッションIDが変わってしまうので、それを無効にする
		http.sessionManagement().sessionFixation().none();
		
		// end
	}
}