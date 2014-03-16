package com.qrrest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.qrrest.dao2.SQLExecution;

abstract class BaseDao<T> {

	private SQLExecution sqlE = new SQLExecution();

	protected SQLExecution getSqlExecution() {
		return sqlE;
	}

	protected abstract T parseRS(ResultSet rs) throws SQLException;

	protected T parseRsToModel(ResultSet rs) {
		try {
			if (rs.next()) {
				return parseRS(rs);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected List<T> parseRsToList(ResultSet rs) {
		List<T> result = new LinkedList<T>();
		try {
			while (rs.next()) {
				result.add(parseRS(rs));
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected int parseInt(ResultSet rs) {
		try {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	protected double parseDouble(ResultSet rs) {
		try {
			if (rs.next()) {
				return rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	protected String parseString(ResultSet rs) {
		try {
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected int getLastInsertId() {
		String sql = "select LAST_INSERT_ID()";
		Object[] params = {};
		return parseInt(getSqlExecution().execSqlWithRS(sql, params));
	}
}
