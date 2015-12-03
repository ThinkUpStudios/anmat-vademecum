//
//  Section.m
//  VNM
//
//  Created by mag on 10/25/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import "Section.h"
#import "SectionRow.h"
#import "SectionRow1Line.h"
#import "SectionRow2Lines.h"
#import "SectionRow3Lines.h"

@implementation Section {
    NSMutableArray *rows;
}

- (id) initWithName:(NSString *) name {
    self.name = name;
    
    rows = [[NSMutableArray alloc] init];
    
    return self;
}

- (void) addRow:(NSString *) line1 {
    SectionRow1Line *row = [[SectionRow1Line alloc] initWithLine:line1];
    
    [rows addObject:row];
}

- (void) addRow:(NSString *) line1 line2:(NSString *) line2 {
    SectionRow2Lines *row = [[SectionRow2Lines alloc] initWithLines:line1 line2:line2];
    
    [rows addObject:row];
}

- (void) addRow:(NSString *) line1 line2:(NSString *) line2 line3:(NSString *) line3 {
    SectionRow3Lines *row = [[SectionRow3Lines alloc] initWithLines:line1 line2:line2 line3:line3];
    
    [rows addObject:row];
}

- (NSArray *) getRows {
    return rows;
}

@end
