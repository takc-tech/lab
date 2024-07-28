import { test as base } from '@playwright/test';
import SamplePage from '@/pom/SamplePage';

export const test = base.extend({
  // テストケースごとに実行される
  samplePage: async ({ page }, use, testInfo) => {

    // 事前処理
    // POMの生成やログイン処理、テストデータ登録など
    const samplePage = new SamplePage(page, testInfo);
    console.log("@fixture:setup samplePage");

    // テストケースにPOMを渡す
    await use(samplePage);

    // 事後処理
    // データの削除やブラウザの状態のリセットなど
    console.log("@fixture:teardown samplePage");

  }
});
export { expect } from '@playwright/test';
