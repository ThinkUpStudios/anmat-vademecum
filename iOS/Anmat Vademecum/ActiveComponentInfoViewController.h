//
//  ActiveComponentViewController.h
//  Anmat Vademecum
//
//  Created by mag on 3/22/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ActiveComponent.h"

@interface ActiveComponentInfoViewController : UITableViewController

@property (nonatomic, strong) NSString *name;
@property (nonatomic, strong) ActiveComponent *component;

@end
