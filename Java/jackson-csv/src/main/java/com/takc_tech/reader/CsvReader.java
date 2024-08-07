package com.takc_tech.reader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.takc_tech.bean.AnnotatedBean;
import com.takc_tech.bean.UnannotatedBean;

public class CsvReader {

	/**
	 * Beanのアノテーション定義に基づきマッピングする
	 * @param path 読込対象ファイルパス
	 * @return List of entries read
	 * @throws IOException
	 */
	public static List<AnnotatedBean> readUsingAnnotation(Path path) throws IOException {

		var mapper = new CsvMapper();
		var csvSchema = mapper
				.schemaFor(AnnotatedBean.class)
				.withHeader();

		MappingIterator<AnnotatedBean> mappingIterator = mapper.readerFor(AnnotatedBean.class)
				.with(csvSchema)
				.readValues(path.toFile());

		return mappingIterator.readAll();
	}

	/**
	 * CSVの並びを外部から取得し、その定義に基づいて読み込む
	 * @param path 読込対象ファイルパス
	 * @return List of entries read
	 * @throws IOException
	 */
	public static List<UnannotatedBean> readUsingExternalDefinition(Path path) throws IOException {

		List<String> columnOrder = fetchColumnOrder();

		var schemaBuilder = CsvSchema.builder();
		for (String columnName : columnOrder) {
			schemaBuilder.addColumn(columnName);
		}
		var schema = schemaBuilder.build().withHeader();

		var mapper = new CsvMapper();

		// 値オブジェクトへのマッピング方法を登録
		// 登録しなくても何故かマッピングされた
		// 
		// SimpleModule module = new SimpleModule();
		// module.addDeserializer(CustomValue.class, new CustomValue.CustomValueDeserializer());
		// mapper.registerModule(module);

		MappingIterator<UnannotatedBean> mappingIterator = mapper.readerFor(UnannotatedBean.class)
				.with(schema)
				.readValues(path.toFile());

		return mappingIterator.readAll();
	}

	/**
	 * CSVの並びを取得する
	 * @return CSVの並び順をマッピング対象のメンバー名で返却。
	 */
	private static List<String> fetchColumnOrder() {
		// DBやファイルなどから取得
		List<String> columnOrder = new ArrayList<>();
		columnOrder.add("value3");
		columnOrder.add("value1");
		columnOrder.add("value2");
		return columnOrder;
	}

	/**
	 * CSVヘッダーの値に基づいてメンバーにマッピングする
	 * @param path 読込対象ファイルパス
	 * @return List of entries read
	 * @throws IOException
	 */
	public static List<UnannotatedBean> readUsingHeader(Path path) throws IOException {

		var mapper = new CsvMapper();
		mapper.setPropertyNamingStrategy(new CustomStrategy());

		var schema = CsvSchema.builder().build().withHeader();

		MappingIterator<UnannotatedBean> mappingIterator = mapper.readerFor(UnannotatedBean.class)
				.with(schema)
				.readValues(path.toFile());

		return mappingIterator.readAll();

	}

	/**
	 * ヘッダー名から対応するメンバー名を取得するためのクラス
	 */
	private static class CustomStrategy extends PropertyNamingStrategy {

		@Override
		public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
			return getHeaderName(defaultName);
		}

		@Override
		public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
			return getHeaderName(defaultName);
		}

		@Override
		public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
			return getHeaderName(defaultName);
		}

		private String getHeaderName(String defaultName) {
			// フィールド名から対応するヘッダー名を取得
			// ここでは「fieldN」を「valueN」にマッピングする
			return defaultName.replace("value", "field");
		}
	}
}
