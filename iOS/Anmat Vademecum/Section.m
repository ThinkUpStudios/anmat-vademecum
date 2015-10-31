//
//  Section.m
//  VNM
//
//  Created by mag on 10/25/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import "Section.h"
#import "SectionRow.h"

@implementation Section {
    NSMutableArray *rows;
}

- (id) initWithName:(NSString *) name {
    self.name = name;
    
    rows = [[NSMutableArray alloc] init];
    
    return self;
}

- (void) addRow:(NSString *)value detail:(NSString *)detail {
    SectionRow *row = [[SectionRow alloc] initWithValue:value];
    
    row.detail = detail;
    
    [rows addObject:row];
}

- (NSArray *) getRows {
    return rows;
}

@end
