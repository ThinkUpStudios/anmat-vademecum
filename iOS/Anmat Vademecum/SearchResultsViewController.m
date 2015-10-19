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
#import "MenuViewController.h"
#import "MedicinesFilter.h"

@interface SearchResultsViewController()

@end

@implementation SearchResultsViewController {
    MedicineService *medicineService;
    NSArray *medicines;
    BOOL isLoading;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    UIBarButtonItem *btnSort = [[UIBarButtonItem alloc] init];
    UIImage *imgSort = [UIImage imageNamed:@"Sort"];
    
    [btnSort setImage:imgSort];
    [btnSort setTarget:self];
    [btnSort setAction:@selector(showSortOptions:)];
    
    UIBarButtonItem *btnHome = [[UIBarButtonItem alloc] init];
    UIImage *imgHome = [UIImage imageNamed:@"Home"];
    
    [btnHome setImage:imgHome];
    [btnHome setTarget:self];
    [btnHome setAction:@selector(showHome:)];
    
    self.navigationItem.rightBarButtonItems = [NSArray arrayWithObjects:btnSort, btnHome, nil];
    
    medicineService = [[MedicineService alloc] init];
    medicines = [[NSArray alloc] init];
    
    [self loadMedicines:Price];
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
    return 196;
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

- (IBAction)showSortOptions:(id)sender {
    UIAlertController *sortSheet = [UIAlertController alertControllerWithTitle:@"Ordenar por..." message:@"" preferredStyle:UIAlertControllerStyleActionSheet];
    
    sortSheet.popoverPresentationController.barButtonItem = self.navigationItem.rightBarButtonItems[0];
    sortSheet.popoverPresentationController.sourceView = self.view;
    
    UIAlertAction *priceSortAction = [UIAlertAction actionWithTitle:@"Precio" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
        [self dismissViewControllerAnimated:YES completion:nil];
        [self loadMedicines:Price];
    }];
    UIAlertAction *formSortAction = [UIAlertAction actionWithTitle:@"Forma Farmaceútica" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
        [self dismissViewControllerAnimated:YES completion:nil];
        [self loadMedicines:Form];
    }];
    UIAlertAction *genericSortAction = [UIAlertAction actionWithTitle:@"Nombre Genérico" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
        [self dismissViewControllerAnimated:YES completion:nil];
        [self loadMedicines:GenericName];
    }];
    UIAlertAction *comercialSortAction = [UIAlertAction actionWithTitle:@"Nombre Comercial" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
        [self dismissViewControllerAnimated:YES completion:nil];
        [self loadMedicines:ComercialName];
    }];
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"Cancelar" style:UIAlertActionStyleCancel handler:^(UIAlertAction * action) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }];
    
    [sortSheet addAction:priceSortAction];
    [sortSheet addAction:formSortAction];
    [sortSheet addAction:genericSortAction];
    [sortSheet addAction:comercialSortAction];
    [sortSheet addAction:cancelAction];
    
    [self
     presentViewController:sortSheet animated:YES completion:nil];
}

-(void) showHome:(id) sender {
    MenuViewController *about = (MenuViewController *) [self.storyboard instantiateViewControllerWithIdentifier:@"MenuViewController"];
    
    [self.navigationController pushViewController:about animated:YES];
}

- (void) loadMedicines: (SortOptions) orderBy {
    isLoading = YES;
    
    [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_LOW, 0), ^{
        medicines = [medicineService getMedicinesByFilter:self.searchFilter orderBy:orderBy];
        
        dispatch_async(dispatch_get_main_queue(), ^{
            isLoading = NO;
            [self.tableView reloadData];
            [MBProgressHUD hideHUDForView:self.view animated:YES];
        });
    });
}

@end
