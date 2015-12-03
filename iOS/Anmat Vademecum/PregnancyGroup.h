//
//  PregnancyGroup.h
//  VNM
//
//  Created by mag on 12/2/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "PregnancyComponent.h"

@interface PregnancyGroup : NSObject

@property NSString *name;

- (void) addComponent: (PregnancyComponent *)component;

- (NSArray *) getComponents;

@end
