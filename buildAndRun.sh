#!/bin/sh

export JAVA_HOME="C:\Program Files\Android\Android Studio\jbr"

./gradlew assambleDebug

adb install -d app/build/outputs/apk/debug/app-debug.apk

adb shell am start -n "sk.tuke.earthexplorer/sk.tuke.earthexplorer.MainActivity"