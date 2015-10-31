//
//  AutoLayoutCellWithSubtitle.h
//  VNM
//
//  Created by mag on 10/24/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AutoLayoutLabel.h"

@interface AutoLayoutCellWithDetail : UITableViewCell

@property (nonatomic, weak) IBOutlet AutoLayoutLabel *lblTitle;

@property (nonatomic, weak) IBOutlet AutoLayoutLabel *lblSubtitle;

@end
