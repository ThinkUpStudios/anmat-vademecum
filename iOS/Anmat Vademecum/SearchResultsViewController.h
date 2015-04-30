//
//  SearchResultsViewController.h
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MedicinesFilter.h"

@interface SearchResultsViewController : UITableViewController

@property (nonatomic , strong) MedicinesFilter* searchFilter;

@end
