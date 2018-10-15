#!/bin/sh
HOST='fuchsbau.cu.ma'
USER='fuchsba1'
PASSWD='fD3*6)YCn93hxM'
./gradlew build
cd static

ftp -n $HOST <<END_SCRIPT
quote USER $USER
quote PASS $PASSWD
cd public_html
put index.html
put default.css
put loading_spinner.gif
put main.bundle.js
quit
END_SCRIPT
exit 0
