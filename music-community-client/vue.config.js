let proxyObj = {};
proxyObj['/ws'] = {
    ws: true,
    target: "ws://localhost:8989"
};
proxyObj['/'] = {
    ws: false,
    target: 'http://localhost:8989',
    changeOrigin: true,
    pathRewrite: {
        '^/': ''
    }
}
module.exports = {
    devServer: {
        host: 'localhost',
        port: 8081,
        proxy: proxyObj
    }
}
