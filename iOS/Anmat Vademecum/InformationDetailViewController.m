//
//  PrescriptionViewController.m
//  VNM
//
//  Created by mag on 12/2/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import "InformationDetailViewController.h"
#import "AutoLayoutCell1Line.h"
#import "SectionRow1Line.h"

static NSString * const AutoLayoutCellIdentifier = @"InformationDetailCell";

@interface InformationDetailViewController ()

@end

@implementation InformationDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.tableView.sectionHeaderHeight = 40.0;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 1;
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
    return self.section.name;
}

- (AutoLayoutCell1Line *)basicCellAtIndexPath:(NSIndexPath *)indexPath {
    AutoLayoutCell1Line *cell = [self.tableView dequeueReusableCellWithIdentifier:AutoLayoutCellIdentifier forIndexPath:indexPath];
    
    [self configureBasicCell:cell atIndexPath:indexPath];
    
    return cell;
}

- (void)configureBasicCell:(AutoLayoutCell1Line *)cell atIndexPath:(NSIndexPath *)indexPath {
    SectionRow1Line *content = [self.section getRows][0];
    
    [self setContentForCell:cell content:content.line1];
}

- (void)setContentForCell:(AutoLayoutCell1Line *)cell content:(NSString *)content {
    NSString *text = content ?: NSLocalizedString(@"[-]", nil);
    
    [cell.lblLine1 setText:text];
}

- (CGFloat)heightForBasicCellAtIndexPath:(NSIndexPath *)indexPath {
    static AutoLayoutCell1Line *sizingCell = nil;
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
