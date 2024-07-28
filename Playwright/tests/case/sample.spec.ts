// POMを使用しないなど、testを拡張しない場合は下記。
// import { test, expect } from '@playwright/test';
// 拡張したならば、拡張したソースから取得しないと、当然samplePageの未定義エラーが出る。
import { test, expect } from '@/fixtures';

// a11y
// npm install @axe-core/playwright
import AxeBuilder from '@axe-core/playwright';

// ファイル単位の設定
// https://playwright.dev/docs/test-use-options#emulation-options
test.use({
  // 画面サイズ
  viewport: { width: 500, height: 450 },
});

// グループ化
// https://playwright.dev/docs/test-annotations#group-tests
test.describe('test group', () => {

  // グループ単位の設定
  test.use({
    // 画面サイズ
    viewport: { width: 400, height: 300 },
  });

  test('sample', {
    // アノテーションサンプル
    // https://playwright.dev/docs/test-annotations#annotate-tests
    // レポートに出力される。
    annotation: [
      { type: 'issue', description: 'GitHubやBacklog等のチケットのURLとか' }
    ],
    // タグのサンプル
    // https://playwright.dev/docs/test-annotations#tag-tests
    // 任意のタグをつけられる。
    // npx playwright test --grep tag で特定のタグを持ったテストケースのみを実行できる。
    tag: ['@sample', '@pom'],
    // playwright.config.tsのuseに記載されている内容は取得できるようだ。
  }, async ({ samplePage, viewport, isMobile, page }, testInfo) => {

    // Fixturesの内容
    console.log(`@viewport:${JSON.stringify(viewport)}`);
    console.log(`@isMobile:${isMobile}`);
    console.log(`@testInfo.title:${testInfo.title}`);

    // 環境変数サンプル
    // https://playwright.dev/docs/test-parameterize#env-files
    console.log(`@.env[SAMPLE] = ${process.env.SAMPLE}`);

    // POMによるテスト
    // https://playwright.dev/docs/pom
    await samplePage.goto();
    await samplePage.screenshot("1_初期表示");
    await samplePage.setText(samplePage.getTestTitle());
    await samplePage.screenshot("2_テストタイトルをテキストボックスに入力");
    await samplePage.clickButton();
    await samplePage.screenshot("3_ボタン押下後");

    // a11yテスト
    const accessibilityScanResults = await new AxeBuilder({ page }).analyze();
    console.log(accessibilityScanResults.violations);
  });

  const condition = true;
  // https://playwright.dev/docs/test-annotations#skip-a-test
  test('条件付きスキップのサンプル', async ({ page }) => {
    // conditionを満たす場合、テストはスキップされる。
    // テスト単位のスキップの場合、Fixtureの前処理/後処理やbefore/after系の処理は実行されるみたい。(=>テストレポートに記載あり)
    // グループ単位でも指定可能。
    // 任意のコンディション
    test.skip(condition, '条件付きスキップ');
    console.log("@テスト実行");
  });
});
