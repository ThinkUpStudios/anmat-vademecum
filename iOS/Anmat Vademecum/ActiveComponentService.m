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
    return [repository getByName:name];
}

@end
