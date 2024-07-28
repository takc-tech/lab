import { defineConfig, devices } from '@playwright/test';

// ================================================================
// ADD
// 環境変数の設定
// https://playwright.dev/docs/test-parameterize#env-files
const dotenv = require('dotenv');
// 複数の設定ファイルを指定可能
// 基本的には先に記述したものが優先される。
// 後に記述した内容で上書きしたい場合はoverrideオプションを使用
// https://www.npmjs.com/package/dotenv?activeTab=readme#-examples
dotenv.config({ path: [".env"] });
// ================================================================

/**
 * Read environment variables from file.
 * https://github.com/motdotla/dotenv
 */
// import dotenv from 'dotenv';
// dotenv.config({ path: path.resolve(__dirname, '.env') });

/**
 * See https://playwright.dev/docs/test-configuration.
 */
export default defineConfig({
  testDir: './tests',
  /* Run tests in files in parallel */
  fullyParallel: true,
  /* Fail the build on CI if you accidentally left test.only in the source code. */
  forbidOnly: !!process.env.CI,
  /* Retry on CI only */
  retries: process.env.CI ? 2 : 0,
  /* Opt out of parallel tests on CI. */
  workers: process.env.CI ? 1 : undefined,
  /* Reporter to use. See https://playwright.dev/docs/test-reporters */
  reporter: 'html',
  /* Shared settings for all the projects below. See https://playwright.dev/docs/api/class-testoptions. */
  use: {
    /* Base URL to use in actions like `await page.goto('/')`. */
    // baseURL: 'http://127.0.0.1:3000',

    /* Collect trace when retrying the failed test. See https://playwright.dev/docs/trace-viewer */
    trace: 'on-first-retry',
  },

  /* Configure projects for major browsers */
  projects: [
    // ================================================================
    // ADD
    // https://playwright.dev/docs/test-global-setup-teardown
    {
      name: 'setup',
      testMatch: /setup\.ts/,
      teardown: 'cleanup',
    },
    {
      name: 'cleanup',
      testMatch: /teardown\.ts/,
    },
    // https://playwright.dev/docs/test-projects
    {
      name: 'Google Chrome',
      use: {
        ...devices['Desktop Chrome'], channel: 'chrome',
        viewport: {
          width: 1280,
          height: 720
        },
      },
      dependencies: ['setup'],
    },
    // ================================================================
    // DEL
    // {
    //   name: 'chromium',
    //   use: { ...devices['Desktop Chrome'] },
    // },

    // {
    //   name: 'firefox',
    //   use: { ...devices['Desktop Firefox'] },
    // },

    // {
    //   name: 'webkit',
    //   use: { ...devices['Desktop Safari'] },
    // },
    // ================================================================

    /* Test against mobile viewports. */
    // {
    //   name: 'Mobile Chrome',
    //   use: { ...devices['Pixel 5'] },
    // },
    // {
    //   name: 'Mobile Safari',
    //   use: { ...devices['iPhone 12'] },
    // },

    /* Test against branded browsers. */
    // {
    //   name: 'Microsoft Edge',
    //   use: { ...devices['Desktop Edge'], channel: 'msedge' },
    // },
    // {
    //   name: 'Google Chrome',
    //   use: { ...devices['Desktop Chrome'], channel: 'chrome' },
    // },
  ],

  /* Run your local dev server before starting the tests */
  // webServer: {
  //   command: 'npm run start',
  //   url: 'http://127.0.0.1:3000',
  //   reuseExistingServer: !process.env.CI,
  // },
});
