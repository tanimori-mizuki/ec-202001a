package com.example9.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Item;
import com.example9.form.SortConditionNumberForm;
import com.example9.service.ShowItemListService;

/**
 * 商品を一覧表示するコントローラーです.
 * 
 * @author mizuki
 *
 */
@Controller
@RequestMapping("/")
public class ShowItemListController {

	@Autowired
	private ShowItemListService showItemListService;

	@Autowired
	private HttpSession session;

	@Autowired
	private ServletContext application;

	// 1ページに表示する商品は6品
	private static final int VIEW_SIZE = 6;

	@ModelAttribute
	public SortConditionNumberForm setUpSortConditionNumberForm() {
		SortConditionNumberForm form = new SortConditionNumberForm();
		form.setSortConditionNumber("0");
		return form;
	}

	/**
	 * 商品一覧表示、商品検索を行います.
	 * 
	 * @param model リクエストスコープ
	 * @return 商品一覧画面
	 */
	@RequestMapping("")
	public String showList(String code, Model model, Integer pagingNumber) {
		// オートコンプリート用の記述
		List<Item> fullItemList = showItemListService.showList();
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(fullItemList);
		application.setAttribute("itemListForAutocomplete", itemListForAutocomplete);

		// itemListを宣言しておく
		List<Item> itemList = null;
		// もし検索されない場合は全件表示する（商品一覧表示機能）
		if (code == null) {
			// 商品一覧を取得
			itemList = showItemListService.showList();
		} else {
			// 検索された場合
			model.addAttribute("code", code);
			itemList = showItemListService.searchByLikeName(code);
		}

		// １件もヒットしない場合
		if (itemList.size() == 0) {
			String message = "該当する商品がありません";
			model.addAttribute("message", message);
			itemList = showItemListService.showList();
		}
		// ソート用にsessionスコープに残しておく
		session.setAttribute("itemList", itemList);

//		 -- ページング処理 -- 
		if (pagingNumber == null) {
			pagingNumber = 1;
		}

		// １ページに表示する商品が詰まったpageItemListを受け取る
		List<Item> pageItemList = makeListPaging(pagingNumber, itemList, model);

		List<List<Item>> itemListList = getThreeItemList(pageItemList);
		model.addAttribute("itemListList", itemListList);

		// 商品詳細画面から一覧画面に戻る際、元のページに戻れるよう、ページ番号・検索条件を保存しておく
		session.setAttribute("pageNumForDetailPage", pagingNumber);
		session.setAttribute("code", code);
		return "item_list_curry";
	}

	@RequestMapping("/showPage")
	public String showPage(Integer pagingNumber, Model model) {
		@SuppressWarnings("unchecked")
		List<Item> itemList = (List<Item>) session.getAttribute("itemList");

//		 -- ページング処理 -- 
		if (pagingNumber == null) {
			pagingNumber = 1;
		}
		// １ページに表示する商品が詰まったpageItemListを受け取る
		List<Item> pageItemList = makeListPaging(pagingNumber, itemList, model);

		List<List<Item>> itemListList = getThreeItemList(pageItemList);
		model.addAttribute("itemListList", itemListList);

		// 商品詳細画面から一覧画面に戻る際、元のページに戻れるよう、ページ番号を保存しておく
		session.setAttribute("pageNumForDetailPage", pagingNumber);
		return "item_list_curry";
	}

	/**
	 * ユーザーの好きな条件で商品一覧/検索結果を並べ換える.
	 * 
	 * @param form         ソート条件フォーム
	 * @param model        リクエストスコープ
	 * @param pagingNumber ページ数
	 * @return
	 */
	@RequestMapping("/sortShowList")
	public String sortShowList(SortConditionNumberForm form, Model model, Integer pagingNumber) {
		@SuppressWarnings("unchecked")
		List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		itemList = sortItemList(itemList, form);

//		 -- ページング処理 -- 
		if (pagingNumber == null) {
			pagingNumber = 1;
		}

		// １ページに表示する商品が詰まったpageItemListを受け取る
		List<Item> pageItemList = makeListPaging(pagingNumber, itemList, model);

		List<List<Item>> itemListList = getThreeItemList(pageItemList);
		model.addAttribute("itemListList", itemListList);

		// 商品詳細画面から一覧画面に戻る際、元のページに戻れるよう、sessionスコープ内のページ番号・ソート条件を更新
		session.removeAttribute("pageNumForDetailPage");
		session.removeAttribute("sortConditionNumber");
		session.setAttribute("pageNumForDetailPage", pagingNumber);
		session.setAttribute("sortConditionNumber", form.getSortConditionNumber());
		return "item_list_curry";
	}

	/**
	 * ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき10件表示させる場合→1,2,3がpageNumbersに入る
	 * 
	 * @param model        モデル
	 * @param employeePage ページング情報
	 */
	private List<Integer> calcPageNumbers(Model model, Page<Item> ItemPage) {
		int totalPages = ItemPage.getTotalPages();
		List<Integer> pageNumbers = null;
		if (totalPages > 0) {
			pageNumbers = new ArrayList<>();
			for (int i = 1; i <= totalPages; i++) {
				pageNumbers.add(i);
			}
		}
		return pageNumbers;
	}

	/**
	 * ページング用メソッド.
	 * 
	 * @param pagingNumber 表示させたいページ数
	 * @param itemList     絞り込み対象リスト
	 * @param model        リクエストスコープ
	 * @return 1ページに表示されるサイズ分の商品情報
	 */
	private List<Item> makeListPaging(int pagingNumber, List<Item> itemList, Model model) {
		// 表示させたいページ数を-1しなければうまく動かない
		pagingNumber--;
		// どの従業員から表示させるかと言うカウント値
		int startItemCount = pagingNumber * VIEW_SIZE;
		// 絞り込んだ後の商品リストが入る変数
		List<Item> list;
		// 該当ページに表示させる従業員一覧を作成
		int toIndex = Math.min(startItemCount + VIEW_SIZE, itemList.size());
		list = itemList.subList(startItemCount, toIndex);
		// 上記で作成した該当ページに表示させる従業員一覧をページングできる形に変換して返す
		Page<Item> itemPage = new PageImpl<Item>(list, PageRequest.of(pagingNumber, VIEW_SIZE), itemList.size());
		// リストにページ数を入れて、リクエストスコープに格納する
		List<Integer> pageNumbersOfFullItemList = calcPageNumbers(model, itemPage);
		model.addAttribute("pageNumbersOfFullItemList", pageNumbersOfFullItemList);

		// threeItemListに投げるためにListに詰め替える
		List<Item> pageItemList = new ArrayList<>();
		for (Item item : itemPage) {
			pageItemList.add(item);
		}

		return pageItemList;
	}

	/**
	 * ItemListをソートする.
	 * 
	 * @param itemList ソートしたいItemList
	 * @param form     並び替えフォーム
	 * @return ソート済みのitemList
	 */
	private List<Item> sortItemList(List<Item> itemList, SortConditionNumberForm form) {
		Comparator<Item> sortCondition = null;

		if ("0".equals(form.getSortConditionNumber())) {
			// Mサイズの価格の昇順でソートするComparator
			sortCondition = new Comparator<Item>() {
				@Override
				public int compare(Item item1, Item item2) {
					return item1.getPriceM().compareTo(item2.getPriceM());
				}
			};
		} else if ("1".equals(form.getSortConditionNumber())) {
			// Mサイズの価格の降順でソートするComparator
			sortCondition = new Comparator<Item>() {
				@Override
				public int compare(Item item1, Item item2) {
					return item2.getPriceM().compareTo(item1.getPriceM());
				}
			};
		} else if ("2".equals(form.getSortConditionNumber())) {
			// 口コミ評価点数の降順でソートするComparator
			sortCondition = new Comparator<Item>() {
				@Override
				public int compare(Item item1, Item item2) {
					return item2.getAveEvaluationTenfold().compareTo(item1.getAveEvaluationTenfold());
				}
			};
		} else if ("3".equals(form.getSortConditionNumber())) {
			// 口コミ件数の降順でソートするComparator
			sortCondition = new Comparator<Item>() {
				@Override
				public int compare(Item item1, Item item2) {
					return item2.getCountEvaluation().compareTo(item1.getCountEvaluation());
				}
			};
		}

		Collections.sort(itemList, sortCondition);
		return itemList;
	}

	/**
	 * 3個のItemオブジェクトを1セットにしてリストで返す.
	 * 
	 * @param itemList 商品リスト
	 * @return 3個1セットの商品リスト
	 */
	private List<List<Item>> getThreeItemList(List<Item> itemList) {
		List<List<Item>> itemListList = new ArrayList<>();
		List<Item> threeItemList = new ArrayList<>();

		for (int i = 1; i <= itemList.size(); i++) {
			threeItemList.add(itemList.get(i - 1));

			if (i % 3 == 0 || i == itemList.size()) {
				itemListList.add(threeItemList);
				threeItemList = new ArrayList<>();
			}
		}
		return itemListList;
	}

}
