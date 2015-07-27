package com.enduniverse.mysql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.plugin.Plugin;

public class MySQL {
	
	private Connection connection;
	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;
	private boolean lite;
	private Plugin plugin;
	private String dbl;
	
	public MySQL(String hostname, String port, String database, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
		this.connection = null;
		this.lite = false;
	}
	
	public void openLite(Plugin plugin, String dbl) throws SQLException {
		this.lite = true;
		this.plugin = plugin;
		this.dbl = dbl;
		if (isConnected()){
			return;
		}
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdirs();
		}
		File file = new File(plugin.getDataFolder(), dbl);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().getPath().toString() + "/" + dbl);
		}
	
	public void open() throws SQLException {
		if(lite){
			openLite(plugin, dbl);
			return;
		}
		if(isConnected()) connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database,this.user, this.password);
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
	
