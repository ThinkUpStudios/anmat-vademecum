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
#import "String.h"

@implementation MedicineService {
    MedicineRepository *repository;
}

- (id) init {
    repository = [[MedicineRepository alloc] init];
    
    return self;
}

- (NSArray *) getMedicines: (NSString *)genericName comercialName: (NSString *)comercialName laboratory: (NSString *) laboratory {
    NSArray *medicines = [repository getAll:[String trim:genericName] comercialName:[String trim:comercialName] laboratory:[String trim:laboratory]];
    
    return [self reOrder:medicines];
}

-(NSArray *)getMedicines:(NSString *)activeComponent {
    NSArray *medicines = [repository getByActiveComponent:[String trim:activeComponent]];
    NSMutableArray *result = [[NSMutableArray alloc] init];
    
    for (Medicine *medicine in medicines) {
        BOOL include = NO;
        NSArray *formulaDetails = [medicine.genericName componentsSeparatedByString:@"+"];
        
        for (NSString *formulaDetail in formulaDetails) {
            NSString *trimmedFormulaDetail = [String trim:formulaDetail];
                       
            if(trimmedFormulaDetail == nil || trimmedFormulaDetail.length == 0) {
                continue;
            }
            
            NSUInteger byteLength = [trimmedFormulaDetail lengthOfBytesUsingEncoding:NSUTF8StringEncoding];
            char buffer[byteLength + 1];
            const char *utf8_buffer = [trimmedFormulaDetail cStringUsingEncoding:NSUTF8StringEncoding];
            
            strncpy(buffer, utf8_buffer, byteLength);
            
            int separationIndex = -1;
            
            for(int i = 0; i < byteLength; i++) {
                if([[NSCharacterSet decimalDigitCharacterSet] characterIsMember: buffer[i]]) {
                    separationIndex = i;
                    break;
                }
            }
            
            NSString *componentName = @"";
            
            if(separationIndex != -1) {
                componentName = [trimmedFormulaDetail substringToIndex:separationIndex];
            } else {
                componentName = trimmedFormulaDetail;
            }
            
            if([[String trim:componentName] isEqualToString:[String trim:activeComponent]]) {
                include = YES;
                break;
            }
        }
        
        if(include) {
            [result addObject:medicine];
        }
    }
    
    return [self reOrder:result];
}

- (NSArray *) getSimilarMedicines: (Medicine *)reference {
    NSArray *medicines = [repository getByGenericName:reference.genericName];
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
    
    return [self reOrder:result];
}

- (NSArray *) getGenericNames:(NSString *)searchText {
    return [repository getGenericNames:[String trim:searchText]];
}

- (NSArray *) getComercialNames:(NSString *)searchText {
    return [repository getComercialNames:[String trim:searchText]];
}

- (NSArray *) getLaboratories:(NSString *)searchText {
    return [repository getLaboratories:[String trim:searchText]];
}

- (NSArray *) reOrder: (NSArray *) medicines {
    NSMutableArray *emptyPriceMedicines = [[NSMutableArray alloc] init];
    NSMutableArray *result = [[NSMutableArray alloc] init];
    
    for (Medicine *medicine in medicines) {
        BOOL isEmpty = NO;
        
        if(medicine.price == nil || medicine.price.length == 0 || [medicine.price isEqualToString:@"$-"]) {
            if(medicine.hospitalUsage == 1){
                medicine.price = @"U.H";
            } else {
                isEmpty = YES;
            }
        }
        
        if(isEmpty) {
            [emptyPriceMedicines addObject:medicine];
        } else {
            [result addObject:medicine];
        }
    }
    
    [result addObjectsFromArray:emptyPriceMedicines];
    
    return result;
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
