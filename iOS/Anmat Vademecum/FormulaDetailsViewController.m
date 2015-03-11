//
//  FormulaDetailsViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/21/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "FormulaDetailsViewController.h"
#import "FormulaComponent.h"

@interface FormulaDetailsViewController ()

@property NSMutableArray *formulaComponents;

@end

@implementation FormulaDetailsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.formulaComponents = [[NSMutableArray alloc] init];
    [self loadFormulaComponents];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    if(self.formulaComponents.count > 0) {
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
    return [self.formulaComponents count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView
                             dequeueReusableCellWithIdentifier:@"FormulaCell"
                             forIndexPath:indexPath];
    FormulaComponent *component = [self.formulaComponents objectAtIndex:indexPath.row];
    
    UILabel *lblComponent = (UILabel *)[cell.contentView viewWithTag:1];
    UILabel *lblQuantity = (UILabel *)[cell.contentView viewWithTag:2];
    
    [lblComponent setText:component.name];
    [lblQuantity setText:component.quantity];
    
    return cell;
}

- (void)loadFormulaComponents {
    NSString *genericName = self.medicine.genericName;
    NSArray *formulaDetails = [genericName componentsSeparatedByString:@"+"];
    
    for (NSString *formulaDetail in formulaDetails) {
        if(formulaDetail == nil || formulaDetail.length == 0) {
            continue;
        }
        
        NSUInteger byteLength = [formulaDetail lengthOfBytesUsingEncoding:NSUTF8StringEncoding];
        char buffer[byteLength + 1];
        const char *utf8_buffer = [formulaDetail cStringUsingEncoding:NSUTF8StringEncoding];
        
        strncpy(buffer, utf8_buffer, byteLength);
        
        int separationIndex = -1;
        
        for(int i = 0; i < byteLength; i++) {
            if([[NSCharacterSet decimalDigitCharacterSet] characterIsMember: buffer[i]] ||
               buffer[i] == ',' ||
               buffer[i] == '.') {
                separationIndex = i;
                break;
            }
        }

        FormulaComponent *component = [[FormulaComponent alloc] init];
        
        if(separationIndex != -1) {
            NSString *name = [formulaDetail substringToIndex:separationIndex];
            NSString *quantity = [formulaDetail substringFromIndex:separationIndex];
            
            
            component.name = name;
            component.quantity = quantity;
        } else {
            component.name = formulaDetail;
            component.quantity = @"-";
        }
        
        [self.formulaComponents addObject:component];
    }
}

@end
