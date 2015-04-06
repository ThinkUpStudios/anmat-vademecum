//
//  MainDetailsViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "MainDetailsViewController.h"
#import "Medicine.h"
#import "DetailsTabViewController.h"

@interface MainDetailsViewController ()

@property (weak, nonatomic) IBOutlet UIView *cntDetails;
@property (weak, nonatomic) IBOutlet UILabel *lblCertificate;
@property (weak, nonatomic) IBOutlet UILabel *lblTroquel;
@property (weak, nonatomic) IBOutlet UILabel *lblLaboratory;
@property (weak, nonatomic) IBOutlet UILabel *lblComercialName;
@property (weak, nonatomic) IBOutlet UILabel *lblGenericName;
@property (weak, nonatomic) IBOutlet UILabel *lblRequestCondition;
@property (weak, nonatomic) IBOutlet UILabel *lblTrazability;
@property (weak, nonatomic) IBOutlet UILabel *lblForm;
@property (weak, nonatomic) IBOutlet UILabel *lblPresentation;
@property (weak, nonatomic) IBOutlet UILabel *lblPrice;
@property (weak, nonatomic) IBOutlet UILabel *lblCountry;
@property (weak, nonatomic) IBOutlet UILabel *lblGtin;

@end

@implementation MainDetailsViewController

- (void) viewDidLoad {
    [super viewDidLoad];
    [self loadMedicine];
}

- (void) didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

-(void) loadMedicine {
    self.lblCertificate.text = self.medicine.certificate;
    self.lblTroquel.text = self.medicine.troquel;
    self.lblLaboratory.text = self.medicin∫e.laboratory;
    self.lblComercialName.text = self.medicine.comercialName;
    self.lblGenericName.text = self.medicine.genericName;
    self.lblRequestCondition.text = self.medicine.requestCondition;
    self.lblTrazability.text = self.medicine.trazability;
    self.lblPresentation.text = self.medicine.presentation;
    self.lblForm.text = self.medicine.form;
    self.lblCountry.text = self.medicine.country;
    self.lblGtin.text = self.medicine.gtin;
    
    if(self.medicine.hospitalUsage == 1 &&
       (self.medicine.price == nil || self.medicine.price.length == 0 || [self.medicine.price isEqualToString:@"$-"])) {
        self.lblPrice.text = @"Presentación de Uso Hospitalario";
    } else {
        self.lblPrice.text = self.medicine.price;
    }
}

@end
