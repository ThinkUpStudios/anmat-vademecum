//
//  ActiveComponentService.h
//  Anmat Vademecum
//
//  Created by mag on 3/31/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ActiveComponent.h"

@interface ActiveComponentService : NSObject

- (NSArray *) getAll: (NSString *)searchText;

- (ActiveComponent *) getByName: (NSString *)name;

@end
