# KiwiPrints-Cordova

Kiwi is an Android application for managing patient health encounters. It uses the [SecugenPlugin] (https://github.com/chrisekelley/SecugenPlugin)
to enable fingerprint scanning for identification. This plugin works with the Secugen Hamster Plus fingerprint scanner.

# Development

## Overview

This application was built using Cordova, which is a container for the single page web application that provides most of the user interface for the app.

## Equipment

This project uses a Secugen Hamster Plus for fingerprint scanning. The [equipment setup](docs/equipment_setup.md) is documented.

## Deploy

To deploy to tablet:

    cordova run android

## Updating the Secugen Plugin

Use platforms/android/bakeapp.sh to refresh the plugin when you change it.

## Installing plugins from scratch

    cordova plugin add org.apache.cordova.device
    cordova plugin add org.apache.cordova.vibration
    cordova plugin add org.apache.cordova.dialogs
    cordova plugin add org.apache.cordova.file-transfer
    cordova plugin add org.apache.cordova.geolocation
    cordova plugin add org.apache.cordova.network-information
    cordova plugin add https://github.com/chrisekelley/Version.git
    cordova plugin add com.scispike.logger
    cordova plugin add org.apache.cordova.file
    cordova plugin add https://github.com/Initsogar/cordova-webintent.git
    cordova plugin add /Users/chrisk/source/SecugenPlugin 
    cordova plugin add de.appplant.cordova.plugin.local-notification && cordova prepare

If you need to cleanup old versions of the app that had a different package, add "Uninstall Android Application from Cordova" plugin:

    cordova plugin add https://github.com/chrisekelley/pman 
## Create a soft link to the coconut code

Clone https://github.com/chrisekelley/coconut
ln -s /Users/chrisk/source/coconut/_attachments www   

## Creating a new project

In CouchDB Futon, clone the following, using a new name for the new db's:
 - coconut-central
 - coconut-forms
 
 Create a new couch for the data
 
 Modify the forms and adjust the urls in coconut-central-newdb/coconut.config and reset coconut-central-newdb/version

## Payloads to and from the Scaning Service

Payload sent to service:
{"Key":"keyFromSerivce","Name":"Kiwi-Test","Template":"TEMPLATE_FORMAT_ISO19794","Finger":"1","District":"DistrictA"}

response from service: {"UID":"36 character UUID","Name":"Kiwi-Moz-01-2015","Finger":0,"Threshold":30,"StatusCode":1,"Error":null}

## Fingerprint Scanning Service Codes:

- Status Code 0 = Invalid form sent
- Status Code 1 = Success
- Status Code 2 = Invalid key
- Status Code 3 = Invalid ISO template

## Configuration


Change the url and paths in res/values/strings.xml

    <string name="app_name">Kiwi</string>
    <string name="templatePath">/sdcard/Download/fprints/</string>
    <string name="serverUrl">http://somewhere.com/</string>
    <string name="serverUrlFilepath">api/Person/Enroll</string>
    <string name="serverKey">authorizationKey</string>
    <string name="templateFormat">TEMPLATE_FORMAT_ISO19794</string>
    <string name="APP_VERSION">1</string>
    <string name="WIPE_CACHE">1</string>

## Updates

Increment APP_VERSION and set WIPE_CACHE = 1 to force the app to wipe the app cache and reset the whole app. 

Increment version="1.0.9"  and android:versionCode="11" in config.xml. 

Read the [coconut README](https://github.com/chrisekelley/coconut/blob/coconut-pouch/README.md#how-do-i-handle-application-updates) for more information on updates.

## Debugging

Since the fingerprint scanner uses the device's USB port, you must use wifi debugging. Here are some useful commands:

    adb kill-server
    adb start-server
    adb tcpip 5555
    adb shell ip -f inet addr show wlan0 | grep -Po 'inet \K[\d.]+'
    adb connect 192.168.0.101

    ./installapp.sh

Kudos for the one-liner to get the ip address: http://unix.stackexchange.com/a/87470

The platforms/android/bakeapp.sh script is also useful during development when removing/installing the plugin.

## Viewing logs

adb logcat | grep `adb shell ps | grep org.rti.kiwi.tt | cut -c10-15`

kudos: http://stackoverflow.com/a/9869609

## Creating icons

https://github.com/AlexDisler/cordova-icon

install:  

    npm install -g AlexDisler/cordova-icon
    
This version is directly from the developer's website - the one on NPM does not generate xxhdpi icons.

Save icon.png in project root. Save as 512x512. Run cordova-icon. It installs icons in platforms/android/res/ for the folllowing resolutions:
            { name : 'drawable/icon.png',       size : 96 },
            { name : 'drawable-hdpi/icon.png',  size : 72 },
            { name : 'drawable-ldpi/icon.png',  size : 36 },
            { name : 'drawable-mdpi/icon.png',  size : 48 },
            { name : 'drawable-xhdpi/icon.png', size : 96 },
            { name : 'drawable-xxhdpi/icon.png', size : 144 },


