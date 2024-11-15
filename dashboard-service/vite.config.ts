// vite.config.ts
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    proxy: {
      '/api/v1/data': {
        target: 'http://localhost:8082',
        changeOrigin: true,
        ws: true,  // Enable WebSocket proxy
      },
    },
  },
});