//
//  AnmatDataService.h
//  VNM
//
//  Created by mag on 4/27/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AnmatData.h"

@interface AnmatDataService : NSObject

- (void) checkUpdateAvailable:(void (^)(BOOL existsNewUpdate, NSError *error))completion;

- (void) processNewData:(void (^)(AnmatData *data, NSError *error))completion;

@end
