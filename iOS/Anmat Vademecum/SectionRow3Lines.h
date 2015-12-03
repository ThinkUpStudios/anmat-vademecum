//
//  SectionRow3Lines.h
//  VNM
//
//  Created by mag on 10/31/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SectionRow.h"

@interface SectionRow3Lines : SectionRow

@property NSString *line1;

@property NSString *line2;

@property NSString *line3;

- (id) initWithLines:(NSString *) line1 line2:(NSString *) line2 line3:(NSString *) line3;

@end
