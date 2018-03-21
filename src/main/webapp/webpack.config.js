const webpack = require('webpack');
const path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry: "./src/app.js",
    output: {
        path: '../../../target/classes/static/',
        filename: "bundle.js"
    },
    module: {
        loaders: [
            {
                test: /\.css$/,
                loader: "style!css"
            },
            {
                test: /\.jsx?$/,
                include: [
                    path.resolve(__dirname, "./src")
                ],
                loader: 'babel',
                query: {
                    presets: ['es2015', 'react', "stage-2"]
                }
            },
            {
                test: /\.css$/,
                loader: ExtractTextPlugin.extract("style-loader", "css-loader")
            },
            {
                test: /\.less$/,
                loader: ExtractTextPlugin.extract("style-loader", "css-loader!less-loader")
            },
            {
                test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'url?limit=10000&mimetype=application/octet-stream&prefix=fonts'
            }
        ]
    },
    plugins: [
        new ExtractTextPlugin("[name].css"),
        // new webpack.optimize.UglifyJsPlugin({
        //     compress: { warnings: false }
        // })
        new HtmlWebpackPlugin({
            template: '../resources/static/index.html'
        }),

    ],
    watchOptions: {
        aggregateTimeout: 100,
        poll: 1000
    }
};