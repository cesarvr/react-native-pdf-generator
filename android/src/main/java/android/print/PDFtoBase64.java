package android.print;

import com.pdf.generator.PDFNotify;
import com.pdf.generator.PDFStore;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import android.content.Context;
import android.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// https://github.com/cesarvr/pdf-generator/issues/9
// http://www.annalytics.co.uk/android/pdf/2017/04/06/Save-PDF-From-An-Android-WebView/
public class PDFtoBase64 {

    private static final String TAG = PDFtoBase64.class.getSimpleName();
    private static final String FILE_PREFIX = "PDF_GENERATOR";
    private static final String FILE_EXTENSION = "pdf";
    private static final String FILE_EMPTY_ERROR = "Error: Empty PDF File";
    private static final String FILE_NOT_FOUND = "Error: Temp File Not Found";
    private static final String IO_EXCEPTION = "Error: I/O";
    private final PrintAttributes attributes;

    private Context ctx;
    private File file;
    private String encodedBase64;
    PrintAttributes.MediaSize pageType = PrintAttributes.MediaSize.ISO_A4;
    PDFStore store;
    PDFNotify notify;


    public PDFtoBase64(PDFStore store) {
        this.store = store;
        PrintAttributes.MediaSize mediaSize = pageType.asLandscape();

         attributes = new PrintAttributes.Builder()
                .setMediaSize(mediaSize)
                .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(new PrintAttributes.Margins(10,10,10,5)).build();
        //.setMinMargins(PrintAttributes.Margins.NO_MARGINS).build();
    }

    public void setG(PDFNotify notify){
        this.notify = notify;
    }


    public void process(final PrintDocumentAdapter printAdapter) {

        final CancellationSignal cancellationSignal = new CancellationSignal();


        final PageRange[] ALL_PAGES_ARRAY = new PageRange[]{PageRange.ALL_PAGES};


        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                Log.e(TAG, "onCancel: The action was cancelled");
            }

        });


        final PrintDocumentAdapter.WriteResultCallback myWriteResultCallback = new PrintDocumentAdapter.WriteResultCallback() {

            @Override
            public void onWriteFinished(PageRange[] pages) {
                super.onWriteFinished(pages);

                if(notify != null)
                    notify.endPrinting();
            }

            @Override
            public void onWriteCancelled() {
                super.onWriteCancelled();

                Log.d(TAG, "onWriteCancelled: Cancelled!!");
            }

            @Override
            public void onWriteFailed(CharSequence error) {
                super.onWriteFailed(error);

                Log.d(TAG, "onWriteFailed: Failed!!! " + error.toString() );
            }
        };

        final PrintDocumentAdapter.LayoutResultCallback myLayoutResultCallback = new PrintDocumentAdapter.LayoutResultCallback() {
            @Override
            public void onLayoutFinished(PrintDocumentInfo info, boolean changed) {

                printAdapter.onWrite(ALL_PAGES_ARRAY, store.getFileDescriptor(), cancellationSignal, myWriteResultCallback);
            }

            @Override
            public void onLayoutFailed(CharSequence error) {
                super.onLayoutFailed(error);

                Log.e(TAG, "onLayoutFailed: " + error.toString() );
            }
        };

        printAdapter.onLayout(null, attributes, null, myLayoutResultCallback, null);
    }


    private void Test(){


    }


}
