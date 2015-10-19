//
//  MedicineRepository.h
//  Anmat Vademecum
//
//  Created by mag on 3/16/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MedicinesFilter.h"

@interface MedicineRepository : NSObject

- (NSArray *) getAll: (SortOptions) orderBy;

- (NSArray *) getAll: (NSString *)genericName comercialName: (NSString *)comercialName laboratory: (NSString *) laboratory form: (NSString *) form onlyRemediar: (BOOL) onlyRemediar orderBy: (SortOptions) orderBy;

- (NSArray *)getAll:(NSArray *)componentIdentifiers orderBy: (SortOptions) orderBy;

- (NSArray *) getGenericNames: (NSString *)searchText;

- (NSArray *) getComercialNames: (NSString *)searchText;

- (NSArray *) getLaboratories: (NSString *)searchText;

- (NSArray *) getForms: (NSString *)searchText;

@end
