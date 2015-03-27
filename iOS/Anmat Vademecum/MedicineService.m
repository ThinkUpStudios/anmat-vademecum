//
//  MedicineService.m
//  Anmat Vademecum
//
//  Created by mag on 3/8/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "MedicineService.h"
#import "Medicine.h"
#import "MedicineRepository.h"

@implementation MedicineService

MedicineRepository *repository;

- (id) init {
    repository = [[MedicineRepository alloc] init];
    
    return self;
}

- (NSArray *) getMedicines: (NSString *)genericName comercialName: (NSString *)comercialName laboratory: (NSString *) laboratory {
    return [repository getAll:genericName comercialName:comercialName laboratory:laboratory];
}

-(NSArray *)getMedicines:(NSString *)activeComponent {
    NSArray *medicines = [repository getAll];
    NSMutableArray *result = [[NSMutableArray alloc] init];
    
    for (Medicine *medicine in medicines) {
        if([medicine.genericName containsString:activeComponent]) {
            [result addObject:medicine];
        }
    }
    
    return result;
}

- (NSArray *) getSimilarMedicines: (Medicine *)reference {
    NSArray *medicines = [repository getAll:reference.genericName comercialName:@"" laboratory:@""];
    NSMutableArray *result = [[NSMutableArray alloc] init];
    
    for (Medicine *medicine in medicines) {
        BOOL include = YES;
        
        if([self areEqual:medicine reference:reference]) {
            include = NO;
        }
        
        if(include) {
            [result addObject:medicine];
        }
    }
    
    return result;
}

- (NSArray *) getGenericNames:(NSString *)searchText {
    return [repository getGenericNames:searchText];
}

- (NSArray *) getComercialNames:(NSString *)searchText {
    return [repository getComercialNames:searchText];
}

- (NSArray *) getLaboratories:(NSString *)searchText {
    return [repository getLaboratories:searchText];
}

- (BOOL) areEqual: (Medicine *)medicine reference: (Medicine *) reference {
    return [medicine.genericName isEqualToString:reference.genericName] &&
    [medicine.comercialName isEqualToString:reference.comercialName] &&
    [medicine.laboratory isEqualToString:reference.laboratory] &&
    [medicine.form isEqualToString:reference.form] &&
    [medicine.certificate isEqualToString:reference.certificate] &&
    [medicine.presentation isEqualToString:reference.presentation];
}

@end
