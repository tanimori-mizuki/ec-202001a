package com.example9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example9.domain.User;
import com.example9.repository.UserRepository;

/**
 * ユーザ登録の処理を行うサービス.
 * 
 * @author suzukikunpei
 *
 */
@Service
public class UserRegisterService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * ユーザ情報を登録する.
	 * 
	 * @param user ユーザ情報
	 */
	public void insert(User user) {
		userRepository.insert(user);
	}
	
	/**
	 * メールアドレスの検索する.
	 * @param email　メールアドレス
	 * @return ユーザ情報
	 */
	public User checkEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

}
