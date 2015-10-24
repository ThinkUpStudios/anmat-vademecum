//
//  FormulaDetailsViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/21/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "FormulaDetailsViewController.h"
#import "ActiveComponentInfoViewController.h"
#import "ActiveComponentService.h"
#import "Formula.h"
#import "Component.h"
#import "FormulaParser.h"

@implementation FormulaDetailsViewController {
    Formula *formula;
    ActiveComponentService *componentsService;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    componentsService = [[ActiveComponentService alloc] init];
    formula = [FormulaParser parse:self.medicine.genericName];
    
    self.edgesForExtendedLayout = UIRectEdgeAll;
    self.tableView.contentInset = UIEdgeInsetsMake(0., 0., CGRectGetHeight(self.tabBarController.tabBar.frame), 0);
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    if([formula getComponents].count > 0) {
        return 1;
    } else {
        UILabel *lblMessage = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, self.view.bounds.size.width, self.view.bounds.size.height)];
        
        lblMessage.text = @"Sin Componentes";
        lblMessage.textColor = [UIColor grayColor];
        lblMessage.numberOfLines = 0;
        lblMessage.textAlignment = NSTextAlignmentCenter;
        [lblMessage sizeToFit];
        
        self.tableView.backgroundView = lblMessage;
        self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        
        return 0;
    }
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [formula getComponents].count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView
                             dequeueReusableCellWithIdentifier:@"FormulaCell"
                             forIndexPath:indexPath];
    Component *component = [[formula getComponents] objectAtIndex:indexPath.row];
    
    UILabel *lblComponent = (UILabel *)[cell.contentView viewWithTag:1];
    UILabel *lblQuantity = (UILabel *)[cell.contentView viewWithTag:2];
    
    [lblComponent setText:component.activeComponent];
    [lblQuantity setText:component.proportion];
    
    return cell;
}

-(void)tableView:(UITableView *)tableView accessoryButtonTappedForRowWithIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *selectedCell = [tableView cellForRowAtIndexPath:indexPath];
    
    [self performSegueWithIdentifier:@"ShowActiveComponent" sender:selectedCell];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([[segue identifier] isEqualToString:@"ShowActiveComponent"]) {
        ActiveComponentInfoViewController *activeComponent = segue.destinationViewController;
        NSIndexPath *selectedIndex = [self.tableView indexPathForCell:sender];
        Component *selectedFormulaComponent = [[formula getComponents] objectAtIndex:selectedIndex.item];
        ActiveComponent *component = [componentsService getByName:selectedFormulaComponent.activeComponent];
        
        activeComponent.name = selectedFormulaComponent.activeComponent;
        activeComponent.component = component;
    }
}

@end
