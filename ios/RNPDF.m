
#import "RNPDF.h"
#import <React/RCTLog.h>


@implementation RNMyLibrary

- (dispatch_queue_t)methodQueue
{
        return dispatch_get_main_queue();
 //   return dispatch_queue_create("com.facebook.React.AsyncLocalStorageQueue", DISPATCH_QUEUE_SERIAL);
}
    
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(addEvent:(NSString *)name location:(NSString *)location)
{
    RCTLogInfo(@"Pretending to create an event %@ at %@", name, location);
}

-(void (^)(NSData *data)) promisifyResponseAsBase64: (RCTPromiseResolveBlock) resolve {
    
    return ^(NSData *data){
        resolve([data base64EncodedStringWithOptions:0]);
    };
}

-(void (^)(NSError *error)) returnErrorAsText: (RCTPromiseRejectBlock)reject {
    return ^(NSError *error) {
        reject(@"no_events", @"Generation Error", error);
    };
}


RCT_EXPORT_METHOD(fromURL: (NSString *)URL
                  pdfGenerateResolve: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject)
{
      self.htmlPdfKit =  [BNHtmlPdfKit saveUrlAsPdf:[NSURL URLWithString:URL]
                                         pageSize:BNPageSizeA4
                                         isLandscape:NO
                                         success: [self promisifyResponseAsBase64:resolve]
                                         failure: [self returnErrorAsText:reject]];
}

RCT_EXPORT_METHOD(fromHTML: (NSString *)HTMLPayload
                  baseURL: (NSString *)baseURL
                  pdfGenerateResolve: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject)
{
    baseURL = ([baseURL isEqualToString:@""])? @"http://localhost" : baseURL;
    NSURL *_base = [NSURL URLWithString: baseURL];
    
    self.htmlPdfKit =  [BNHtmlPdfKit saveHTMLAsPdf:HTMLPayload
                                         pageSize:BNPageSizeA4
                                         isLandscape:NO
                                         baseUrl: _base
                                         success: [self promisifyResponseAsBase64:resolve]
                                         failure: [self returnErrorAsText:reject]];
}


@end
