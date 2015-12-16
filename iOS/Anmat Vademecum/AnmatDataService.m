//
//  AnmatDataService.m
//  VNM
//
//  Created by mag on 4/27/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "AnmatDataService.h"
#import "VersionRepository.h"

@implementation AnmatDataService {
    NSString *serviceUriTemplate;
    VersionRepository *versionRepository;
}

- (id) init {
    serviceUriTemplate = @"http://recorriendo.cloudapp.net:81/anmatdataservice/AnmatDataService.svc/%@";
    versionRepository = [[VersionRepository alloc] init];
    
    return self;
}

- (void) checkUpdateAvailable:(void (^)(BOOL existsNewUpdate, NSError *error))completion {
    int version = [self getLatestVersion];
    NSString *serviceMethodString = [NSString stringWithFormat:@"isnewdataavailable?version=%d", version];
    NSString *serviceUriString = [NSString stringWithFormat:serviceUriTemplate, serviceMethodString];
    NSURL *serviceUrl = [NSURL URLWithString:serviceUriString];
    NSURLRequest *request = [NSURLRequest requestWithURL:serviceUrl cachePolicy:NSURLRequestReloadIgnoringCacheData timeoutInterval:5.0];
    
    [[[NSURLSession sharedSession] dataTaskWithRequest:request completionHandler:^(NSData *data, NSURLResponse *response, NSError *error){
        if (completion) {
            if (error)
            {
                [self logError:error message:@"Error al chequear nueva version disponible"];
                completion(NO, error);
            } else {
                NSString *responseBody = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
                
                completion([responseBody boolValue], error);
            }
        }
    }] resume];
}

-(void)processNewData:(void (^)(AnmatData *, NSError *))completion {
    NSString *serviceUriString = [NSString stringWithFormat:serviceUriTemplate, @"getdata"];
    NSURL *serviceUrl = [NSURL URLWithString:serviceUriString];
    NSURLRequest *request = [NSURLRequest requestWithURL:serviceUrl cachePolicy:NSURLRequestReloadIgnoringCacheData timeoutInterval:120];
    
    [[[NSURLSession sharedSession] dataTaskWithRequest:request completionHandler:^(NSData *data, NSURLResponse *response, NSError *error){
        if (completion) {
            if (error)
            {
                [self logError:error message:@"Error al bajar la nueva version"];
                completion(nil, error);
            } else {
                id json = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingAllowFragments error:&error];
                
                if ([json isKindOfClass:[NSDictionary class]] && error == nil)
                {
                    NSNumber *contentSize = [json objectForKey:@"ContentSize"];
                    NSString *content = [json objectForKey:@"Content"];
                    AnmatData *data = [[AnmatData alloc] init];
                    
                    data.content = content;
                    data.contentSize = [contentSize longValue];
                    
                    completion(data, error);
                } else {
                    [self logError:error message:@"Error deserializar el contenido de la nueva version"];
                    completion(nil, error);
                }
            }
        }
    }] resume];
}

- (int) getLatestVersion {
    Version *version = [versionRepository getLatestVersion];
    
    return (int)version.number;
}

- (void) logError:(NSError *) error message:(NSString *) message {
    NSString *logMessage = [NSString stringWithFormat:@"Error: %@ - Detalle: %@", message, error.description];
    
    NSLog(@"%@", logMessage);
}

@end
