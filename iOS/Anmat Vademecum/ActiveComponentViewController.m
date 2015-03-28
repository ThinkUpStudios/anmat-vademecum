//
//  ActiveComponentViewController.m
//  Anmat Vademecum
//
//  Created by mag on 3/22/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "ActiveComponentViewController.h"
#import "SearchResultsViewController.h"
#import "MedicineService.h"

@interface ActiveComponentViewController ()

@property (weak, nonatomic) IBOutlet UILabel *txtActiveComponent;

@end

@implementation ActiveComponentViewController

MedicineService *medicineService;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.txtActiveComponent.text = self.componentName;
    
    medicineService = [[MedicineService alloc] init];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([[segue identifier] isEqualToString:@"ShowMedicinesWithSameComponent"]) {
        SearchResultsViewController *results = segue.destinationViewController;
        
        results.medicines = [medicineService getMedicines:self.txtActiveComponent.text];
    }
}

@end
