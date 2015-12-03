//
//  MainDetailsViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "MainDetailsViewController.h"
#import "Medicine.h"
#import "DetailsTabViewController.h"
#import "AutoLayoutCell1Line.h"

static NSString * const AutoLayoutCellIdentifier = @"MainDetailCell";

@interface MainDetailsViewController ()

@end

@implementation MainDetailsViewController {
    NSMutableArray *medicineTitles;
    NSMutableArray *medicineDetails;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    if(self.medicine) {
        medicineTitles = [[NSMutableArray alloc] init];
        medicineDetails = [[NSMutableArray alloc] init];
        
        if(self.medicine.isRemediar == 1) {
            [medicineTitles addObject:@"Remediar"];
            [medicineDetails addObject:@"El Ministerio de Salud de la Nación garantiza la distribución gratuita de este medicamento mediante el programa REMEDIAR"];
        }
        
        [medicineTitles addObject:@"Nro. de Certificado"];
        [medicineTitles addObject:@"Nro. de Troquel"];
        [medicineTitles addObject:@"País de Industria"];
        [medicineTitles addObject:@"GTIN"];
        [medicineTitles addObject:@"Laboratorio"];
        [medicineTitles addObject:@"Nombre Comercial"];
        [medicineTitles addObject:@"Nombre Genérico"];
        [medicineTitles addObject:@"Condición de Expendio"];
        [medicineTitles addObject:@"Condición de Trazabilidad"];
        [medicineTitles addObject:@"Forma Farmaceútica"];
        [medicineTitles addObject:@"Presentación"];
        [medicineTitles addObject:@"Precio de Venta"];
        
        [medicineDetails addObject:self.medicine.certificate];
        [medicineDetails addObject:self.medicine.troquel];
        [medicineDetails addObject:self.medicine.country];
        [medicineDetails addObject:self.medicine.gtin];
        [medicineDetails addObject:self.medicine.laboratory];
        [medicineDetails addObject:self.medicine.comercialName];
        [medicineDetails addObject:self.medicine.genericName];
        [medicineDetails addObject:self.medicine.requestCondition];
        [medicineDetails addObject:self.medicine.trazability];
        [medicineDetails addObject:self.medicine.form];
        [medicineDetails addObject:self.medicine.presentation];
        
        NSString *price = self.medicine.price;
        
        if([price isEqualToString:@"U.H"]) {
            price = @"Presentación de Uso Hospitalario (U.H)";
        }
        
        [medicineDetails addObject:price];
    }
    
    self.tableView.sectionHeaderHeight = 40.0;
    self.edgesForExtendedLayout = UIRectEdgeAll;
    self.tableView.contentInset = UIEdgeInsetsMake(0., 0., CGRectGetHeight(self.tabBarController.tabBar.frame), 0);
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 1;
}

- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView {
    if(self.medicine) {
        return medicineTitles.count;
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
    return medicineTitles[section];
}

- (AutoLayoutCell1Line *)basicCellAtIndexPath:(NSIndexPath *)indexPath {
    AutoLayoutCell1Line *cell = [self.tableView dequeueReusableCellWithIdentifier:AutoLayoutCellIdentifier forIndexPath:indexPath];
    
    [self configureBasicCell:cell atIndexPath:indexPath];
    
    return cell;
}

- (void)configureBasicCell:(AutoLayoutCell1Line *)cell atIndexPath:(NSIndexPath *)indexPath {
    NSString *title = medicineTitles[indexPath.section];
    NSString *content = medicineDetails[indexPath.section];
    NSTextAlignment alignment = NSTextAlignmentLeft;
    BOOL bold = NO;
    
    if([title isEqualToString:@"Precio de Venta"]) {
        alignment = NSTextAlignmentRight;
        bold = YES;
    }
    
    if([title isEqualToString:@"Remediar"]) {
        alignment = NSTextAlignmentCenter;
        bold = YES;
    }
    
    [self setContentForCell:cell content:content alignment:alignment bold:bold];
}

- (void)setContentForCell:(AutoLayoutCell1Line *)cell content:(NSString *)content alignment:(NSTextAlignment) alignment bold:(BOOL) bold {
    NSString *text = content ?: NSLocalizedString(@"[-]", nil);
    
    if(bold) {
        [cell.lblLine1 setFont:[UIFont boldSystemFontOfSize:15]];
    }
    
    cell.lblLine1.textAlignment = alignment;
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
