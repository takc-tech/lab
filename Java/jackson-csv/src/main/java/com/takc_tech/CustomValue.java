package com.takc_tech;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * 値オブジェクトのサンプル
 */
public class CustomValue {

	private String originalValue;

	public CustomValue(String originalValue) {
		this.originalValue = originalValue;
	}

	@Override
	public String toString() {
		return "@" + originalValue;
	}

	/**
	 * CSVから取得した値から、値オブジェクトを生成するクラス
	 */
	public static class CustomValueDeserializer extends StdDeserializer<CustomValue> {

		public CustomValueDeserializer() {
			this(null);
		}

		public CustomValueDeserializer(Class<?> vc) {
			super(vc);
		}

		@Override
		public CustomValue deserialize(JsonParser jp, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			return new CustomValue(jp.getText());
		}
	}
}
