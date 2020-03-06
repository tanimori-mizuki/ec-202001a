package com.example9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example9.domain.CheckedCreditCard;
import com.example9.form.OrderForm;

/**
 * クレジットカード情報の確認をするサービス.
 * 
 * @author mayuiono
 *
 */
@Service
public class CheckCreditCardService {

	@Autowired
	RestTemplate restTemplate;

	/** WebApiのwarファイルデプロイ先URL */
	private static final String URL = "http://192.168.17.35:8080/sample-credit-card-web-api/credit-card/payment";

	/**
	 * クレジットカード情報
	 * @param form
	 * @return
	 */
	public CheckedCreditCard checkCardInfo(OrderForm form) {
		return restTemplate.postForObject(URL, form, CheckedCreditCard.class);
	}
}
