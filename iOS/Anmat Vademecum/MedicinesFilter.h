//
//  MedicinesFilter.h
//  VNM
//
//  Created by mag on 4/30/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Medicine.h"

typedef NS_OPTIONS(NSUInteger, SortOptions) {
    Price                 = 0,
    Form   = 1 << 0,
    GenericName        = 1 << 1,
    ComercialName  = 1 << 2
};

@interface MedicinesFilter : NSObject

@property NSString *genericName;

@property NSString *comercialName;

@property NSString *laboratory;

@property NSString *form;

@property NSString *activeComponent;

@property BOOL onlyRemediar;

@end
