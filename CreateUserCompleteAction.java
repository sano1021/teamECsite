package com.internousdev.laravel.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.laravel.dao.UserInfoDAO;
import com.opensymphony.xwork2.ActionSupport;

public class CreateUserCompleteAction extends ActionSupport implements SessionAware {

	private Map<String, Object> session;

	// 入力された値を表示
	public String execute() throws SQLException {

		String result = ERROR;
		String sex = null;

		if ("女性".equals(String.valueOf(session.get("sex")))) {
			sex = "1";
		} else {
			sex = "0";
		}

		// 格納したデータを取得
		UserInfoDAO userInfoDAO = new UserInfoDAO();
		int count = userInfoDAO.createUser(session.get("family_name").toString(), session.get("first_name").toString(),
				session.get("family_name_kana").toString(), session.get("first_name_kana").toString(), sex,
				session.get("email").toString(), session.get("user_id_create").toString(),
				session.get("password").toString());

		// 成功したらユーザー情報入力完了画面に遷移
		if (count > 0) {
			result = SUCCESS;
			session.put("create_user_flg", 1);
		}

		// セッション削除
		session.remove("family_name");
		session.remove("first_name");
		session.remove("family_name_kana");
		session.remove("first_name_kana");
		session.remove("sex");
		session.remove("sexList");
		session.remove("email");
		session.remove("password");

		return result;
	}

	public Map<String, Object> getSession() {
		return this.session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
