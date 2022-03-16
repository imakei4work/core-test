package jp.co.hogehoge.framework.test.db;

import javax.naming.InitialContext;

import jp.co.hogehoge.framework.test.db.ds.TestContextFactory;
import jp.co.hogehoge.framework.test.db.ds.TestDataSource;

/**
 * UT用のDB設定クラス。
 */
public class TestDB {

	/**
	 * UTでDBを使用するためのセットアップ処理を行う。
	 * 
	 * @param dsName   データ・ソース名
	 * @param host     ホスト名
	 * @param port     ポート番号
	 * @param dbName   データベース名
	 * @param userName ユーザー名
	 * @param password パスワード
	 * @param option   オプション
	 */
	public static void setup(String dsName, String host, String port, String dbName, String userName, String password,
			String option) {

		// システム・プロパティの設定
		System.setProperty("java.naming.factory.initial", TestContextFactory.class.getName());

		// データ・ソースの設定
		try {
			InitialContext context = new InitialContext();
			context.bind(dsName, new TestDataSource(host, port, dbName, userName, password, option));
		} catch (Exception e) {
			throw new RuntimeException("データ・ソースの設定に失敗しました。", e);
		}
	}

}
