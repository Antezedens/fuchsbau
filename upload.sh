#!/bin/sh
HOST='ftpupload.net'
USER='b11_23264273'
PASSWD='_byet.host_'
./gradlew build
cd static

ftp -n $HOST <<END_SCRIPT
quote USER $USER
quote PASS $PASSWD
cd htdocs
put index.html
put default.css
put loading_spinner.gif
put main.bundle.js
quit
END_SCRIPT
exit 0
