//
//  SectionRow.h
//  VNM
//
//  Created by mag on 10/25/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SectionRow.h"

@interface SectionRow2Lines : SectionRow

@property NSString *line1;

@property NSString *line2;

- (id) initWithLines:(NSString *) line1 line2:(NSString *) line2;

@end
