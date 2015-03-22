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
    [self setLayoutConstraints];
    [self loadMedicine];
}

- (void) didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    [scrollView setContentOffset: CGPointMake(0, scrollView.contentOffset.y)];
}

- (void) setLayoutConstraints {
    NSLayoutConstraint *topConstraint = [NSLayoutConstraint constraintWithItem:self.cntDetails
                                                                     attribute:NSLayoutAttributeTop
                                                                     relatedBy:0
                                                                        toItem:self.view
                                                                     attribute:NSLayoutAttributeTopMargin
                                                                    multiplier:1.0
                                                                      constant:0];
    [self.view addConstraint:topConstraint];
    
    /*NSLayoutConstraint *leftConstraint = [NSLayoutConstraint constraintWithItem:self.cntDetails
     attribute:NSLayoutAttributeLeading
     relatedBy:0
     toItem:self.view
     attribute:NSLayoutAttributeLeft
     multiplier:1.0
     constant:0];
     [self.view addConstraint:leftConstraint];
     
     NSLayoutConstraint *rightConstraint = [NSLayoutConstraint constraintWithItem:self.cntDetails
     attribute:NSLayoutAttributeTrailing
     relatedBy:0
     toItem:self.view
     attribute:NSLayoutAttributeRight
     multiplier:1.0
     constant:0];
     [self.view addConstraint:rightConstraint];*/
    
    NSLayoutConstraint *bottomConstraint = [NSLayoutConstraint constraintWithItem:self.cntDetails
                                                                        attribute:NSLayoutAttributeBottom
                                                                        relatedBy:0
                                                                           toItem:self.view
                                                                        attribute:NSLayoutAttributeBottomMargin
                                                                       multiplier:1.0
                                                                         constant:0];
    [self.view addConstraint:bottomConstraint];
}

-(void) loadMedicine {
    self.lblCertificate.text = self.medicine.certificate;
    self.lblTroquel.text = self.medicine.troquel;
    self.lblLaboratory.text = self.medicine.laboratory;
    self.lblComercialName.text = self.medicine.comercialName;
    self.lblGenericName.text = self.medicine.genericName;
    self.lblRequestCondition.text = self.medicine.requestCondition;
    self.lblTrazability.text = self.medicine.trazability;
    self.lblPresentation.text = self.medicine.presentation;
    self.lblForm.text = self.medicine.form;
    
    NSMutableString *price = [[NSMutableString alloc] init];
    
    [price appendString:@"$"];
    [price appendString:[NSString stringWithFormat:@"%.2f", self.medicine.price]];
    
    self.lblPrice.text = price;
    self.lblCountry.text = self.medicine.country;
    self.lblGtin.text = self.medicine.gtin;
}

@end
