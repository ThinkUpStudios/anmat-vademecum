//
//  UISelectableButton.m
//  Anmat Vademecum
//
//  Created by mag on 3/8/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "UISelectableButton.h"

@implementation UISelectableButton

-(void)setHighlighted:(BOOL)highlighted {
    if(highlighted) {
        self.backgroundColor = [UIColor grayColor];
    } else {
        self.backgroundColor = [UIColor colorWithRed:239/255.0 green:239/255.0 blue:239/255.0 alpha:0.7];
    }
    
    [super setHighlighted:highlighted];
}

-(void)setSelected:(BOOL)selected {
    if(selected) {
        self.backgroundColor = [UIColor grayColor];
    } else {
        self.backgroundColor = [UIColor colorWithRed:239/255.0 green:239/255.0 blue:239/255.0 alpha:0.7];
    }
    
    [super setSelected:selected];
}

@end
