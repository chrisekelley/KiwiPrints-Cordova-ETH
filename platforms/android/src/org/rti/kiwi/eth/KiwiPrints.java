/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package org.rti.kiwi.eth;

import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;

import org.apache.cordova.*;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.os.Build;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.io.File;

import de.martinreinhardt.cordova.plugins.CertificatesCordovaWebViewClient;
import de.martinreinhardt.cordova.plugins.CertsCordovaInterfaceImpl;


public class KiwiPrints extends CordovaActivity
{
	private static final String TAG = "KiwiPrints";
	private static final String APP_VERSION = "APP_VERSION";
     
	@Override
    public void onCreate(Bundle savedInstanceState)
    {


        int id = this.getApplicationContext().getResources().getIdentifier("APP_VERSION", "string", this.getPackageName());
//		Log.d(TAG, "APP_VERSION id: " + id);
    	String appVersion = this.getApplicationContext().getResources().getString(id);
    	Log.d(TAG, "appVersion: " + appVersion);
    	SharedPreferences sharedPreferences = this.getPreferences(this.getApplicationContext().MODE_PRIVATE);
    	
    	// kudos: http://pulse7.net/android/android-application-shortcut-home-screen/
    	boolean isFirstTime = true;
    	isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
        if (isFirstTime) {
            // Create explicit intent which will be used to call Our application
            // when some one clicked on short cut
            Intent shortcutIntent = new Intent(getApplicationContext(),KiwiPrints.class);
            shortcutIntent.setAction(Intent.ACTION_MAIN);
            Intent intent = new Intent();
 
            // Create Implicit intent and assign Shortcut Application Name, Icon
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "KiwiPrints");
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                    Intent.ShortcutIconResource.fromContext(
                            getApplicationContext(), R.drawable.icon));
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            getApplicationContext().sendBroadcast(intent);
 
            // Set preference to inform that we have created shortcut on
            // Homescreen
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.commit();
        }
    	
    	int appVersionPref = Integer.parseInt(sharedPreferences.getString("APP_VERSION", "0"));
        Log.d(TAG, "appVersionPref: " + appVersionPref);
		if ((appVersionPref != Integer.parseInt(appVersion))) {
            // you are updating
	        Log.d(TAG, "Updating the app.");
	        // check if we need to wipe the cache
	        id = this.getApplicationContext().getResources().getIdentifier("WIPE_CACHE", "string", this.getPackageName());
	        Log.d(TAG, "WIPE_CACHE id: " + id);
	    	String wipeCache = this.getApplicationContext().getResources().getString(id);
	    	Log.d(TAG, "wipeCache: " + wipeCache);
	    	if (Integer.parseInt(wipeCache) == 1) {
	    		Log.d(TAG, "wiping the cache: ");
//	    		super.clearCache(); // just add This Line
	    		clearApplicationData();
	    	}
            // set APP_VERSION = 2 and your new values
	        SharedPreferences.Editor editor = sharedPreferences.edit();
	        editor.putString("APP_VERSION", appVersion);
	        editor.commit();
        } else {
            // you are not updating
        	Log.d(TAG, "Not updating the app.");
        }
    	
		super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_cordova_with_layout);
//        cordovaInterface = makeCordovaInterface();

        super.init();

        Log.d(TAG, "Setting app to allow self-signed certificates.");
//        SystemWebViewEngine systemWebViewEngine = (SystemWebViewEngine) appView.getEngine();
//        CertificatesCordovaWebViewClient cWebClient = new CertificatesCordovaWebViewClient(systemWebViewEngine);
//        cWebClient.setAllowUntrusted(true);
//        WebView webView = (WebView) systemWebViewEngine.getView();
//        webView.clearCache(false);
//        webView.setWebViewClient(cWebClient);
////        webView.setWebChromeClient(cWebClient)

//        CordovaWebView webView = super.appView.getView();
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();
//            }
//        });


        // Set by <content src="index.html" /> in config.xml
        super.loadUrl(Config.getStartUrl());
        //super.loadUrl("file:///android_asset/www/index.html");
    }

//    /**
//     * Construct the default web view object.
//     *
//     * Override this to customize the webview that is used.
//     */
//    @Override
//    protected CordovaWebView makeWebView() {
//        return new CordovaWebViewImpl(makeWebViewEngine());
//    }
//
//    @Override
//    protected CordovaWebViewEngine makeWebViewEngine() {
//        return CordovaWebViewImpl.createEngine(this, preferences);
//    }

//    kudos: http://www.catharinegeek.com/embed-cordova-webview-in-android-native-app/
//    @Override
//    protected CordovaWebView makeWebView() {
//        SystemWebView webView = (SystemWebView)findViewById(R.id.cordovaWebView);
//        SystemWebViewEngine parentEngine = new SystemWebViewEngine(webView);
//        CertificatesCordovaWebViewClient cWebClient = new CertificatesCordovaWebViewClient(parentEngine);
////        webView.setWebViewClient(cWebClient);
//        webView.setWebViewClient(new SystemWebViewClient(parentEngine) {
//
//            @Override
//            public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
//                Log.d(TAG, "onReceivedSslError. Proceeding. " );
//                handler.proceed();
//            }
//        });
//        return new CordovaWebViewImpl(parentEngine);
//    }

//    @Override
//    protected void createViews() {
//        //Why are we setting a constant as the ID? This should be investigated
////        appView.getView().setId(100);
////        appView.getView().setLayoutParams(new FrameLayout.LayoutParams(
////                ViewGroup.LayoutParams.MATCH_PARENT,
////                ViewGroup.LayoutParams.MATCH_PARENT));
////
////        setContentView(appView.getView());
//
//        if (preferences.contains("BackgroundColor")) {
//            int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
//            // Background of activity:
//            appView.getView().setBackgroundColor(backgroundColor);
//        }
//
//        appView.getView().requestFocusFromTouch();
//    }
	
	public void clearApplicationData() {
	       File cache = getCacheDir();
	       File appDir = new File(cache.getParent());
	       Log.d(TAG, "appDir: " + appDir.getAbsolutePath());
	       if (appDir.exists()) {
	           String[] children = appDir.list();
	           for (String s : children) {
	               if (!s.equals("lib")) {
	                   deleteDir(new File(appDir, s));
	                   Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
	           }
	       }
	       }
	   }

	   public static boolean deleteDir(File dir) {
	       if (dir != null && dir.isDirectory()) {
	           String[] children = dir.list();
	           for (int i = 0; i < children.length; i++) {
	               boolean success = deleteDir(new File(dir, children[i]));
	               if (!success) {
	                   return false;
	           }
	       }
	       }
	           return dir.delete();
	   }

     @Override
    public void onReceivedError( int errorCode, String description, String failingUrl)
    {
        if (failingUrl.contains("#")) {
            Log.v("LOG", "Config.getStartUrl():"+ Config.getStartUrl());
            Log.v("LOG", "failing url:"+ failingUrl);
            final int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
            if (sdkVersion > Build.VERSION_CODES.GINGERBREAD) {
                String[] temp;
                temp = failingUrl.split("#");
                Log.v("LOG", "load page without internal link:"+ temp[0]);
                super.loadUrl(temp[0]); // load page without internal link
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
            Log.v("LOG", "try again failing url:"+ failingUrl);
            super.loadUrl(failingUrl);  // try again
        } else {
             Log.v("LOG", "failing url does not contain #, loading regular url.");
             super.loadUrl(Config.getStartUrl());
        }
    }
}
