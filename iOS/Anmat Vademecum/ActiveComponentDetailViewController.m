//
//  ActiveComponentDetailViewController.m
//  VNM
//
//  Created by mag on 10/24/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import "ActiveComponentDetailViewController.h"
#import "AutoLayoutCell.h"

static NSString * const AutoLayoutCellIdentifier = @"AutoLayoutCell";

@interface ActiveComponentDetailViewController ()

@end

@implementation ActiveComponentDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = self.section;
    self.tableView.sectionHeaderHeight = 40.0;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 1;
}

- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView {
    if(self.subtitles.count > 0) {
        return self.subtitles.count;
    } else {
        UILabel *lblMessage = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, self.view.bounds.size.width, self.view.bounds.size.height)];
        
        lblMessage.text = @"Sin Información";
        lblMessage.textColor = [UIColor grayColor];
        lblMessage.numberOfLines = 0;
        lblMessage.textAlignment = NSTextAlignmentCenter;
        [lblMessage sizeToFit];
        
        self.tableView.backgroundView = lblMessage;
        self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        
        return 0;
    }
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
    return self.subtitles[section];
}

- (AutoLayoutCell *)basicCellAtIndexPath:(NSIndexPath *)indexPath {
    AutoLayoutCell *cell = [self.tableView dequeueReusableCellWithIdentifier:AutoLayoutCellIdentifier forIndexPath:indexPath];
    
    [self configureBasicCell:cell atIndexPath:indexPath];
    
    return cell;
}

- (void)configureBasicCell:(AutoLayoutCell *)cell atIndexPath:(NSIndexPath *)indexPath {
    NSString *content = self.details[indexPath.section];
    
    [self setContentForCell:cell content:content];
}

- (void)setContentForCell:(AutoLayoutCell *)cell content:(NSString *)content {
    NSString *text = content ?: NSLocalizedString(@"[-]", nil);
    
    [cell.lblContent setText:text];
}

- (CGFloat)heightForBasicCellAtIndexPath:(NSIndexPath *)indexPath {
    static AutoLayoutCell *sizingCell = nil;
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