module.exports = {
  outputDir: 'target/dist',
  assetsDir: 'static',
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      }
    }
  },
}
