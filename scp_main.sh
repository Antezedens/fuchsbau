#!/bin/sh
./gradlew build
cd static
scp -P $6 default.css main.bundle.js $3@$4:temp-switch/html/


