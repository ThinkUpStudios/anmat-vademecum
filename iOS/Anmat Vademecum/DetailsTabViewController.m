//
//  DetailsTabViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "DetailsTabViewController.h"

@interface DetailsTabViewController ()

@end

@implementation DetailsTabViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.navigationItem.title = @"Detalle";
    
    if ([self respondsToSelector:@selector(edgesForExtendedLayout)]) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        self.extendedLayoutIncludesOpaqueBars = NO;
        self.automaticallyAdjustsScrollViewInsets = NO;
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
