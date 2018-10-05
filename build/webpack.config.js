'use strict';

var webpack = require('webpack');

var config = {
    "mode": "development",
    "context": "/Users/gas/src/rumbaUI/build/js",
    "entry": {
        "main": "./rumbaUI"
    },
    "output": {
        "path": "/Users/gas/src/rumbaUI/build/bundle",
        "filename": "[name].bundle.js",
        "chunkFilename": "[id].bundle.js",
        "publicPath": "/"
    },
    "module": {
        "rules": [
            
        ]
    },
    "resolve": {
        "modules": [
            "js",
            "resources/main",
            "/Users/gas/src/rumbaUI/build/node_modules",
            "node_modules"
        ]
    },
    "plugins": [
        
    ]
};

module.exports = config;

