import { test as setup } from '@playwright/test';

setup('teardown', async ({ }) => {
  console.log('@teardown');
});
