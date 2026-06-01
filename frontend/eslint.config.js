/** @type {import('eslint').Linter.Config} */
export default {
  root: true,
  env: { browser: true, es2020: true },
  extends: ['eslint:recommended'],
  plugins: ['react-hooks'],
  rules: {
    'react-hooks/rules-of-hooks': 'error',
    'react-hooks/exhaustive-deps': 'warn',
    'no-unused-vars': ['warn', { argsIgnorePattern: '^_' }],
  },
}
