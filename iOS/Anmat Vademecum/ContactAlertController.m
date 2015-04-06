//
//  ContactAlertController.m
//  Anmat Vademecum
//
//  Created by mag on 4/5/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "ContactAlertController.h"

@interface ContactAlertController ()

@end

@implementation ContactAlertController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    NSString *phoneNumber = @"0800-333-1234";
    
    UIAlertAction *phoneAction = [UIAlertAction actionWithTitle:[@"Tel: " stringByAppendingString:phoneNumber] style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:[@"telprompt://" stringByAppendingString:phoneNumber]]];
        [self dismissViewControllerAnimated:YES completion:nil];
    }];
    UIAlertAction *faxAction = [UIAlertAction actionWithTitle:@"Fax: (011) 4340-0800/5252-8200 int 1159" style:UIAlertActionStyleDefault handler:nil];
    
    NSString *mailAddress = @"responde@anmat.gov.ar";
    
    UIAlertAction *mailAction = [UIAlertAction actionWithTitle:[@"Email: " stringByAppendingString:mailAddress] style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:[@"mailto://" stringByAppendingString:mailAddress]]];
        [self dismissViewControllerAnimated:YES completion:nil];
    }];
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"Cancelar" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }];
    
    [self addAction:phoneAction];
    [self addAction:faxAction];
    [self addAction:mailAction];
    [self addAction:cancelAction];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
