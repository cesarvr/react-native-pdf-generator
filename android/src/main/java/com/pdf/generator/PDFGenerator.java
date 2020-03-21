
package com.pdf.generator;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.print.PDFtoBase64;

import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

import com.pdf.generator.OffscreenBrowser;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;



public class PDFGenerator extends ReactContextBaseJavaModule {
  private static final String TAG = "PDFGenerator";
  private final ReactApplicationContext reactContext;



  public PDFGenerator(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    Log.d(TAG, "PDFGenerator: Constructor");
  }



  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private PDFtoBase64 configure(final Promise promise) {
      final PDFStore temporaryStorage = new PDFStore(this.getCurrentActivity().getBaseContext());
      PDFtoBase64 pdfB64 = new PDFtoBase64(temporaryStorage);

      pdfB64.setG(new PDFNotify(){
          @Override
          public void endPrinting() {
              super.endPrinting();
              promise.resolve( temporaryStorage.getAsBase64() );
          }
      });

      return pdfB64;
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @ReactMethod
  public void fromURL(final String URL, final Promise promise) {
      final Activity activity = this.reactContext.getCurrentActivity();

      activity.runOnUiThread(new Runnable() {
          @Override
          public void run() {
              PDFtoBase64 p2b64 = configure(promise);

              final OffscreenBrowser offscreen = new OffscreenBrowser(activity)
                      .setPrinterAdapter(p2b64);

              offscreen.loadFromURL(URL);
          }
      });
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @ReactMethod
  public void fromHTML(final String HTML, final String baseURL, final Promise promise){
      final Activity activity = this.reactContext.getCurrentActivity();

      activity.runOnUiThread(new Runnable() {
          @Override
          public void run() {
              PDFtoBase64 p2b64 = configure(promise);

              final OffscreenBrowser offscreen = new OffscreenBrowser(activity)
                      .setPrinterAdapter(p2b64);

              offscreen.loadFromRawHTML(HTML, baseURL);
          }
      });


  }

  @Override
  public String getName() {
    return "PDFGenerator";
  }
}
