export default class I18n {

  /** デフォルト */
  private defaultDefinition;
  /** カスタム */
  private customDefinition;

  constructor() {
    this.defaultDefinition = require("./default.json");
    try {
      // 環境変数から対象となるcustomIdを取得
      const customId = process.env.CUSTOM_ID;
      // 対象のJSONを読み込む
      this.customDefinition = require(`./${customId}.json`);
    } catch (e) {
      // 対象のJSONが無かったらデフォルトを設定
      this.customDefinition = this.defaultDefinition;
    }
  }

  /**
   * 文言を取得する。
   * @param key 文言に対応するキー
   * 例)label.sample
   * @returns keyに対応する文言
   */
  get(key: string): string {
    // customに無ければdefaultにフォールバック
    return this.search(this.customDefinition, key)
      || this.search(this.defaultDefinition, key);
  }

  search(target, key: string) {
    try {
      const keys: string[] = key.split(".");
      let obj = target[keys[0]];
      for (let i = 1; i < keys.length; i++) {
        obj = obj[keys[i]];
      }
      return obj;
    } catch (e) {
      return null;
    }
  }
}
