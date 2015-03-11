//
//  MenuViewController.m
//  Anmat Vademecum
//
//  Created by mag on 3/7/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "MenuViewController.h"
#import <QuartzCore/QuartzCore.h>
#import "UISelectableButton.h"

@interface MenuViewController ()

@property (weak, nonatomic) IBOutlet UISelectableButton *btnMeds;
@property (weak, nonatomic) IBOutlet UISelectableButton *btnInfo;

@end

@implementation MenuViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [[self.btnMeds layer] setBorderWidth:1.0f];
    [[self.btnMeds layer] setBorderColor:[UIColor colorWithRed:17/255.0 green:55/255.0 blue:86/255.0 alpha:255].CGColor];
    
    [[self.btnInfo layer] setBorderWidth:1.0f];
    [[self.btnInfo layer] setBorderColor:[UIColor colorWithRed:17/255.0 green:55/255.0 blue:86/255.0 alpha:255].CGColor];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
