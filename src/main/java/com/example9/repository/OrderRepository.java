package com.example9.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example9.domain.Item;
import com.example9.domain.Order;
import com.example9.domain.OrderItem;
import com.example9.domain.OrderTopping;
import com.example9.domain.Topping;

/**
 * 注文情報を扱うリポジトリ.
 * 
 * @author mayumiono
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("orders");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	private ResultSetExtractor<List<Order>> RESULT_SET_EXTRACTOR = (rs) -> {
		List<Order> orderList = new ArrayList<>();
		List<OrderItem> orderItemList;
		List<OrderTopping> orderToppingList;

		Integer orderIdOneBefore = 0;
		Integer orderItemIdOneBefore = 0;
		while (rs.next()) {

			if (rs.getInt("order_id") != orderIdOneBefore) {
				// 注文情報の作成
				Order order = new Order();
				order.setId(rs.getInt("order_id"));
				order.setUserId(rs.getInt("user_id"));
				order.setStatus(rs.getInt("status"));
				order.setTotalPrice(rs.getInt("total_price"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setDestinationName(rs.getString("destination_name"));
				order.setDestinationEmail(rs.getString("destination_email"));
				order.setDestinationZipcode(rs.getString("destination_zipcode"));
				order.setDestinationAddress(rs.getString("destination_address"));
				order.setDestinationTel(rs.getString("destination_tel"));
				order.setPaymentMethod(rs.getInt("payment_method"));
				orderItemList = new ArrayList<>();
				order.setOrderItemList(orderItemList);
				// 注文を注文リストに追加
				orderList.add(order);
				// 参照済み注文のIDを変数に格納
				orderIdOneBefore = rs.getInt("order_id");
			}

			if (rs.getInt("order_item_id") != orderItemIdOneBefore) {
				// 商品情報の作成
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("order_item_id"));
				orderItem.setItemId(rs.getInt("item_id"));
				orderItem.setOrderId(rs.getInt("order_id"));
				orderItem.setQuantity(rs.getInt("quantity"));
				String size = rs.getString("size");
				orderItem.setSize(size.toCharArray()[0]);
				Item item = new Item();
				item.setId(rs.getInt("item_id"));
				item.setName(rs.getString("item_name"));
				item.setPriceM(rs.getInt("item_price_m"));
				item.setPriceL(rs.getInt("item_price_l"));
				item.setImagePath(rs.getString("image_path"));
				orderItem.setItem(item);
				orderToppingList = new ArrayList<>();
				orderItem.setOrderToppingList(orderToppingList);
				try {
					// findByOrderId()実行時は口コミIDを取得する
					Integer reviewId = rs.getInt("review_id");
					orderItem.setReviewId(reviewId);
				} catch (Exception e) {
					// findByOrderId()以外のメソッド実行時は口コミID取得しない
				}
				// 注文内の商品リストに商品情報追加
				List<OrderItem> orderItemListOfThisOrder = orderList.get(orderList.size() - 1).getOrderItemList();
				orderItemListOfThisOrder.add(orderItem);
				// 参照済商品のIDを変数に格納
				orderItemIdOneBefore = rs.getInt("order_item_id");
			}

			// toppingIdが存在する場合は、最新の商品情報のトッピングリストにトッピング情報を追加する
			if (rs.getInt("topping_id") > 0) {
				// トッピング情報作成
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("order_toppings_id"));
				orderTopping.setToppingId(rs.getInt("topping_id"));
				orderTopping.setOrderItemId(rs.getInt("order_item_id"));
				Topping topping = new Topping();
				topping.setId(rs.getInt("topping_id"));
				topping.setName(rs.getString("toppings_name"));
				topping.setPriceM(rs.getInt("topping_price_m"));
				topping.setPriceL(rs.getInt("topping_price_l"));
				orderTopping.setTopping(topping);

				// 商品リスト内のトッピングリストにトッピング情報追加
				List<OrderItem> orderItemListOfThisOrder = orderList.get(orderList.size() - 1).getOrderItemList();
				List<OrderTopping> orderToppingListOfThisItem = orderItemListOfThisOrder
						.get(orderItemListOfThisOrder.size() - 1).getOrderToppingList();
				orderToppingListOfThisItem.add(orderTopping);
			}

		}

		return orderList;
	};

	/**
	 * 引数のユーザーIDとステータスで、注文情報を注文ID降順で取得する.
	 * 
	 * @param userId ユーザーID
	 * @param status ステータス
	 * @return 注文情報一覧(該当データなしの場合null)
	 */
	public List<Order> findByUserIdAndStatus(Integer userId, Integer status) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT A.id AS order_id, A.user_id, A.status, A.total_price, A.order_date, A.destination_name, A.destination_email, ");
		sql.append(
				"A.destination_zipcode, A.destination_address, A.destination_tel, A.delivery_time, A.payment_method, ");
		sql.append(
				"F.order_item_id, F.item_id, F.quantity, F.size, F.item_name, F.item_price_m, F.item_price_l, F.image_path, ");
		sql.append(
				"G.order_toppings_id, G.topping_id, G.order_item_id, G.toppings_name, G.topping_price_m, G.topping_price_l ");
		sql.append("FROM orders AS A JOIN ");
		sql.append("(SELECT B.id AS order_item_id, B.item_id, B.order_id, B.quantity, B.size, ");
		sql.append("C.name AS item_name, C.price_m AS item_price_m, C.price_l AS item_price_l, C.image_path ");
		sql.append("FROM order_items AS B JOIN items AS C ON B.item_id=C.id) AS F ");
		sql.append("ON A.id=F.order_id LEFT OUTER JOIN ");
		sql.append("(SELECT D.id AS order_toppings_id, D.topping_id, D.order_item_id, ");
		sql.append("E.name AS toppings_name, E.price_m AS topping_price_m, E.price_l AS topping_price_l ");
		sql.append("FROM order_toppings AS D JOIN toppings AS E ");
		sql.append("ON D.topping_id=E.id) AS G ON F.order_item_id=G.order_item_id ");
		sql.append("WHERE A.user_id=:id ");

		SqlParameterSource param;
		int statusInt = status;
		if (statusInt == 0) {
			sql.append("AND A.status=:status ORDER BY A.id DESC; ");
			param = new MapSqlParameterSource().addValue("id", userId).addValue("status", status);
		} else {
			sql.append("AND NOT A.status=0 ORDER BY A.id DESC; ");
			param = new MapSqlParameterSource().addValue("id", userId);
		}

		List<Order> orderList = template.query(sql.toString(), param, RESULT_SET_EXTRACTOR);
		if (orderList.size() == 0) {
			return null;
		}
		return orderList;
	}

	/**
	 * 注文IDで注文情報を検索・取得する.
	 * 
	 * @param orderId 注文ID
	 * @return 注文情報
	 */
	public List<Order> findByOrderId(Integer orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A.id AS order_id, A.user_id, A.status, A.total_price, A.order_date, A.destination_name, ");
		sql.append(
				"A.destination_email, A.destination_zipcode, A.destination_address, A.destination_tel, A.delivery_time, ");
		sql.append("A.payment_method, F.order_item_id, F.item_id, F.quantity, F.size, F.item_name, F.item_price_m, ");
		sql.append("F.item_price_l, F.image_path, G.order_toppings_id, G.topping_id, G.order_item_id, ");
		sql.append("G.toppings_name, G.topping_price_m, G.topping_price_l, F.review_id ");
		sql.append("FROM orders AS A LEFT OUTER JOIN ");
		sql.append("(SELECT B.id AS order_item_id, B.item_id, B.order_id, B.quantity, B.size, ");
		sql.append(
				"C.name AS item_name, C.price_m AS item_price_m, C.price_l AS item_price_l, C.image_path, H.id AS review_id ");
		sql.append("FROM order_items AS B JOIN items AS C ON B.item_id=C.id ");
		sql.append("LEFT OUTER JOIN reviews AS H ON B.id=H.order_item_id) AS F ");
		sql.append("ON A.id=F.order_id LEFT OUTER JOIN ");
		sql.append("(SELECT D.id AS order_toppings_id, D.topping_id, D.order_item_id, ");
		sql.append("E.name AS toppings_name, E.price_m AS topping_price_m, E.price_l AS topping_price_l ");
		sql.append("FROM order_toppings AS D JOIN toppings AS E ");
		sql.append("ON D.topping_id=E.id) AS G ON F.order_item_id=G.order_item_id ");
		sql.append("WHERE A.id=:id;");

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", orderId);

		List<Order> orderList = template.query(sql.toString(), param, RESULT_SET_EXTRACTOR);
		return orderList;
	}

	/**
	 * DBにorderオブジェクトを追加.
	 * 
	 * @param order orderオブジェクト
	 * @return
	 */
	public Order insertOrder(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);

		if (order.getId() == null) {
			Number key = insert.executeAndReturnKey(param);
			order.setId(key.intValue());
		} else {
			String sql = "INSERT INTO orders "
					+ "(user_id, status, total_price, order_date, destination_name, destination_email, "
					+ "destination_zipcode, destination_address, destination_tel, delivery_time, payment_method) "
					+ "VALUES (:userId, :status, :totalPrice, :orderDate, :destinationName, :destinationEmail, "
					+ ":destinationZipcode, :destinationAddress, :destinationTel, :deliveryTime, :paymentMethod) ";
			template.update(sql, param);
		}
		return order;
	}

	/**
	 * 主キーを元にDBから削除.
	 * 
	 * @param id 主キー
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM orders WHERE id = :id ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

	/**
	 * 引数の注文情報に該当するデータの、ステータスを更新する.
	 * 
	 * @param order 注文情報
	 */
	public void updateStatus(Order order) {
		String sql = "UPDATE orders SET status=:status WHERE id=:orderId;";
		SqlParameterSource param;
		int paymentMethod = order.getPaymentMethod();
		if (paymentMethod == 1) {
			// 決済方法が代金引換（paymentMethod=1）の場合はステータスを注文前(0)→未入金(1)へ更新
			param = new MapSqlParameterSource().addValue("status", 1).addValue("orderId", order.getId());
		} else {
			// 決済方法が代金引換（paymentMethod=1）以外の場合はステータスを注文前(0)→入金済(2)へ更新
			param = new MapSqlParameterSource().addValue("status", 2).addValue("orderId", order.getId());
		}
		template.update(sql, param);
	}

	/**
	 * 注文確認画面で入力されたユーザ情報を更新する.
	 * 
	 * @param order 注文情報
	 */
	public void updateByUserId(Order order) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE orders SET destination_name = :destinationName, destination_email = :destinationEmail, ");
		sql.append("destination_zipcode = :destinationZipcode, destination_address = :destinationAddress, ");
		sql.append(
				"destination_tel = :destinationTel , delivery_time = :deliveryTime, payment_method = :paymentMethod, ");
		sql.append("order_date = :orderDate, total_price = :totalPrice WHERE user_id = :userId AND status = 0");

		SqlParameterSource param = new BeanPropertySqlParameterSource(order);

		template.update(sql.toString(), param);
	}
	
	
	/**
	 *　Orderテーブルのuser_idをログインユーザのものに更新する
	 * 
	 * @param order ログイン前のショッピングカートの中身
	 */
	public void updateUserId(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE orders SET user_id = :userId WHERE status = 0 ");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		template.update(sql.toString(), param);
	}
	
	
	/**
	 * Orderを削除し、OrderItemのorder_idを更新する.
	 * ログイン前のカートの中身をログイン後カートに反映させるためのメソッド
	 * 
	 * @param id 主キー
	 */
	public void deleteOrderAndUpdateOrderItem(Integer id, Integer userId) {
		String sql = "WITH deleted AS (DELETE FROM orders WHERE id = :id RETURNING id) " +
				 	 "UPDATE order_items SET order_id = (SELECT id FROM orders WHERE user_id = :userId AND status = 0) "+ 
				 	 "WHERE order_id IN (SELECT id FROM deleted) ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id).addValue("userId", userId);
		template.update(sql, param);
	}
	
}