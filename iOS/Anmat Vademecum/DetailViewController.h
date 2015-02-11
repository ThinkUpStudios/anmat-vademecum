//
//  DetailViewController.h
//  Anmat Vademecum
//
//  Created by mag on 2/11/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DetailViewController : UIViewController

@property (strong, nonatomic) id detailItem;
@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;

@end

