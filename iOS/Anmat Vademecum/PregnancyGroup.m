//
//  PregnancyGroup.m
//  VNM
//
//  Created by mag on 12/2/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import "PregnancyGroup.h"

@implementation PregnancyGroup {
    NSMutableArray *components;
}

- (id)init {
    components = [[NSMutableArray alloc] init];
    
    return self;
}

- (void)addComponent:(PregnancyComponent *)component {
    [components addObject:component];
}

- (NSArray *)getComponents {
    return components;
}

@end
