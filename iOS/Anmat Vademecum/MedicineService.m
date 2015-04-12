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

@implementation MedicineService {
    MedicineRepository *repository;
}

- (id) init {
    repository = [[MedicineRepository alloc] init];
    
    return self;
}

- (NSArray *) getMedicines: (NSString *)genericName comercialName: (NSString *)comercialName laboratory: (NSString *) laboratory {
    return [repository getAll:genericName comercialName:comercialName laboratory:laboratory];
}

-(NSArray *)getMedicines:(NSString *)activeComponent {
    NSArray *medicines = [repository getByActiveComponent:activeComponent];
    NSMutableArray *result = [[NSMutableArray alloc] init];
    
    for (Medicine *medicine in medicines) {
        BOOL include = NO;
        NSArray *formulaDetails = [medicine.genericName componentsSeparatedByString:@"+"];
        
        for (NSString *formulaDetail in formulaDetails) {
            if(formulaDetail == nil || formulaDetail.length == 0) {
                continue;
            }
            
            NSUInteger byteLength = [formulaDetail lengthOfBytesUsingEncoding:NSUTF8StringEncoding];
            char buffer[byteLength + 1];
            const char *utf8_buffer = [formulaDetail cStringUsingEncoding:NSUTF8StringEncoding];
            
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
                componentName = [formulaDetail substringToIndex:separationIndex];
            } else {
                componentName = formulaDetail;
            }
            
            if([[self trimLastSpace:componentName] isEqualToString:[self trimLastSpace:activeComponent]]) {
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

- (NSString*) trimLastSpace:(NSString*)text{
    int i = (int)text.length - 1;
    
    for (; i >= 0 && [text characterAtIndex:i] == ' '; i--);
    
    return [text substringToIndex:i + 1];
}

@end
