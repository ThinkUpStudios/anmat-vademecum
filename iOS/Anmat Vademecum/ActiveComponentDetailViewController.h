//
//  ActiveComponentDetailViewController.h
//  VNM
//
//  Created by mag on 10/24/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ActiveComponent.h"

@interface ActiveComponentDetailViewController : UITableViewController

@property (nonatomic, strong) NSString* ifa;
@property (nonatomic, strong) NSString* section;
@property (nonatomic, strong) NSArray *subtitles;
@property (nonatomic, strong) NSArray *details;

@end
