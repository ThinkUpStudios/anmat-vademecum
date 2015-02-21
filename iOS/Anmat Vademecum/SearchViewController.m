//
//  SearchViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "SearchViewController.h"
#import "MainSearchSectionViewController.h"

@interface SearchViewController()

@property (weak, nonatomic) IBOutlet UITextField *txtComercialName;
@property (weak, nonatomic) IBOutlet UITextField *txtLaboratory;
@property UIPickerView *pkvLaboratories;

@end

@implementation SearchViewController

NSArray *laboratories;

-(void) viewDidLoad {
    [super viewDidLoad];
    
    self.txtComercialName.delegate = self;
    self.txtLaboratory.delegate = self;

    laboratories = @[@"Bagho", @"Boehringer", @"Roche", @"Bayer", @"Pfizer", @"Novartis", @"Panalab", @"Gador", @"Ivax", @"Eurolab"];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (BOOL) textFieldShouldBeginEditing:(UITextField *)textField {
    if ([textField isEqual:self.txtLaboratory]) {
        [textField resignFirstResponder];
        
        CGRect laboratoriesFrame = CGRectMake(0, 40, 0, 0);
        
        self.pkvLaboratories = [[UIPickerView alloc] initWithFrame:laboratoriesFrame];
        self.pkvLaboratories.showsSelectionIndicator = YES;
        self.pkvLaboratories.dataSource = self;
        self.pkvLaboratories.delegate = self;
        
        textField.inputView = self.pkvLaboratories;
    }
    
    return YES;
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    
    return YES;
}

-(NSInteger) numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 1;
}

-(NSInteger) pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    return laboratories.count;
}

-(NSString*) pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
    return laboratories[row];
}

-(void) pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
    self.txtLaboratory.text = laboratories[row];
    [self.txtLaboratory resignFirstResponder];

}

@end
