package jp.co.hogehoge.framework.test.db.ds.mock;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

/**
 * コンテキスト・ファクトリーのモック。
 */
public class MockContextFactory implements InitialContextFactory {

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
		// モックのコンテキストを返す
		return new MockContext();
	}
}
