//
//  MainDetailsViewController.h
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Medicine.h"

@interface MainDetailsViewController : UIViewController<UIScrollViewDelegate>

@property (nonatomic , strong) Medicine* medicine;

@end
