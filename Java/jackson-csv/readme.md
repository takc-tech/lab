# JacksonによるCSV読込サンプル

含まれるサンプルは下記。

- Beanのアノテーション定義に基づき読み込む方法
- CSVの並びを外部から取得し、その定義に基づいて読み込む方法
- CSVヘッダーの値に基づいてメンバーにマッピングする方法
- 値オブジェクトへマッピングする方法

以下はその他のメモ。

## 空行を無視する

```java
// https://fasterxml.github.io/jackson-dataformats-text/javadoc/csv/2.9/com/fasterxml/jackson/dataformat/csv/CsvParser.Feature.html
var mapper = new CsvMapper();
mapper.configure(CsvParser.Feature.SKIP_EMPTY_LINES, true);
```

## アノテーションを使用しないマッピングで起こりうるエラー

CSVのヘッダーに想定外の値が入っている場合、下記の例外が発生しうる。

```
com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException

# メッセージ例
Unrecognized field "field4" (class com.takc_tech.bean.UnannotatedBean), not marked as ignorable (3 known properties: "field2", "field3", "field1"])
```

下記を設定すれば想定外の値は無視されて、例外は発生しなくなる。

```java
mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
```

## リンク

[API](https://fasterxml.github.io/jackson-dataformats-text/javadoc/csv/2.9/index.html)