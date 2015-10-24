//
//  ActiveComponentViewController.m
//  Anmat Vademecum
//
//  Created by mag on 3/22/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "ActiveComponentInfoViewController.h"
#import "ActiveComponentDetailViewController.h"
#import "SearchResultsViewController.h"
#import "MedicineService.h"
#import "MedicinesFilter.h"
#import "MenuViewController.h"

@interface ActiveComponentInfoViewController ()

@end

@implementation ActiveComponentInfoViewController {
    NSMutableArray *activeComponentTitles;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    UIBarButtonItem *btnSameComponent = [[UIBarButtonItem alloc] init];
    UIImage *imgSameComponent = [UIImage imageNamed:@"SameComponent"];
    
    [btnSameComponent setImage:imgSameComponent];
    [btnSameComponent setTarget:self];
    [btnSameComponent setAction:@selector(showMedicinesWithSameComponent:)];
    
    UIBarButtonItem *btnHome = [[UIBarButtonItem alloc] init];
    UIImage *imgHome = [UIImage imageNamed:@"Home"];
    
    [btnHome setImage:imgHome];
    [btnHome setTarget:self];
    [btnHome setAction:@selector(showHome:)];
    
    self.navigationItem.rightBarButtonItems = [NSArray arrayWithObjects:btnSameComponent, btnHome, nil];
    
    if(self.component) {
        activeComponentTitles = [[NSMutableArray alloc] init];
        
        if(![self.component.clasification isEqualToString:@""]) {
            [activeComponentTitles addObject:@"Clasificación Terapéutica"];
        }
        
        if(![self.component.description isEqualToString:@""] ||
           ![self.component.action isEqualToString:@""] ||
           ![self.component.cinetic isEqualToString:@""]) {
            [activeComponentTitles addObject:@"Farmacología"];
        }
        
        if(![self.component.indication isEqualToString:@""] ||
           ![self.component.posology isEqualToString:@""]) {
            [activeComponentTitles addObject:@"Indicaciones y Posología"];
        }
        
        if(![self.component.contraindication isEqualToString:@""]) {
            [activeComponentTitles addObject:@"Contraindicaciones"];
        }
        
        if(![self.component.interaction isEqualToString:@""]) {
            [activeComponentTitles addObject:@"Interacciones"];
        }
        
        if(![self.component.reaction isEqualToString:@""]) {
            [activeComponentTitles addObject:@"Reacciones Adversas"];
        }
        
        if(![self.component.reference isEqualToString:@""]) {
            [activeComponentTitles addObject:@"Referencias"];
        }
        
        if(![self.component.additionalInfo isEqualToString:@""]) {
            [activeComponentTitles addObject:@"Información Adicional"];
        }
        
        if(![self.component.bibliography isEqualToString:@""]) {
            [activeComponentTitles addObject:@"Bibliografía"];
        }
    }
    
    self.title = self.name;
    self.tableView.sectionHeaderHeight = 40.0;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView {
    if(self.component) {
        return 1;
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

-(NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    return @"Información";
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [activeComponentTitles count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView
                             dequeueReusableCellWithIdentifier:@"ActiveComponentInfoCell"
                             forIndexPath:indexPath];
    
    NSString *result = [activeComponentTitles objectAtIndex:indexPath.row];
    
    cell.textLabel.text = result;
    
    return cell;
}

-(void)tableView:(UITableView *)tableView accessoryButtonTappedForRowWithIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *selectedCell = [tableView cellForRowAtIndexPath:indexPath];
    
    [self performSegueWithIdentifier:@"ShowActiveComponentDetails" sender:selectedCell];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([[segue identifier] isEqualToString:@"ShowMedicinesWithSameComponent"]) {
        SearchResultsViewController *results = segue.destinationViewController;
        MedicinesFilter *filter = [[MedicinesFilter alloc] init];
        
        filter.activeComponent = self.component.ifa;
        results.searchFilter = filter;
    } else if([[segue identifier] isEqualToString:@"ShowActiveComponentDetails"]) {
        ActiveComponentDetailViewController *detail = segue.destinationViewController;
        
        NSIndexPath *selectedIndex = [self.tableView indexPathForCell:sender];
        NSString *title = [activeComponentTitles objectAtIndex:selectedIndex.item];
        
        detail.ifa = self.component.ifa;
        detail.section = title;
        
        NSMutableArray *subtitles = [[NSMutableArray alloc] init];
        NSMutableArray *details = [[NSMutableArray alloc] init];
        
        if([title isEqualToString:@"Clasificación Terapéutica"]) {
            [subtitles addObject:@"Clasificación Terapéutica"];
            [details addObject:self.component.clasification];
        }
        
        if([title isEqualToString:@"Farmacología"]) {
            [subtitles addObject:@"Descripción"];
            [subtitles addObject:@"Mecanismos de acción"];
            [subtitles addObject:@"Farmacocinética"];
            [details addObject:self.component.pharmacology];
            [details addObject:self.component.action];
            [details addObject:self.component.cinetic];
        }
        
        if([title isEqualToString:@"Indicaciones y Posología"]) {
            [subtitles addObject:@"Indicaciones"];
            [subtitles addObject:@"Posología"];
            [details addObject:self.component.indication];
            [details addObject:self.component.posology];
        }
        
        if([title isEqualToString:@"Contraindicaciones"]) {
            [subtitles addObject:@"Contraindicaciones"];
            [details addObject:self.component.contraindication];
        }
        
        if([title isEqualToString:@"Interacciones"]) {
            [subtitles addObject:@"Interacciones"];
            [details addObject:self.component.interaction];
        }
        
        if([title isEqualToString:@"Reacciones Adversas"]) {
            [subtitles addObject:@"Reacciones Adversas"];
            [details addObject:self.component.reaction];
        }
        
        if([title isEqualToString:@"Referencias"]) {
            [subtitles addObject:@"Referencias"];
            [details addObject:self.component.reference];
        }
        
        if([title isEqualToString:@"Información Adicional"]) {
            [subtitles addObject:@"Información Adicional"];
            [details addObject:self.component.additionalInfo];
        }
        
        if([title isEqualToString:@"Bibliografía"]) {
            [subtitles addObject:@"Bibliografía"];
            [details addObject:self.component.bibliography];
        }
        
        detail.subtitles = subtitles;
        detail.details = details;
    }
}

-(void) showMedicinesWithSameComponent:(id) sender {
    SearchResultsViewController *searchResults = (SearchResultsViewController *) [self.storyboard instantiateViewControllerWithIdentifier:@"SearchResultsViewController"];
    MedicinesFilter *filter = [[MedicinesFilter alloc] init];
    
    filter.activeComponent = self.name;
    searchResults.searchFilter = filter;
    searchResults.title = @"Mismo Principio Activo";
    
    [self.navigationController pushViewController:searchResults animated:YES];
}

-(void) showHome:(id) sender {
    MenuViewController *about = (MenuViewController *) [self.storyboard instantiateViewControllerWithIdentifier:@"MenuViewController"];
    
    [self.navigationController pushViewController:about animated:YES];
}

@end
