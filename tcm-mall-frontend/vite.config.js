import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
    plugins: [vue()],
    server: {
        port: 5173,
        proxy: {
            // 把所有以/api开头的请求代理到后端
            '/api': {
                target: 'http://localhost:8080/tcm-mall', // 你的后端完整地址
                changeOrigin: true, // 开启跨域
                rewrite: (path) => path.replace(/^\/api/, '/api'), // 路径保持不变
                configure: (proxy, options) => {
                    proxy.on('proxyRes', (proxyRes, req, res) => {
                        const cookies = proxyRes.headers['set-cookie']
                        if (cookies) {
                            proxyRes.headers['set-cookie'] = cookies.map(cookie =>
                                cookie.replace(/Path=\/tcm-mall/g, 'Path=/')
                            )
                        }
                    })
                }
            }
        }
    }
})