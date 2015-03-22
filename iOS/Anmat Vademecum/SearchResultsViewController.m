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

@interface SearchResultsViewController()

@end

@implementation SearchResultsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView {
    if(self.medicines.count > 0) {
        return 1;
    } else {
        UILabel *lblMessage = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, self.view.bounds.size.width, self.view.bounds.size.height)];
        
        lblMessage.text = @"Sin Resultados";
        lblMessage.textColor = [UIColor grayColor];
        lblMessage.numberOfLines = 0;
        lblMessage.textAlignment = NSTextAlignmentCenter;
        [lblMessage sizeToFit];
        
        self.tableView.backgroundView = lblMessage;
        self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        
        return 0;
    }
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.medicines count];
}

- (UITableViewCell *) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView
                             dequeueReusableCellWithIdentifier:@"MedicineCell"
                             forIndexPath:indexPath];

    Medicine *medicine = [self.medicines objectAtIndex:indexPath.row];
    
    UILabel * lblGenericName = (UILabel *)[cell.contentView viewWithTag:1];
    UILabel * lblComercialName = (UILabel *)[cell.contentView viewWithTag:2];
    UILabel * lblLaboratory = (UILabel *)[cell.contentView viewWithTag:3];
    UILabel * lblCertificate = (UILabel *)[cell.contentView viewWithTag:4];
    UILabel * lblForm = (UILabel *)[cell.contentView viewWithTag:5];
    UILabel * lblPrice = (UILabel *)[cell.contentView viewWithTag:6];
    
    [lblGenericName setText:medicine.genericName];
    [lblComercialName setText:medicine.comercialName];
    [lblLaboratory setText:medicine.laboratory];
    [lblCertificate setText:medicine.certificate];
    [lblForm setText:medicine.form];
    
    NSMutableString *price = [[NSMutableString alloc] init];
    
    [price appendString:@"$"];
    [price appendString:[NSString stringWithFormat:@"%.2f", medicine.price]];
    
    [lblPrice setText:price];
    
    if(indexPath.item % 2 == 1) {
        [cell setBackgroundColor:[UIColor whiteColor]];
    } else {
        [cell setBackgroundColor:[UIColor colorWithRed:239/255.0 green:239/255.0 blue:239/255.0 alpha:1]];
    }
    
    return cell;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 135;
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([[segue identifier] isEqualToString:@"ShowDetails"]) {
        UITabBarController *tabs = segue.destinationViewController;
        MainDetailsViewController *details = [tabs.viewControllers objectAtIndex:0];
        FormulaDetailsViewController *formula = [tabs.viewControllers objectAtIndex:1];
        
        NSIndexPath *selectedIndex = [self.tableView indexPathForCell:sender];
        Medicine * selectedMed = [self.medicines objectAtIndex:selectedIndex.item];
        
        details.medicine = selectedMed;
        formula.medicine = selectedMed;
    }
}

@end
