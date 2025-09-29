module.exports = {
    lintOnSave: false,   //加入此行 , false为关闭true为开启
    configureWebpack:{
        devtool: process.env.NODE_ENV === 'production' ? false : 'source-map',
    }
}
