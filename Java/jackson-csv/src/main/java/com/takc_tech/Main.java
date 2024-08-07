package com.takc_tech;

import java.io.IOException;
import java.nio.file.Path;

import com.takc_tech.reader.CsvReader;

public class Main {

	public static void main(String args[]) throws IOException {

		String csvFilePath = "./csv/sample.csv";

		// マッピング対象Beanのアノテーションを使用してマッピング
		System.out.println(CsvReader.readUsingAnnotation(Path.of(csvFilePath)));

		// DBや外部ファイルからカラムの並びを取得してマッピング
		System.out.println(CsvReader.readUsingExternalDefinition(Path.of(csvFilePath)));

		// CSVのヘッダーの値を使用してマッピング
		System.out.println(CsvReader.readUsingHeader(Path.of(csvFilePath)));
	}

}
