import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

const path = require('path')
function resolve(dir) {
  return path.join(__dirname, dir)
}
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  alias: {
    '@': resolve('./src'),
    '@v': resolve('./src/views'),
    '@c': resolve('./src/components'),
    '@u': resolve('./src/utils'),
    '@a': resolve('./src/api'),
  },
  server: {
    port: 1025,
    open: true,
    // 如果端⼝占⽤，是退出，还是尝试其他端⼝
    strictPort: false,
    https: false,
    proxy: {
      '/api': {
        // target: 'http://localhost:8080/',
        target: 'http://localhost:9090/',
        changeOrigin: true,
        rewrite: path => path.replace(/^\/api/, ''),
        secure: false,
        ws: true
      },
      '/WeCom': {
        target: 'https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=c7fac8f1-083a-4737-8a09-6a34e9c49124',
        changeOrigin: true,
        rewrite: path => path.replace(/^\/WeCom/, '')
      }
    }
  },
  build: {
    outDir: 'dist'
  }
})