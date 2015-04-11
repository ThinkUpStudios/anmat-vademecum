//
//  Formula.h
//  VNM
//
//  Created by mag on 4/11/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Component.h"

@interface Formula : NSObject

- (NSArray *) getComponents;

- (void) addComponent: (Component *)component;

@end
