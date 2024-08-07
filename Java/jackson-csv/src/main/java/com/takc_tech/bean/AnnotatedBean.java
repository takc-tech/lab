package com.takc_tech.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.takc_tech.CustomValue;
import com.takc_tech.CustomValue.CustomValueDeserializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * アノテーションを使用するマッピング検証用Bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
		"field1",
		"field2",
		"field3",
})
public class AnnotatedBean {

	@JsonProperty("field1")
	private String value1;

	@JsonProperty("field2")
	private String value2;

	@JsonProperty("field3")
	@JsonDeserialize(using = CustomValueDeserializer.class)
	private CustomValue value3;
}