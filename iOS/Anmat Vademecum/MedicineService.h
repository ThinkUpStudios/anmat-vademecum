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

- (NSArray *) getMedicines: (NSString *)genericName comercialName: (NSString *)comercialName laboratory: (NSString *) laboratory;

- (NSArray *) getSimilarMedicines: (Medicine *)reference;

@end
