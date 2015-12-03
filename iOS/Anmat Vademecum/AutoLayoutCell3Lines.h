//
//  AutoLayoutCell3Lines.h
//  VNM
//
//  Created by mag on 10/31/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AutoLayoutLabel.h"

@interface AutoLayoutCell3Lines : UITableViewCell

@property (nonatomic, weak) IBOutlet AutoLayoutLabel *lblLine1;

@property (nonatomic, weak) IBOutlet AutoLayoutLabel *lblLine2;

@property (nonatomic, weak) IBOutlet AutoLayoutLabel *lblLine3;

@end
