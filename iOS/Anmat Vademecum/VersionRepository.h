//
//  VersionRepository.h
//  VNM
//
//  Created by mag on 4/27/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Version.h"

@interface VersionRepository : NSObject

- (Version *) getLatestVersion;

@end
