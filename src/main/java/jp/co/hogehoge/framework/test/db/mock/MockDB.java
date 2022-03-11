package jp.co.hogehoge.framework.test.db.mock;

import javax.naming.InitialContext;

import jp.co.hogehoge.framework.db.Config;
import jp.co.hogehoge.framework.test.db.ds.mock.MockContextFactory;
import jp.co.hogehoge.framework.test.db.ds.mock.MockDataSource;

/**
 * UT用のDBをモック化するクラス。
 */
public class MockDB {

	/**
	 * DBのモック化を行う。
	 */
	public static void setup() {

		// システム・プロパティの設定
		System.setProperty("java.naming.factory.initial", MockContextFactory.class.getName());

		// データ・ソースの設定
		try {
			InitialContext context = new InitialContext();
			context.bind(Config.DATA_SOURCE_PATH.get(), new MockDataSource());
		} catch (Exception e) {
			throw new RuntimeException("データ・ソースの設定に失敗しました。", e);
		}

	}

}
