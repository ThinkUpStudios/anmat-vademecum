//
//  PregnancyGroupService.m
//  VNM
//
//  Created by mag on 12/2/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import "PregnancyGroupService.h"
#import "PregnancyComponentRepository.h"
#import "PregnancyGroup.h"
#import "PregnancyComponent.h"

@implementation PregnancyGroupService {
    PregnancyComponentRepository *componentsRepository;
}

- (id) init {
    componentsRepository = [[PregnancyComponentRepository alloc] init];
    
    return self;
}

- (NSArray *)getAll {
    NSMutableArray *groups = [[NSMutableArray alloc] init];
    NSArray *components = [componentsRepository getAll];
    
    NSMutableArray *groupComponents = [[NSMutableArray alloc] init];
    NSString *currentGroup = @"";
    
    for (PregnancyComponent *component in components) {
        if([currentGroup isEqualToString:@""]) {
            currentGroup = component.group;
        }
        
        if([component.group isEqualToString:currentGroup]) {
            [groupComponents addObject:component];
        } else {
            PregnancyGroup *group = [[PregnancyGroup alloc] init];
            
            group.name = currentGroup;
            
            for(PregnancyComponent *groupComponent in groupComponents) {
                [group addComponent:groupComponent];
            }
            
            [groups addObject:group];
            currentGroup = component.group;
            [groupComponents removeAllObjects];
            [groupComponents addObject:component];
        }
    }
    
    return groups;
}

@end
