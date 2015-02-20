//
//  MainSearchSectionViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "MainSearchSectionViewController.h"

@interface MainSearchSectionViewController()

@property (weak, nonatomic) IBOutlet UITextField *txtGenericName;

@end

@implementation MainSearchSectionViewController

-(void) viewDidLoad {
    [super viewDidLoad];
    
    self.txtGenericName.delegate = self;
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    
    return YES;
}

@end
