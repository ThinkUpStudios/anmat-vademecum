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
@property (weak, nonatomic) IBOutlet UISelectableButton *btnInfo;
- (IBAction)btnContact:(id)sender;

@end

@implementation MenuViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setBorder:self.btnMeds];
    [self setBorder:self.btnComponents];
    [self setBorder:self.btnInfo];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (IBAction)btnContact:(id)sender {
    UIAlertController *contactSheet = [UIAlertController alertControllerWithTitle:@"ANMAT Responde" message:@"Datos de contacto" preferredStyle:UIAlertControllerStyleActionSheet];
    
    NSString *phoneNumber = @"0800-333-1234";
    
    UIAlertAction *phoneAction = [UIAlertAction actionWithTitle:[@"Tel: " stringByAppendingString:phoneNumber] style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:[@"telprompt://" stringByAppendingString:phoneNumber]]];
        [contactSheet dismissViewControllerAnimated:YES completion:nil];
    }];
    UIAlertAction *faxAction = [UIAlertAction actionWithTitle:@"Fax: (011) 4340-0800/5252-8200 int 1159" style:UIAlertActionStyleDefault handler:nil];
    
    NSString *mailAddress = @"responde@anmat.gov.ar";
    
    UIAlertAction *mailAction = [UIAlertAction actionWithTitle:[@"Email: " stringByAppendingString:mailAddress] style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:[@"mailto://" stringByAppendingString:mailAddress]]];
        [contactSheet dismissViewControllerAnimated:YES completion:nil];
    }];
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"Cancelar" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
        [contactSheet dismissViewControllerAnimated:YES completion:nil];
    }];
    
    [contactSheet addAction:phoneAction];
    [contactSheet addAction:faxAction];
    [contactSheet addAction:mailAction];
    [contactSheet addAction:cancelAction];
    [self
     presentViewController:contactSheet animated:YES completion:nil];
}

-(void) setBorder: (UISelectableButton *) button {
    [[button layer] setBorderWidth:1.0f];
    [[button layer] setBorderColor:[UIColor colorWithRed:17/255.0 green:55/255.0 blue:86/255.0 alpha:255].CGColor];
}

@end
