//
//  MedicineRepository.h
//  Anmat Vademecum
//
//  Created by mag on 3/16/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MedicineRepository : NSObject

- (NSArray *) getAll;

- (NSArray *) getAll: (NSString *)genericName comercialName: (NSString *)comercialName laboratory: (NSString *) laboratory;

- (NSArray *) getGenericNames: (NSString *)searchText;

- (NSArray *) getComercialNames: (NSString *)searchText;

- (NSArray *) getLaboratories: (NSString *)searchText;

@end
