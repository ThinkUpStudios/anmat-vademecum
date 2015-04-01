//
//  DataBaseProvider.h
//  Anmat Vademecum
//
//  Created by mag on 3/31/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "sqlite3.h"

@interface DataBaseProvider : NSObject

- (sqlite3 *) getDataBase;

+ (id)instance;

@end
