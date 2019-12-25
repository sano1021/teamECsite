package com.internousdev.laravel.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class CreateUserAction extends ActionSupport implements SessionAware {
	private Map<String, Object> session;
	private String backFlag;

	public String execute() {

		// セッションの破棄
		if (backFlag == null) {
			session.remove("family_name");
			session.remove("first_name");
			session.remove("family_name_kana");
			session.remove("first_name_kana");
			session.remove("sex");
			session.remove("sexList");
			session.remove("email");
			session.remove("user_id_create");
			session.remove("password");
		}

		if (!session.containsKey("sex")) {
			session.put("sex", "男性");
		} else {
			session.put("sex", String.valueOf(session.get("sex")));
		}

		if (!session.containsKey("sexList")) {
			List<String> sexList = new ArrayList<String>();
			sexList.add("男性");
			sexList.add("女性");
			session.put("sexList", sexList);
		}
		return SUCCESS;
	}

	public String getBackFlag(String backFlag) {
		return backFlag;
	}

	public void setBackFlag(String backFlag) {
		this.backFlag = backFlag;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
