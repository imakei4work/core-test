package jp.co.hogehoge.framework.test.db.mock;

import javax.naming.InitialContext;

import jp.co.hogehoge.framework.test.db.ds.mock.MockContextFactory;
import jp.co.hogehoge.framework.test.db.ds.mock.MockDataSource;

/**
 * UT用のDBをモック化するクラス。
 */
public class MockDB {

	/**
	 * DBのモック化を行う。
	 */

	/**
	 * DBのモック化を行う。
	 * 
	 * @param dsName データ・ソース名
	 */
	public static void setup(String dsName) {

		// システム・プロパティの設定
		System.setProperty("java.naming.factory.initial", MockContextFactory.class.getName());

		// データ・ソースの設定
		try {
			InitialContext context = new InitialContext();
			context.bind(dsName, new MockDataSource());
		} catch (Exception e) {
			throw new RuntimeException("データ・ソースの設定に失敗しました。", e);
		}

	}

}
