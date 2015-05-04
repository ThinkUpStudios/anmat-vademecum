//
//  AutoLayoutLabel.m
//  VNM
//
//  Created by mag on 5/4/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "AutoLayoutLabel.h"

@implementation AutoLayoutLabel

- (void)setBounds:(CGRect)bounds {
    [super setBounds:bounds];
    
    if (self.numberOfLines == 0 && bounds.size.width != self.preferredMaxLayoutWidth) {
        self.preferredMaxLayoutWidth = self.bounds.size.width;
        [self setNeedsUpdateConstraints];
    }
}

@end
