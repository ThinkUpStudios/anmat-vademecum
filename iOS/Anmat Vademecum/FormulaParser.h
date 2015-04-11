//
//  FormulaParser.h
//  VNM
//
//  Created by mag on 4/11/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Formula.h"

@interface FormulaParser : NSObject

+ (Formula *) parse: (NSString *) genericName;

@end
