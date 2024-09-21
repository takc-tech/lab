import java.io.File;
import java.nio.file.Path;

/**
 * 多態性を使用してif文を消すサンプル
 * 指定したデータフォーマットでファイルを読み書きするという例。
 */
public class Main {

	/**
	 * 悪い例
	 * - 同一リテラルを繰り返すな。定数か列挙型を使え。
	 * - フォーマット区分はStringではなく列挙型を使え。
	 *     - Stringだと何を設定していいか分からないし、何でも設定できる。
	 *     - 列挙型ならば、そこに定義しているものしか設定は出来ない。
	 * - データフォーマットを追加する度、使用箇所全てのif文に条件を追加しなければならない。
	 *     - 修正ミスが発生しかねない
	 *     - 修正箇所の調査をしなければならない
	 *     - 修正漏れが発生するかもしれない
	 * - if文が多く、可読性に欠ける。
	 */
	void badPattern(String formatKbn, File file) {

		// 読込フェーズ
		Data data = null;
		if ("JSON".equals(formatKbn)) {
			// JSON形式のデータを読み込む
		} else if ("XML".equals(formatKbn)) {
			// XML形式のデータを読み込む
		}

		// データを加工
		@SuppressWarnings("unused")
		var convertedData = convertData(data);

		// 書き込みフェーズ
		if ("JSON".equals(formatKbn)) {
			// JSON形式でデータを書き込む
		} else if ("XML".equals(formatKbn)) {
			// XML形式でデータを書き込む
		}
	}

	/**
	 * 良い例
	 * - if文は消える
	 * - データフォーマットを追加する際
	 *     - このメソッドへの改修は発生しない。(DIP)
	 *     - DataFormat列挙型を追加して、DataProcessorを実装したクラスを作るだけ(OCP)
	 */
	void goodPattern(DataFormat dataFormat, File file) {

		DataProcessor processor = DataProcessor.of(dataFormat);
		Data data = processor.read(file);

		var convertedData = convertData(data);
		processor.write(convertedData, Path.of("保存先"));
	}

	/**
	 * データを加工する
	 * 説明用なので実装は無い
	 */
	Data convertData(Data data) {
		return data;
	}
}

/**
 * データフォーマット
 */
enum DataFormat {
	JSON, XML
}

class Data {
}

interface DataProcessor {

	/**
	 * ファイル読込
	 * @param file 読み込むファイル
	 * @return
	 */
	Data read(File file);

	/**
	 * ファイル書込
	 * @param data 保存するデータ
	 * @param path 保存先
	 */
	void write(Data data, Path path);

	/**
	 * データフォーマットから対応するプロセッサーを生成
	 * @param dataFormat データフォーマット
	 * @return
	 */
	static DataProcessor of(DataFormat dataFormat) {

		if (dataFormat == DataFormat.JSON) return new JsonDataProcessor();
		if (dataFormat == DataFormat.XML) return new XmlDataProcessor();

		throw new IllegalArgumentException("不正データフォーマット");
	}
}

/**
 * JSON用のプロセッサー
 * 説明用なので実装は無い
 */
class JsonDataProcessor implements DataProcessor {

	@Override
	public Data read(File file) {
		return null;
	}

	@Override
	public void write(Data data, Path path) {}
}

/**
 * XML用のプロセッサー
 * 説明用なので実装は無い
 */
class XmlDataProcessor implements DataProcessor {

	@Override
	public Data read(File file) {
		return null;
	}

	@Override
	public void write(Data data, Path path) {}
}