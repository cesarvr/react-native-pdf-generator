
## PDF Generator for React Native.

Is a light weight native library that transform HTML to PDF, or even better transform the content of a URL like ```https://google.com``` into a PDF.

The PDF is returned in base64 format so you can use more specialized modules to mail, sharing, visualizing, etc.

### How it works

Basically this library takes HTML as string or remote and loads the xml into an internal iOS/Android WebKit, then it traverse the web page generating a WYSIWYG PDF document.

This plugin uses a very light weight iOS library for this called [ BNHtmlPdfKit](https://github.com/brentnycum/BNHtmlPdfKit).

For Android it just use [WebView](https://developer.android.com/reference/android/webkit/WebView).


### Supported Devices

* iOS >=8
* Android >=19

## Getting started

To install the library:

### Easy Way

```sh
  npm i rn-pdf-generator --save
  react-native link
```

### Hard Way

[hard way...](https://facebook.github.io/react-native/docs/linking-libraries-ios)


### Usage

To transform raw HTML into PDF:

```javascript
import PDF  from 'rn-pdf-generator';

PDF.fromHTML(`<P>HELLO WORLD</P>`, `http://localhost`)
   .then(  data => console.log(data)) // WFuIGlzIGRpc3Rpbm....
   .catch( err  =>  console.log('error->', err) )
```

> In case you this HTML fetch resources from external servers you can specify the location using the second parameter ``<base-url>``.

Transforming a remote webpage:

```javascript

import PDF  from 'rn-pdf-generator';

PDF.fromURL('https://www.google.com/')
   .then(  data => console.log(data)) // WFuIGlzIGRpc3Rpbm....
   .catch( err  =>  console.log('error->', err) )

```

### Demo 


[This demo](https://github.com/cesarvr/react-native-pdf-generator-demo) that generates a PDF and then display it using [react-native-pdf plugin](https://www.npmjs.com/package/react-native-pdf). 

![Demo](https://github.com/cesarvr/react-native-pdf-generator-demo/blob/master/demo-img/pdf_document.gif?raw=true)



