package com.takc_tech.bean;

import com.takc_tech.CustomValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * アノテーションを使用しない場合のマッピング検証用Bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnannotatedBean {

	private String value1;

	private String value2;

	private CustomValue value3;
}