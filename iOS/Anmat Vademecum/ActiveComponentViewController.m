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
#import "MedicinesFilter.h"

@interface ActiveComponentViewController ()

@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet UIView *contentView;
@property (weak, nonatomic) IBOutlet UILabel *lblActiveComponent;
@property (weak, nonatomic) IBOutlet UILabel *lblAction;
@property (weak, nonatomic) IBOutlet UILabel *lblIndication;
@property (weak, nonatomic) IBOutlet UILabel *lblPresentation;
@property (weak, nonatomic) IBOutlet UILabel *lblPosology;
@property (weak, nonatomic) IBOutlet UILabel *lblDuration;
@property (weak, nonatomic) IBOutlet UILabel *lblContraindication;
@property (weak, nonatomic) IBOutlet UILabel *lblObservation;

@end

@implementation ActiveComponentViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.lblActiveComponent.text = self.component.name;
    self.lblAction.text = self.component.action;
    self.lblIndication.text = self.component.indication;
    self.lblPresentation.text = self.component.presentation;
    self.lblPosology.text = self.component.posology;
    self.lblDuration.text = self.component.duration;
    self.lblContraindication.text = self.component.contraindication;
    self.lblObservation.text = self.component.observation;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([[segue identifier] isEqualToString:@"ShowMedicinesWithSameComponent"]) {
        SearchResultsViewController *results = segue.destinationViewController;
        MedicinesFilter *filter = [[MedicinesFilter alloc] init];
        
        filter.activeComponent = self.lblActiveComponent.text;
        results.searchFilter = filter;
    }
}

@end
