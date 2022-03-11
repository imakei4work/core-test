package jp.co.hogehoge.framework.test.db;

import java.sql.SQLException;
import java.util.Objects;

import org.powermock.reflect.Whitebox;

import jp.co.hogehoge.framework.db.Entity;
import jp.co.hogehoge.framework.db.Sql;

/**
 * Sqlをモック化するクラス。
 * 
 * @param <P> SQL実行パラメータのデータ型
 * @param <R> SQL実行結果のデータ型
 */
public class SqlMocker<P extends Entity, R> {

	/** Sqlインスタンスを定義しているクラス */
	private Class<?> clazz = null;
	/** Sqlインスタンスの変数名 */
	private String field = null;
	/** モック化するSql Id */
	private String sqlId = null;
	/** モック化するロジック（パラメータ有） */
	private TransactionFunction<P, R> function = null;
	/** モック化するロジック（パラメータ無） */
	private TransactionSupplier<R> supplier = null;

	/**
	 * コンストラクタ。
	 * 
	 * @param clazz    Sqlインスタンスを定義しているクラス
	 * @param field    Sqlインスタンスの変数名
	 * @param sqlId    モック化するSql Id
	 * @param function モック化するロジック（パラメータ有）
	 * @param supplier モック化するロジック（パラメータ無）
	 */
	private SqlMocker(Class<?> clazz, String field, String sqlId, TransactionFunction<P, R> function,
			TransactionSupplier<R> supplier) {
		this.clazz = clazz;
		this.field = field;
		this.sqlId = sqlId;
		this.function = function;
		this.supplier = supplier;
	}

	/**
	 * モック化処理。
	 * 
	 * @param clazz    Sqlインスタンスを定義しているクラス
	 * @param field    Sqlインスタンスの変数名
	 * @param executor モック化するロジック（パラメータ有）
	 */
	public static <P extends Entity, R> void mockWith(Class<?> clazz, String field,
			TransactionFunction<P, R> executor) {
		(new SqlMocker<P, R>(clazz, field, null, executor, null)).apply();
	}

	/**
	 * モック化処理。
	 * 
	 * @param clazz    Sqlインスタンスを定義しているクラス
	 * @param field    Sqlインスタンスの変数名
	 * @param sqlId    モック化するSql Id
	 * @param executor モック化するロジック（パラメータ有）
	 */
	public static <P extends Entity, R> void mockWith(Class<?> clazz, String field, String sqlId,
			TransactionFunction<P, R> executor) {
		(new SqlMocker<P, R>(clazz, field, sqlId, executor, null)).apply();
	}

	/**
	 * モック化処理。
	 * 
	 * @param clazz    Sqlインスタンスを定義しているクラス
	 * @param field    Sqlインスタンスの変数名
	 * @param executor モック化するロジック（パラメータ無）
	 */
	public static <P extends Entity, R> void mockWith(Class<?> clazz, String field, TransactionSupplier<R> executor) {
		(new SqlMocker<P, R>(clazz, field, null, null, executor)).apply();
	}

	/**
	 * モック化処理。
	 * 
	 * @param clazz    Sqlインスタンスを定義しているクラス
	 * @param field    Sqlインスタンスの変数名
	 * @param sqlId    モック化するSql Id
	 * @param executor モック化するロジック（パラメータ無）
	 */
	public static <P extends Entity, R> void mockWith(Class<?> clazz, String field, String sqlId,
			TransactionSupplier<R> executor) {
		(new SqlMocker<P, R>(clazz, field, sqlId, null, executor)).apply();
	}

	/**
	 * モック処理を適用する。
	 */
	private void apply() {
		Whitebox.getInternalState(this.clazz, this.field);
		Whitebox.setInternalState(this.clazz, this.field, new Sql<P, R>() {

			@Override
			public R execute() throws SQLException {
				return Objects.nonNull(supplier) ? supplier.execute() : null;
			}

			@Override
			public R execute(P param) throws SQLException {
				return Objects.nonNull(function) ? function.execute(param) : null;
			}

			@Override
			public String getSql() {
				return null;
			}

			@Override
			public String getSqlId() {
				return sqlId;
			}

		});
	}

	/**
	 * モック処理インタフェース。
	 *
	 * @param <P> SQL実行パラメータのデータ型
	 * @param <R> SQL実行結果のデータ型
	 */
	public static interface TransactionFunction<P, R> {
		public R execute(P param) throws SQLException;
	}

	/**
	 * モック処理インタフェース。
	 * 
	 * @param <R> SQL実行結果のデータ型
	 */
	public static interface TransactionSupplier<R> {
		public R execute() throws SQLException;
	}

}
