//
//  MedicineService.h
//  Anmat Vademecum
//
//  Created by mag on 3/8/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Medicine.h"
#import "MedicinesFilter.h"

@interface MedicineService : NSObject

- (NSArray *) getMedicinesByFilter: (MedicinesFilter *)filter orderBy: (SortOptions) orderBy;

- (NSArray *) getGenericNames: (NSString *)searchText;

- (NSArray *) getComercialNames: (NSString *)searchText;

- (NSArray *) getLaboratories: (NSString *)searchText;

- (NSArray *) getForms: (NSString *)searchText;

@end
