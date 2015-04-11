//
//  Formula.m
//  VNM
//
//  Created by mag on 4/11/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "Formula.h"

@implementation Formula {
    NSMutableArray *components;
}

- (id) init {
    components = [[NSMutableArray alloc] init];
    
    return self;
}

- (NSArray *)getComponents {
    return components;
}

- (void) addComponent:(Component *)component {
    [components addObject:component];
}

@end
