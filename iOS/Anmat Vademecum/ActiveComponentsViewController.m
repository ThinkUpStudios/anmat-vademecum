//
//  ActiveComponentsViewController.m
//  Anmat Vademecum
//
//  Created by mag on 3/22/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "ActiveComponentsViewController.h"
#import "MedicineService.h"
#import "ActiveComponentInfoViewController.h"
#import "ActiveComponentService.h"

@interface ActiveComponentsViewController ()

@property (weak, nonatomic) IBOutlet UISearchBar *txtActiveComponent;
@property (weak, nonatomic) IBOutlet UITableView *tblResults;

@end

@implementation ActiveComponentsViewController {
    ActiveComponentService *componentsService;
    NSMutableArray *searchResults;
}

- (void)viewDidLoad {
    [super viewDidLoad];
   
    self.txtActiveComponent.delegate = self;
    self.tblResults.delegate = self;
    self.tblResults.dataSource = self;
    self.tblResults.hidden = YES;
    
    componentsService = [[ActiveComponentService alloc] init];
    searchResults = [[NSMutableArray alloc] init];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

-(void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText {
    [self search:searchBar text:searchText];
}

-(void)searchBarCancelButtonClicked:(UISearchBar *)searchBar {
    searchBar.text = @"";
    self.tblResults.hidden = YES;
    [searchBar resignFirstResponder];
}

-(BOOL)searchBarShouldBeginEditing:(UISearchBar *)searchBar {
    [searchBar setShowsCancelButton:YES];
    return YES;
}

-(BOOL)searchBarShouldEndEditing:(UISearchBar *)searchBar {
    [searchBar setShowsCancelButton:NO];
    return YES;
}

-(void)searchBarTextDidEndEditing:(UISearchBar *)searchBar {
    [searchBar setShowsCancelButton:NO];
    [searchBar resignFirstResponder];
}

-(void)searchBarSearchButtonClicked:(UISearchBar *)searchBar {
    [self search:searchBar text:searchBar.text];
    [searchBar resignFirstResponder];
}

- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

-(NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    return @"Sugerencias";
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [searchResults count];
}

- (UITableViewCell *) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView
                             dequeueReusableCellWithIdentifier:@"SearchResultCell"
                             forIndexPath:indexPath];
    
    NSString *result = [searchResults objectAtIndex:indexPath.row];
    
    cell.textLabel.text = result;
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *selected = [searchResults objectAtIndex:indexPath.item];
    
    self.txtActiveComponent.text = selected;
    [self.txtActiveComponent resignFirstResponder];
    self.tblResults.hidden = YES;
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([[segue identifier] isEqualToString:@"ShowActiveComponent"]) {
        ActiveComponentInfoViewController *activeComponent = segue.destinationViewController;
        NSIndexPath *selectedIndex = [self.tblResults indexPathForCell:sender];
        NSString *selectedComponentName = [searchResults objectAtIndex:selectedIndex.item];
        ActiveComponent *component = [componentsService getByName:selectedComponentName];
        
        activeComponent.name = selectedComponentName;
        activeComponent.component = component;
    }
}

-(void) search:(UISearchBar *)searchBar text:(NSString *)text {
    [searchResults removeAllObjects];
    
    if(text.length == 0) {
        self.tblResults.hidden = YES;
        
        return;
    }
    
    [self loadSuggested:[componentsService getAll:text ]];
    [self.tblResults reloadData];
    self.tblResults.hidden = NO;
}

-(void) loadSuggested:(NSArray *)values {
    for (NSString *value in values) {
        [searchResults addObject:value];
    }
}

@end
