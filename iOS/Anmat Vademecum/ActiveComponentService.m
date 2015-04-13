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
    return [repository getAllNames:searchText];
}

- (ActiveComponent *) getByName: (NSString *)name {
    NSString *trimmedName = [String trim:name];
    ActiveComponent *component = [repository getByName:trimmedName];
    
    if(component == nil) {
        component = [[ActiveComponent alloc] init];
        component.name = trimmedName;
    }
    
    component.action = [self sanitize:component.action];
    component.indication = [self sanitize:component.indication];
    component.presentation = [self sanitize:component.presentation];
    component.posology = [self sanitize:component.posology];
    component.duration = [self sanitize:component.duration];
    component.contraindication = [self sanitize:component.contraindication];
    component.observation = [self sanitize:component.observation];
    
    return component;
}

- (NSString *) sanitize:(NSString *)value {
    NSString *result = value;
    
    if(result == nil || result.length == 0) {
        result = @"-";
    }
    
    return result;
}

@end
