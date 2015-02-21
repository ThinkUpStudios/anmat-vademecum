//
//  MainDetailsViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "MainDetailsViewController.h"

@interface MainDetailsViewController ()

@property (weak, nonatomic) IBOutlet UILabel *lblCertificate;
@property (weak, nonatomic) IBOutlet UILabel *lblLaboratory;
@property (weak, nonatomic) IBOutlet UILabel *lblGenericName;
@property (weak, nonatomic) IBOutlet UILabel *lblComercialName;
@property (weak, nonatomic) IBOutlet UILabel *lblCountry;
@property (weak, nonatomic) IBOutlet UILabel *lblForm;
@property (weak, nonatomic) IBOutlet UILabel *lblTroquel;
@property (weak, nonatomic) IBOutlet UILabel *lblRequestCondition;
@property (weak, nonatomic) IBOutlet UILabel *lblTrazability;
@property (weak, nonatomic) IBOutlet UILabel *lblPresentation;
@property (weak, nonatomic) IBOutlet UILabel *lblPrice;
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
    self.lblCertificate.text = @"47191";
    self.lblLaboratory.text = @"LABORATORIOS BETA SOCIEDAD ANONIMA";
    self.lblGenericName.text = @"PAROXETINA 10 MG/COMPRIMIDO";
    self.lblComercialName.text = @"PSICOASTEN 10 mg";
    self.lblForm.text = @"PAROXETINA 10 MG/COMPRIMIDO";
    self.lblTroquel.text = @"487430";
    self.lblRequestCondition.text = @"BAJO RECETA ARCHIVADA (PSICOTROPICOS LISTA IV)";
    self.lblTrazability.text = @"Fuera de disposición";
    self.lblPresentation.text = @"BLISTER por 30";
    self.lblPrice.text = @"$ 139,19";
}

@end
