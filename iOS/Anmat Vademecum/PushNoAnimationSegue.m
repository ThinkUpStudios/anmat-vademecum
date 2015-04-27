//
//  PushNoAnimationSegue.m
//  VNM
//
//  Created by mag on 4/27/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "PushNoAnimationSegue.h"

@implementation PushNoAnimationSegue

-(void) perform {
    UIViewController *destination = (UIViewController *)self.destinationViewController;
    UIWindow *window = [[UIApplication sharedApplication].windows objectAtIndex:0];
    
    window.rootViewController = destination;
    [window makeKeyAndVisible];
}

@end
