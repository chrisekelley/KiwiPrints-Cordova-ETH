adb uninstall org.rti.kiwi.eth   
cordova run android
adb logcat | grep `adb shell ps | grep org.rti.kiwi.eth | cut -c10-15`
