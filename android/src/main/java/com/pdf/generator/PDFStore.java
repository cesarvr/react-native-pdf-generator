package com.pdf.generator;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PDFStore {
      private final String TAG = this.getClass().getName();
      private static final String FILE_PREFIX = "PDF_GENERATOR";
      private static final String FILE_EXTENSION = "pdf";
      private File file;
      private ParcelFileDescriptor pfd;
      private Context ctx;


      public PDFStore(Context _ctx){
          this.ctx = _ctx;
          this.makeTemporaryFile();
      }

      public String getAsBase64() {
          String encodedBase64 = null;

          try{
              FileInputStream fileInputStreamReader = new FileInputStream(file);
              byte[] bytes = new byte[(int)file.length()];
              fileInputStreamReader.read(bytes);
              fileInputStreamReader.close();
              encodedBase64 = Base64.encodeToString( bytes, Base64.DEFAULT );
              file.delete();
          } catch(FileNotFoundException ex) {
              Log.e(TAG, "getAsBase64 Error File Not Found: ", ex );
              //cordovaCallback.error(FILE_NOT_FOUND);
          } catch(IOException ex) {
              Log.e(TAG, "getAsBase64 Error in I/O: ", ex );
              //cordovaCallback.error(IO_EXCEPTION);
          }

          return encodedBase64;
      }

      public ParcelFileDescriptor getFileDescriptor() {
          return pfd;
      }
      
      public void makeTemporaryFile() {
          try {
              file = File.createTempFile(FILE_PREFIX, FILE_EXTENSION, ctx.getCacheDir());
              pfd  = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE);
          } catch (Exception e) {
              Log.e(TAG, "Failed to open ParcelFileDescriptor", e);
          }
      }
  }
