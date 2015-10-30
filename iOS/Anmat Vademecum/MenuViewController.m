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
#import "AboutViewController.h"

@interface MenuViewController ()
@property (weak, nonatomic) IBOutlet UIView *contentMenu;
@property (weak, nonatomic) IBOutlet UIScrollView *scrMenu;
@property (weak, nonatomic) IBOutlet UIToolbar *toolBar;
@property (weak, nonatomic) IBOutlet UISelectableButton *btnMeds;
@property (weak, nonatomic) IBOutlet UISelectableButton *btnComponents;
@property (weak, nonatomic) IBOutlet UISelectableButton *btnCommunication;
@property (weak, nonatomic) IBOutlet UISelectableButton *btnPregnancy;
@property (weak, nonatomic) IBOutlet UISelectableButton *btnInfo;

- (IBAction)showCommunicationInfo:(id)sender;

@end

@implementation MenuViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.navigationItem.hidesBackButton = YES;
    
    UIBarButtonItem *btnHelp = [[UIBarButtonItem alloc] init];
    UIImage *imgHelp = [UIImage imageNamed:@"Help"];
    
    [btnHelp setImage:imgHelp];
    [btnHelp setTarget:self];
    [btnHelp setAction:@selector(showAbout:)];
    
    UIBarButtonItem *btnContact = [[UIBarButtonItem alloc] init];
    UIImage *imgContact = [UIImage imageNamed:@"Contact"];
    
    [btnContact setImage:imgContact];
    [btnContact setTarget:self];
    [btnContact setAction:@selector(showContactInfo:)];
    
    self.navigationItem.rightBarButtonItems = [NSArray arrayWithObjects:btnHelp, btnContact, nil];
    
    [self setBorder:self.btnMeds];
    [self setBorder:self.btnComponents];
    [self setBorder:self.btnCommunication];
    [self setBorder:self.btnPregnancy];
    [self setBorder:self.btnInfo];
    
    self.edgesForExtendedLayout = UIRectEdgeNone;
    self.scrMenu.contentInset = UIEdgeInsetsMake(0., 0., CGRectGetHeight(self.toolBar.frame), 0);
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

-(void) setBorder: (UISelectableButton *) button {
    [[button layer] setBorderWidth:1.0f];
    [[button layer] setBorderColor:[UIColor colorWithRed:17/255.0 green:55/255.0 blue:86/255.0 alpha:255].CGColor];
    [button setSelected:NO];
    [button setHighlighted:NO];
}

-(void) showAbout:(id) sender {
    AboutViewController *about = (AboutViewController *) [self.storyboard instantiateViewControllerWithIdentifier:@"AboutViewController"];
    
    [self.navigationController pushViewController:about animated:YES];
}

-(void) showContactInfo:(id) sender {
    ContactAlertController *contactSheet = [ContactAlertController alertControllerWithTitle:@"ANMAT Responde" message:@"Datos de contacto" preferredStyle:UIAlertControllerStyleActionSheet];
    
    contactSheet.popoverPresentationController.barButtonItem = self.navigationItem.rightBarButtonItems[1];
    contactSheet.popoverPresentationController.sourceView = self.view;
    
    [self
     presentViewController:contactSheet animated:YES completion:nil];
}

- (IBAction)showCommunicationInfo:(id)sender {    
    NSURL *url = [NSURL URLWithString:@"http://sistemas.anmat.gov.ar/aplicaciones_net/applications/fvg_eventos_adversos_nuevo/index.html"];
    
    if ([[UIApplication sharedApplication] canOpenURL:url]) {
        [[UIApplication sharedApplication] openURL:url];
    }
}

@end
