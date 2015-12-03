//
//  Section.h
//  VNM
//
//  Created by mag on 10/25/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Section : NSObject

@property NSString *name;

- (id) initWithName:(NSString *) name;

- (void) addRow:(NSString *) line1;

- (void) addRow:(NSString *) line1 line2:(NSString *) line2;

- (void) addRow:(NSString *) line1 line2:(NSString *) line2 line3:(NSString *) line3;

- (NSArray *) getRows;

@end
