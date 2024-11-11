// vite.config.ts
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'  // Fixed from @vitro to @vitejs
import path from 'path'

export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
})