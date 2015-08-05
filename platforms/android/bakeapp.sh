adb uninstall org.rti.kiwi.tt
plugman uninstall --platform android --project /Users/chrisk/source/KiwiPrints-Cordova/platforms/android --plugin com.iwanjunaid.pman.PMan
plugman install --platform android --project /Users/chrisk/source/KiwiPrints-Cordova/platforms/android --plugin /Users/chrisk/source/pman  
rm -rf ../../plugins/com.iwanjunaid.pman.PMan
cp -Rf cordova/plugins/com.iwanjunaid.pman.PMan ../../plugins
cordova run android
adb logcat | grep `adb shell ps | grep org.rti.kiwi.tt | cut -c10-15`
