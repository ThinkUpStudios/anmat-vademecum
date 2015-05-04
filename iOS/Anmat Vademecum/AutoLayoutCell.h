//
//  AutoLayoutCell.h
//  VNM
//
//  Created by mag on 5/4/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AutoLayoutLabel.h"

@interface AutoLayoutCell : UITableViewCell

@property (nonatomic, weak) IBOutlet AutoLayoutLabel *lblContent;

@end
