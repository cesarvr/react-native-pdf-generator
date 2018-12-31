
import { NativeModules } from 'react-native';

let { PDFGenerator } = NativeModules;

if (PDFGenerator === undefined) {
  PDFGenerator = NativeModules.PDFGenerator
}


export default PDFGenerator;
