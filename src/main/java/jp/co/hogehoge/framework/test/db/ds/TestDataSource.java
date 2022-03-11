package jp.co.hogehoge.framework.test.db.ds;

import java.io.Closeable;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Objects;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * UT用のデータ・ソースクラス。
 */
public class TestDataSource implements DataSource, Closeable {

	/** ホスト名 */
	private String host = null;
	/** ポート番号 */
	private String port = null;
	/** データベース名 */
	private String dbName = null;
	/** ユーザー名 */
	private String userName = null;
	/** パスワード */
	private String password = null;
	/** 接続オプション */
	private String option = "";
	/** コネクション（スレッド毎に管理） */
	private static final ThreadLocal<Connection> CONNECTION = new ThreadLocal<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param host     ホスト名
	 * @param port     ポート番号
	 * @param dbName   データベース名
	 * @param userName ユーザー名
	 * @param password パスワード
	 * @param option   接続オプション
	 */
	public TestDataSource(String host, String port, String dbName, String userName, String password, String option) {
		super();
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;
		this.option = option;
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param host     ホスト名
	 * @param port     ポート番号
	 * @param dbName   データベース名
	 * @param userName ユーザー名
	 * @param password パスワード
	 */
	public TestDataSource(String host, String port, String dbName, String userName, String password) {
		this(host, port, dbName, userName, password, "");
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		Connection conn = CONNECTION.get();
		if (Objects.isNull(conn) || conn.isClosed()) {
			String connectUrl = this.host + ":" + this.port + "/" + this.dbName + ":" + this.option;
			conn = DriverManager.getConnection(connectUrl, this.userName, this.password);
			CONNECTION.set(conn);
		}
		return conn;
	}

	@Override
	public synchronized Connection getConnection(String username, String password) throws SQLException {
		return getConnection();
	}

	@Override
	public void close() {
		Connection conn = CONNECTION.get();
		try {
			if (Objects.nonNull(conn)) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}
}
