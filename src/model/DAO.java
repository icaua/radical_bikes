package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.26.49.209:3306/dbradicalmotos";
	private String user = "dba";
	private String password = "123@senac";
	private Connection con;

	/**
	 * Método responsável pela conexão com o banco
	 * @return con
	 */
	public Connection conectar() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
