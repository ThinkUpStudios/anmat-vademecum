//
//  String.h
//  VNM
//
//  Created by mag on 4/12/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface String : NSObject

+ (NSString *) trim: (NSString *) text;

+ (NSString *) trimLeft: (NSString *) text;

+ (NSString *) trimRight: (NSString *) text;

@end
