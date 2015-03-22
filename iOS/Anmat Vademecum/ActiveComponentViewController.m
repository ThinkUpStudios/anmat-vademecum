//
//  ActiveComponentViewController.m
//  Anmat Vademecum
//
//  Created by mag on 3/22/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "ActiveComponentViewController.h"

@interface ActiveComponentViewController ()

@property (weak, nonatomic) IBOutlet UILabel *txtActiveComponent;

@end

@implementation ActiveComponentViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.txtActiveComponent.text = self.componentName;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
