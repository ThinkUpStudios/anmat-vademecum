//
//  ActiveComponentService.m
//  Anmat Vademecum
//
//  Created by mag on 3/31/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "ActiveComponentService.h"
#import "ActiveComponentRepository.h"
#import "ActiveComponent.h"
#import "String.h"

@implementation ActiveComponentService {
    ActiveComponentRepository *repository;
}

- (id) init {
    repository = [[ActiveComponentRepository alloc] init];
    
    return self;
}

- (NSArray *) getAll: (NSString *)searchText {
    return [repository getAllNames:[String trim:searchText]];
}

- (ActiveComponent *) getByName: (NSString *)name {
    NSString *trimmedName = [String trim:name];
    ActiveComponent *component = [repository getByName:trimmedName];
    
    if(component == nil) {
        return component;
    }
    
    component.ifa = [self sanitize:component.ifa];
    component.clasification = [self sanitize:component.clasification];
    component.pharmacology = [self sanitize:component.pharmacology];
    component.action = [self sanitize:component.action];
    component.cinetic = [self sanitize:component.cinetic];
    component.indication = [self sanitize:component.indication];
    component.posology = [self sanitize:component.posology];
    component.contraindication = [self sanitize:component.contraindication];
    component.interaction = [self sanitize:component.interaction];
    component.reaction = [self sanitize:component.reaction];
    component.reference = [self sanitize:component.reference];
    component.additionalInfo = [self sanitize:component.additionalInfo];
    component.bibliography = [self sanitize:component.bibliography];
    
    return component;
}

- (NSString *) sanitize:(NSString *)value {
    NSString *result = value;
    
    if(result == nil || result.length == 0) {
        result = @"";
    }
    
    return result;
}

@end
