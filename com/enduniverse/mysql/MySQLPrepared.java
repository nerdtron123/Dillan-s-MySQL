package com.enduniverse.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLPrepared {

	private MySQL mysql;
	private PreparedStatement ps;
	
	public MySQLPrepared(MySQL mysql, String query) throws SQLException{
		this.mysql = mysql;
		this.ps = mysql.getConnection().prepareStatement(query);
	}

	public void setObject(int i, Object o) throws SQLException {
		ps.setObject(i, o);
	}
	public void setInt(int i, int o) throws SQLException {
		ps.setInt(i, o);
	}
	public void setString(int i, String o) throws SQLException {
		ps.setString(i, o);
	}
	
	public ResultSet query(String query) throws SQLException {
		if (!mysql.isConnected()) {
			mysql.open();
		}
		return ps.executeQuery();
	}
	
	public void update(String query) throws SQLException {
		if (!mysql.isConnected()) {
			mysql.open();
		}
		ps.executeUpdate();
	}
	
}
