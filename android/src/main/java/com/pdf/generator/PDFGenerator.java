
package com.pdf.generator;

import android.content.Context;
import android.os.Build;
import android.print.PDFtoBase64;

import android.webkit.WebView;
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
  }



  private PDFtoBase64 configure(Context ctx, final Promise promise) {
      final PDFStore temporaryStorage = new PDFStore(ctx);
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

  @ReactMethod
  public void fromURL(String URL, final Promise promise) {
        Context ctx  = this.getCurrentActivity().getBaseContext();
        PDFtoBase64 p2b64 = configure(ctx, promise);
        new OffscreenBrowser(ctx)
                .setPrinterAdapter(p2b64)
                .loadFromURL(URL);
  }

  @ReactMethod
  public void fromHTML(String HTML, String baseURL, Promise promise){
       Context ctx  = this.getCurrentActivity().getBaseContext();
       PDFtoBase64 p2b64 = configure(ctx, promise);
       new OffscreenBrowser(ctx)
               .setPrinterAdapter(p2b64)
               .loadFromRawHTML(HTML, baseURL);
  }

  @Override
  public String getName() {
    return "PDFGenerator";
  }
}
