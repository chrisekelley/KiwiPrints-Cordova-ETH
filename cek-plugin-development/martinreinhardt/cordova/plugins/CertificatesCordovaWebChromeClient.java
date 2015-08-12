package de.martinreinhardt.cordova.plugins;

import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import org.apache.cordova.engine.SystemWebChromeClient;
import org.apache.cordova.engine.SystemWebViewEngine;

/**
 * Created by chrisk on 8/7/15.
 */
public class CertificatesCordovaWebChromeClient extends SystemWebChromeClient {
    /**
     * Logging Tag
     */
    public static final String TAG = "CertCordovaWebViewClien";

    private boolean allowUntrusted = false;

    /**
     *
     * @param parentEngine
     */
    public CertificatesCordovaWebChromeClient(SystemWebViewEngine parentEngine) {
        super(parentEngine);
    }

    /**
     * @return true of usage of untrusted (self-signed) certificates is allowed,
     *         otherwise false
     */
    public boolean isAllowUntrusted() {
        return allowUntrusted;
    }

    /**
     *
     *
     * @param pAllowUntrusted
     *            the allowUntrusted to set
     */
    public void setAllowUntrusted(final boolean pAllowUntrusted) {
        this.allowUntrusted = pAllowUntrusted;
    }

    /**
     * @see org.apache.cordova.engine.SystemWebViewClient#onReceivedSslError(WebView,
     *      SslErrorHandler, SslError)
     */
//    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                   SslError error) {
        Log.d(TAG, "onReceivedSslError. Proceed? " + isAllowUntrusted());
        if (isAllowUntrusted()) {
            handler.proceed();
        } else {
            handler.cancel();
        }
    }

}
