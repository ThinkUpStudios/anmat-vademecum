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
@property (weak, nonatomic) IBOutlet UISelectableButton *btnComponents;

@end

@implementation MenuViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setBorder:self.btnMeds];
    [self setBorder:self.btnComponents];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

-(void) setBorder: (UISelectableButton *) button {
    [[button layer] setBorderWidth:1.0f];
    [[button layer] setBorderColor:[UIColor colorWithRed:17/255.0 green:55/255.0 blue:86/255.0 alpha:255].CGColor];
}

@end
