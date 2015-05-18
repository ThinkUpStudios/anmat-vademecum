//
//  MedicineService.h
//  Anmat Vademecum
//
//  Created by mag on 3/8/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Medicine.h"

@interface MedicineService : NSObject

- (NSArray *) getMedicines: (NSString *)genericName comercialName: (NSString *)comercialName laboratory: (NSString *) laboratory form: (NSString *) form;

- (NSArray *) getMedicines: (NSString *)activeComponent;

- (NSArray *) getSimilarMedicines: (Medicine *)reference;

- (NSArray *) getGenericNames: (NSString *)searchText;

- (NSArray *) getComercialNames: (NSString *)searchText;

- (NSArray *) getLaboratories: (NSString *)searchText;

- (NSArray *) getForms: (NSString *)searchText;

@end
