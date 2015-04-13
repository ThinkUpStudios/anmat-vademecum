//
//  String.m
//  VNM
//
//  Created by mag on 4/12/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "String.h"

@implementation String

+ (NSString *)trim:(NSString *)text {
    if(text == nil || text.length == 0) {
        return @"";
    }
    
    return [self trimRight:[self trimLeft:text]];
}

+ (NSString *)trimLeft:(NSString *)text {
    if(text == nil || text.length == 0) {
        return @"";
    }
    
    int i = 0;
    
    for (; i < text.length && [text characterAtIndex:i] == ' '; i++);
    
    if(i == text.length) {
        return @"";
    } else {
        return [text substringFromIndex:i];
    }
}

+ (NSString *)trimRight:(NSString *)text {
    if(text == nil || text.length == 0) {
        return @"";
    }
    
    int i = (int)text.length - 1;
    
    for (; i >= 0 && [text characterAtIndex:i] == ' '; i--);
    
    if(i == -1) {
        return @"";
    } else {
        return [text substringToIndex:i + 1];
    }
}

@end
