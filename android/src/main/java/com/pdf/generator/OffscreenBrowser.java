  
package com.pdf.generator;

import android.content.Context;
import android.os.Build;
import android.print.PDFtoBase64;
import android.print.PrintDocumentAdapter;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class OffscreenBrowser {
      private WebView webView;
      private final String DEFAULT_BASE_URL = "http://localhost";
      private final String TAG = this.getClass().getName();

      public OffscreenBrowser(Context ctx){
          webView = new WebView(ctx);
          webView.getSettings().setDatabaseEnabled(true);
          webView.getSettings().setJavaScriptEnabled(true);
      }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private PrintDocumentAdapter getPrintAdapter(WebView webView){
        PrintDocumentAdapter printAdapter = null;
        Log.e(TAG, "creating a new WebView adapter.");
        if (Build.VERSION.SDK_INT >= 21) {
            printAdapter = webView.createPrintDocumentAdapter("default");
        } else {
            printAdapter = webView.createPrintDocumentAdapter();
        }

        return printAdapter;
    }

      @RequiresApi(api = Build.VERSION_CODES.KITKAT)
      public OffscreenBrowser setPrinterAdapter(final PDFtoBase64 pdFtoBase64){

          webView.setWebViewClient(new WebViewClient() {
              @Override
              public void onPageFinished(WebView view, String url) {
                  super.onPageFinished(view, url);

                  PrintDocumentAdapter pda = getPrintAdapter(view);
                  pdFtoBase64.process(pda);
              }
          });

          return this;
      }

      public void loadFromRawHTML(String data, String baseURL){
          baseURL = baseURL.isEmpty()? DEFAULT_BASE_URL : baseURL;

          //https://developer.android.com/reference/android/webkit/WebView.html#loadDataWithBaseURL(java.lang.String,%20java.lang.String,%20java.lang.String,%20java.lang.String,%20java.lang.String)
          webView.loadDataWithBaseURL(baseURL, data,"text/HTML","UTF-8",null);
      }

      public void loadFromURL(String url){
          webView.loadUrl(url);
      }
  };
