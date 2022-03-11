package jp.co.hogehoge.framework.test.db.ds;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

/**
 * UT用のコンテキスト・ファクトリークラス。
 * システムプロパティ「java.naming.factory.initial」に指定するクラス。
 */
public class TestContextFactory implements InitialContextFactory {

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
		// モックのコンテキストを返す
		return new TestContext();
	}

}
