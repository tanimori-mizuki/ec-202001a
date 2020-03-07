package com.example9.form;

/**
 * ページ数を表すフォーム.
 * 
 * @author mizukitanimori
 *
 */
public class PagingNumberForm {

	/** ページ数 */
	private String pagingNumber;

	@Override
	public String toString() {
		return "PagingNumberForm [pagingNumber=" + pagingNumber + "]";
	}

	public String getPagingNumber() {
		return pagingNumber;
	}

	public void setPagingNumber(String pagingNumber) {
		this.pagingNumber = pagingNumber;
	}

}
