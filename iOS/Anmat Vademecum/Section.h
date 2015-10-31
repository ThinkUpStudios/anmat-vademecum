//
//  Section.h
//  VNM
//
//  Created by mag on 10/25/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SectionRow.h"

@interface Section : NSObject

@property NSString *name;

- (id) initWithName:(NSString *) name;

- (void) addRow:(NSString *) value detail:(NSString *) detail;

- (NSArray *) getRows;

@end
