package com.internousdev.laravel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.internousdev.laravel.dto.UserInfoDTO;
import com.internousdev.laravel.util.DBConnector;

public class UserInfoDAO {

	// ユーザー登録：ユーザーIDの存在チェック
	public boolean getUserId(String user_id) throws SQLException {

		// データベースに接続
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		boolean existUser = false;

		// データの抽出
		String sql = "SELECT COUNT(*) AS count FROM user_info WHERE user_id=?";

		// もしデータが存在した場合エラー なかった場合登録へ
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user_id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt("count") > 0) {
					existUser = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.close();
		}
		return existUser;
	}

	// ユーザー登録：ユーザー情報を登録する
	public int createUser(String family_name, String first_name, String family_name_kana, String first_name_kana,
			String sex, String email, String user_id, String password) throws SQLException {

		int count = 0;

		// データベースに接続する
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		// データベースにデータを登録
		String sql = "INSERT INTO user_info(user_id, password, family_name, first_name, family_name_kana, "
				+ "first_name_kana, sex,email, status, logined, regist_date, update_date) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,now(),now())";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user_id);
			ps.setString(2, password);
			ps.setString(3, family_name);
			ps.setString(4, first_name);
			ps.setString(5, family_name_kana);
			ps.setString(6, first_name_kana);
			ps.setString(7, sex);
			ps.setString(8, email);
			ps.setInt(9, 0);
			ps.setInt(10, 1);
			count = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.close();
		}
		return count;
	}

	// ログイン認証
	public boolean getUserInfo(String userId, String password) throws SQLException {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		boolean result = false;

		String sql = "SELECT COUNT(*) AS count FROM user_info WHERE user_id=? AND password=?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getInt("count") > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.close();
		}
		return result;
	}

	// ユーザー情報の取得(id)
	public UserInfoDTO getUserInfoId(String user_id) throws SQLException {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		UserInfoDTO dto = new UserInfoDTO();

		String sql = "SELECT * FROM user_info where user_id=?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user_id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				dto.setFamilyName(rs.getString("family_name"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setFamilyNameKana(rs.getString("family_name_kana"));
				dto.setFirstNameKana(rs.getString("first_name_kana"));
				dto.setSex(rs.getByte("sex"));
				dto.setEmail(rs.getString("email"));
				dto.setUserId(rs.getString("user_id"));
				dto.setPassword(rs.getString("password"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.close();
		}
		return dto;
	}

	// パスワードの再設定：パスワードの入力値を更新
	public int updatePassword(String user_id, String password) throws SQLException {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		String sql = "UPDATE user_info SET password=?, update_date=now() WHERE user_id=?";

		int result = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, password);
			ps.setString(2, user_id);

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.close();
		}

		return result;
	}
}
