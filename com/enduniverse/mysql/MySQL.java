package com.enduniverse.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {
	
	private Connection connection;
	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;
	
	public MySQL(String hostname, String port, String database, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
		this.connection = null;
	}
	
	public void open() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database,this.user, this.password);
	}
	
	public void close() throws SQLException {
		getConnection().close();
	}
	
	public boolean isConnected() throws SQLException {
		return getConnection() != null & !getConnection().isClosed();
	}
	
	public Connection getConnection() throws SQLException {
		if(!isConnected()) open();
		return connection;
	}
	
	public ResultSet query(String query) throws SQLException {
		if (!isConnected()) {
			open();
		}
		return getConnection().createStatement().executeQuery(query);
	}
	
	public void update(String query) throws SQLException {
		if (!isConnected()) {
			open();
		}
		getConnection().createStatement().executeUpdate(query);
	}
	
	public MySQLPrepared prepare(String query) throws SQLException {
		return new MySQLPrepared(this, query);
	}
	
}
