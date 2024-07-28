import type { Page, Locator, TestInfo } from '@playwright/test';
import I18n from '@/i18n/I18n';

/**
 * POMのサンプル
 * https://playwright.dev/docs/pom
 */
export default class SamplePage {

  readonly page: Page;
  readonly testInfo: TestInfo;
  readonly button: Locator;
  readonly textBox: Locator;

  constructor(page: Page, testInfo: TestInfo) {
    this.page = page;
    this.testInfo = testInfo;
    this.textBox = page.getByRole('textbox');

    const i18n = new I18n();
    this.button = page.getByRole('button', { name: i18n.get("label.button") });
  }

  /**
   * 試験対象のページに移動
   */
  async goto() {
    await this.page.goto('https://takc-tech.com/sample.html');
  }

  /**
   * ボタンをクリック
   */
  async clickButton() {
    await this.button.click();
  }

  /**
   * テキストボックスに値を設定
   */
  async setText(text: string) {
    await this.textBox.fill(text);
  }

  /**
   * 現在実行しているテストのタイトルを取得
   * @returns 
   */
  getTestTitle() {
    return this.testInfo.title;
  }

  /**
   * スクリーンショットを取得
   * @param suffix 接尾辞
   */
  async screenshot(suffix: string) {
    const path = "./img/" + this.testInfo.title + suffix + ".png";
    await this.page.screenshot({ path });
  }
}
