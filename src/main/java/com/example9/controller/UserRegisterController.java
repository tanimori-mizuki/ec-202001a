package com.example9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ユーザ登録をするコントローラ.
 * 
 * @author suzukikunpei
 *
 */

@Controller
@RequestMapping("/register")
public class UserRegisterController {
	
	/**
	 * ユーザ登録する.
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("")
	public String toRegister() {
		return "register_user";
	}
	
	

}
