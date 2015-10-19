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
#import "ActiveComponentRepository.h"
#import "String.h"
#import "MedicinesFilter.h"

@implementation MedicineService {
    MedicineRepository *medicinesRepository;
    ActiveComponentRepository *componentsRepository;
}

- (id) init {
    medicinesRepository = [[MedicineRepository alloc] init];
    componentsRepository = [[ActiveComponentRepository alloc] init];
    
    return self;
}

- (NSArray *) getMedicinesByFilter: (MedicinesFilter *)filter orderBy:(SortOptions)orderBy {
    NSArray *medicines;
    
    if(filter.activeComponent != nil && filter.activeComponent.length > 0) {
        medicines = [self getMedicines:filter.activeComponent orderBy:orderBy];
    } else {
        medicines = [self getMedicines:filter.genericName comercialName:filter.comercialName laboratory:filter.laboratory form:filter.form onlyRemediar: filter.onlyRemediar orderBy:orderBy];
    }
    
    return [self reOrder:medicines];
}

- (NSArray *) getMedicines: (NSString *)genericName comercialName: (NSString *)comercialName laboratory: (NSString *) laboratory form: (NSString *) form onlyRemediar: (BOOL) onlyRemediar orderBy: (SortOptions) orderBy {
    
    return [medicinesRepository getAll:[String trim:genericName] comercialName:[String trim:comercialName] laboratory:[String trim:laboratory] form: [String trim:form] onlyRemediar: onlyRemediar orderBy: orderBy];
}

- (NSArray *) getMedicines:(NSString *) activeComponent orderBy:(SortOptions) orderBy {
    NSString *trimmedActiveComponent = [String trim:activeComponent];
    
    NSMutableArray *componentIdentifiers = [[NSMutableArray alloc] initWithArray:[componentsRepository getAllIdentifiers:trimmedActiveComponent]];
    
    if(componentIdentifiers.count == 0) {
        [componentIdentifiers addObject:trimmedActiveComponent];
    }
    
    NSArray *medicines = [medicinesRepository getAll:componentIdentifiers orderBy:orderBy];
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
            
            
            if([componentIdentifiers containsObject:[String trim:componentName]]) {
                include = YES;
                break;
            }
        }
        
        if(include) {
            [result addObject:medicine];
        }
    }
    
    return result;
}

- (NSArray *) getGenericNames:(NSString *)searchText {
    return [medicinesRepository getGenericNames:[String trim:searchText]];
}

- (NSArray *) getComercialNames:(NSString *)searchText {
    return [medicinesRepository getComercialNames:[String trim:searchText]];
}

- (NSArray *) getLaboratories:(NSString *)searchText {
    return [medicinesRepository getLaboratories:[String trim:searchText]];
}

- (NSArray *) getForms: (NSString *)searchText {
    return [medicinesRepository getForms:[String trim:searchText]];
}

- (NSArray *) reOrder: (NSArray *) medicines {
    NSMutableArray *emptyPriceMedicines = [[NSMutableArray alloc] init];
    NSMutableArray *result = [[NSMutableArray alloc] init];
    
    for (Medicine *medicine in medicines) {
        BOOL isEmpty = NO;
        
        if(medicine.price == nil || medicine.price.length == 0 || [medicine.price isEqualToString:@"$-"]) {
            if(medicine.isRemediar == 1) {
                medicine.price = @"REMEDIAR";
            } else if(medicine.hospitalUsage == 1){
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

@end
