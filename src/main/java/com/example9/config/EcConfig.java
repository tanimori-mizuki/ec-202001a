package com.example9.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
class ExConfig extends WebSecurityConfigurerAdapter {
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// ログイン前にアクセス可とするファイル群
				.mvcMatchers("/").permitAll()
				.mvcMatchers("/searchResult").permitAll()
				.mvcMatchers("/sortShowList").permitAll()
				.mvcMatchers("/add_to_cart**").permitAll()
				.mvcMatchers("/delete_from_cart**").permitAll()
				.mvcMatchers("/login").permitAll()
				.mvcMatchers("/login/referer").permitAll()
				.mvcMatchers("/showPage").permitAll()
//				.mvcMatchers("/confirm**").permitAll()
				.mvcMatchers("/show_cart_list**").permitAll()
				.mvcMatchers("/item-detail**").permitAll()
				.mvcMatchers("/register**").permitAll()
				.mvcMatchers("/css/**").permitAll()
				.mvcMatchers("/img_curry/**")
				.permitAll().mvcMatchers("/js/**").permitAll()
				// 上記以外のファイルは、ログイン以前のアクセス不可とする
				.anyRequest().authenticated().and();
		// LOGIN
		http.formLogin()
				.loginPage("/login") // ログイン画面を表示するパス
				.loginProcessingUrl("/login_input") // ログイン可否判定するパス（HTMLの入力フォームでth:action()内に指定）
				.failureUrl("/login?error=true") // ログイン失敗したときに遷移させるパス
				.defaultSuccessUrl("/login/after_login", true) //トップページ
				.usernameParameter("email") //ログインユーザー名（ログイン画面のHTML上の<input name="**">とそろえる）
				.passwordParameter("password").and()
				.logout() 
					.logoutRequestMatcher(new AntPathRequestMatcher("/login/logout"))
					.logoutSuccessUrl("/")
//                .deleteCookies("JSESSIONID")
					.invalidateHttpSession(true).permitAll();
		//　デフォルトの設定ではログイン前後でセッションIDが変わってしまうので、それを無効にする
		http.sessionManagement().sessionFixation().none();
		
		// end
	}
}