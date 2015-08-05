adb uninstall org.rti.kiwi.tt   
cordova run android
adb logcat | grep `adb shell ps | grep org.rti.kiwi.tt | cut -c10-15`
