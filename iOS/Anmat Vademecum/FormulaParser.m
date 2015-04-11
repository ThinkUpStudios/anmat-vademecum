//
//  FormulaParser.m
//  VNM
//
//  Created by mag on 4/11/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "FormulaParser.h"
#import "Component.h"

@implementation FormulaParser

+ (Formula *)parse:(NSString *)genericName {
    Formula *formula = [[Formula alloc] init];
    NSArray *parts = [genericName componentsSeparatedByString:@"+"];
    
    for (NSString *part in parts) {
        if(part == nil || part.length == 0 || [part isEqualToString:@"-"]) {
            continue;
        }
        
        Component *component = [self getComponent:part];
        
        [formula addComponent:component];
    }
    
    return formula;
}

+ (Component *) getComponent: (NSString *) part {
    Component *component = [[Component alloc] init];
    NSString *activeComponent = @"";
    NSString *proportion = @"";
    
    NSInteger proportionStartIndex = -1;
    BOOL unitFound = NO;
    BOOL spaceFound = NO;
    BOOL slashFound = NO;
    BOOL proportionRead = NO;
    
    NSInteger length = [part lengthOfBytesUsingEncoding:NSUTF8StringEncoding];
    char partChars[length];
    const char *buffer = [part cStringUsingEncoding:NSUTF8StringEncoding];
    
    strncpy(partChars, buffer, length);
    
    NSInteger i = length;
    
    while (!proportionRead && i >= 0)
    {
        if (unitFound)
        {
            if ([[NSCharacterSet decimalDigitCharacterSet] characterIsMember: partChars[i]] ||
                partChars[i] == '.' ||
                partChars[i] == ',')
            {
                if(spaceFound) {
                    proportionRead = YES;
                    proportionStartIndex = i + 1;
                }
            }
            else if (partChars[i] == ' ') {
                spaceFound = YES;
            }
            else if (partChars[i] == '/' && !slashFound)
            {
                slashFound = YES;
                unitFound = NO;
                spaceFound = NO;
            }
            else
            {
                proportionRead = YES;
                proportionStartIndex = i + 1;
            }
        }
        else if ([[NSCharacterSet decimalDigitCharacterSet] characterIsMember: partChars[i]])
        {
            unitFound = YES;
        }
        
        i--;
    }
    
    if (proportionStartIndex == -1)
    {
        activeComponent = part;
    }
    else
    {
        activeComponent = [part substringToIndex:proportionStartIndex];
        proportion = [part substringFromIndex:proportionStartIndex];
    }
    
    component.activeComponent = activeComponent;
    component.proportion = proportion;
    
    return component;
}

@end
