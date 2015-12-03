//
//  PregnancyDetailsViewController.m
//  VNM
//
//  Created by mag on 12/3/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import "PregnancyDetailsViewController.h"
#import "AutoLayoutCell3Lines.h"
#import "SectionRow3Lines.h"

static NSString * const AutoLayoutCellIdentifier = @"PregnancyDetailCell";

@interface PregnancyDetailsViewController ()

@end

@implementation PregnancyDetailsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [[self.group getComponents] count];
}

- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    return [self basicCellAtIndexPath:indexPath];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return [self heightForBasicCellAtIndexPath:indexPath];
}

-(void)tableView:(UITableView *)tableView willDisplayHeaderView:(UIView *)view forSection:(NSInteger)section {
    UITableViewHeaderFooterView *headerIndexText = (UITableViewHeaderFooterView *)view;
    
    headerIndexText.backgroundView.backgroundColor = [UIColor colorWithRed:239/255.0 green:239/255.0 blue:239/255.0 alpha:255.0];
    
    headerIndexText.textLabel.textColor = [UIColor colorWithRed:38/255.0 green:98/255.0 blue:140/255.0 alpha:255.0];
}

-(NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    return self.group.name;
}

- (AutoLayoutCell3Lines *)basicCellAtIndexPath:(NSIndexPath *)indexPath {
    AutoLayoutCell3Lines *cell = [self.tableView dequeueReusableCellWithIdentifier:AutoLayoutCellIdentifier forIndexPath:indexPath];
    
    [self configureBasicCell:cell atIndexPath:indexPath];
    
    return cell;
}

- (void)configureBasicCell:(AutoLayoutCell3Lines *)cell atIndexPath:(NSIndexPath *)indexPath {
    PregnancyComponent *component = [self.group getComponents][indexPath.row];
    
    [self setContentForCell:cell line1:component.ifa line2:component.medicines line3:component.clasification blankAsDash:indexPath.row > 0];
}

- (void)setContentForCell:(AutoLayoutCell3Lines *)cell line1:(NSString *)line1 line2:(NSString *)line2 line3:(NSString *) line3 blankAsDash:(BOOL) blankAsDash {
    
    if(line1 == nil || line1.length == 0) {
        line1 = blankAsDash ? @"-" : line1;
    }
    
    if(line2 == nil || line2.length == 0) {
        line2 = blankAsDash ? @"-" : line2;
    }
    
    if(line3 == nil || line3.length == 0) {
        line3 = blankAsDash ? @"-" : line3;
    }
    
    [cell.lblLine1 setText:line1];
    [cell.lblLine2 setText:line2];
    [cell.lblLine3 setText:line3];
}

- (CGFloat)heightForBasicCellAtIndexPath:(NSIndexPath *)indexPath {
    static AutoLayoutCell3Lines *sizingCell = nil;
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        sizingCell = [self.tableView dequeueReusableCellWithIdentifier:AutoLayoutCellIdentifier];
    });
    
    [self configureBasicCell:sizingCell atIndexPath:indexPath];
    
    return [self calculateHeightForConfiguredSizingCell:sizingCell];
}

- (CGFloat)calculateHeightForConfiguredSizingCell:(UITableViewCell *)sizingCell {
    [sizingCell setNeedsLayout];
    [sizingCell layoutIfNeeded];
    
    CGSize size = [sizingCell.contentView systemLayoutSizeFittingSize:UILayoutFittingCompressedSize];
    
    sizingCell.bounds = CGRectMake(0.0f, 0.0f, CGRectGetWidth(self.tableView.frame), CGRectGetHeight(sizingCell.bounds));
    
    return size.height + 1.0f;
}

@end
