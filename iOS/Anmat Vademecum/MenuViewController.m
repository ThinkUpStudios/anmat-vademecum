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
#import "ContactAlertController.h"

@interface MenuViewController ()

@property (weak, nonatomic) IBOutlet UIBarButtonItem *btnBar;
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
    ContactAlertController *contactSheet = [ContactAlertController alertControllerWithTitle:@"ANMAT Responde" message:@"Datos de contacto" preferredStyle:UIAlertControllerStyleActionSheet];

    contactSheet.popoverPresentationController.barButtonItem = self.btnBar;
    contactSheet.popoverPresentationController.sourceView = self.view;
    
    [self
     presentViewController:contactSheet animated:YES completion:nil];
}

-(void) setBorder: (UISelectableButton *) button {
    [[button layer] setBorderWidth:1.0f];
    [[button layer] setBorderColor:[UIColor colorWithRed:17/255.0 green:55/255.0 blue:86/255.0 alpha:255].CGColor];
}

@end
