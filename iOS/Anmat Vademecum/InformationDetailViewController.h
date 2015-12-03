//
//  PrescriptionViewController.h
//  VNM
//
//  Created by mag on 12/2/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Section.h"

@interface InformationDetailViewController : UITableViewController

@property (nonatomic, strong) NSString* header;
@property (nonatomic, strong) Section* section;

@end
