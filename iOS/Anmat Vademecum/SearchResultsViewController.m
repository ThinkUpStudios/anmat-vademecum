//
//  SearchResultsViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "SearchResultsViewController.h"
#import "Medicine.h"
#import "MainDetailsViewController.h"
#import "FormulaDetailsViewController.h"
#import "MBProgressHUD.h"
#import "MedicineService.h"

@interface SearchResultsViewController()

@end

@implementation SearchResultsViewController {
    MedicineService *medicineService;
    NSArray *medicines;
    BOOL isLoading;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    medicineService = [[MedicineService alloc] init];
    medicines = [[NSArray alloc] init];
    isLoading = YES;
    
    [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_LOW, 0), ^{
        if(self.searchFilter.activeComponent != nil && self.searchFilter.activeComponent.length > 0) {
            medicines = [medicineService getMedicines:self.searchFilter.activeComponent];
        } else if(self.searchFilter.medicine != nil) {
            medicines = [medicineService getSimilarMedicines:self.searchFilter.medicine];
        } else {
            medicines = [medicineService getMedicines:self.searchFilter.genericName comercialName:self.searchFilter.comercialName laboratory:self.searchFilter.laboratory];
        }
        
        dispatch_async(dispatch_get_main_queue(), ^{
            isLoading = NO;
            [self.tableView reloadData];
            [MBProgressHUD hideHUDForView:self.view animated:YES];
        });
    });
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView {
    if(medicines.count > 0) {
        return 1;
    } else {
        if(isLoading == NO) {
            UILabel *lblMessage = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, self.view.bounds.size.width, self.view.bounds.size.height)];
            
            lblMessage.text = @"Sin Resultados";
            lblMessage.textColor = [UIColor grayColor];
            lblMessage.numberOfLines = 0;
            lblMessage.textAlignment = NSTextAlignmentCenter;
            [lblMessage sizeToFit];
            
            self.tableView.backgroundView = lblMessage;
        }
        
        self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        
        return 0;
    }
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [medicines count];
}

- (UITableViewCell *) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView
                             dequeueReusableCellWithIdentifier:@"MedicineCell"
                             forIndexPath:indexPath];

    Medicine *medicine = [medicines objectAtIndex:indexPath.row];
    
    UILabel * lblGenericName = (UILabel *)[cell.contentView viewWithTag:1];
    UILabel * lblComercialName = (UILabel *)[cell.contentView viewWithTag:2];
    UILabel * lblForm = (UILabel *)[cell.contentView viewWithTag:3];
    UILabel * lblCertificate = (UILabel *)[cell.contentView viewWithTag:4];
    UILabel * lblPresentation = (UILabel *)[cell.contentView viewWithTag:5];
    UILabel * lblLaboratory = (UILabel *)[cell.contentView viewWithTag:6];
    UILabel * lblPrice = (UILabel *)[cell.contentView viewWithTag:7];
    
    [lblGenericName setText:medicine.genericName];
    [lblComercialName setText:medicine.comercialName];
    [lblForm setText:medicine.form];
    [lblCertificate setText:medicine.certificate];
    [lblPresentation setText:medicine.presentation];
    [lblLaboratory setText:medicine.laboratory];
    [lblPrice setText:medicine.price];
    
    if(indexPath.item % 2 == 1) {
        [cell setBackgroundColor:[UIColor whiteColor]];
    } else {
        [cell setBackgroundColor:[UIColor colorWithRed:239/255.0 green:239/255.0 blue:239/255.0 alpha:1]];
    }
    
    return cell;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 164;
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([[segue identifier] isEqualToString:@"ShowDetails"]) {
        UITabBarController *tabs = segue.destinationViewController;
        MainDetailsViewController *details = [tabs.viewControllers objectAtIndex:0];
        FormulaDetailsViewController *formula = [tabs.viewControllers objectAtIndex:1];
        
        NSIndexPath *selectedIndex = [self.tableView indexPathForCell:sender];
        Medicine * selectedMed = [medicines objectAtIndex:selectedIndex.item];
        
        details.medicine = selectedMed;
        formula.medicine = selectedMed;
    }
}

@end
