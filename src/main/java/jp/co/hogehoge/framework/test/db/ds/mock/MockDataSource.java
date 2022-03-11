package jp.co.hogehoge.framework.test.db.ds.mock;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * データ・ソースのモック。
 */
public class MockDataSource implements DataSource, Closeable {

	@Override
	public Connection getConnection() throws SQLException {
		// モックのコネクションを返す
		return (Connection) new MockConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// モックのコネクションを返す
		return (Connection) new MockConnection();
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

	@Override
	public void close() throws IOException {
	}

}
