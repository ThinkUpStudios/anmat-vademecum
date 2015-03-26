//
//  SearchViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "SearchViewController.h"
#import "MainSearchSectionViewController.h"
#import "SearchResultsViewController.h"
#import "MedicineService.h"
#import "Medicine.h"

@interface SearchViewController()

@property (weak, nonatomic) IBOutlet UISearchBar *txtGenericName;
@property (weak, nonatomic) IBOutlet UISearchBar *txtComercialName;
@property (weak, nonatomic) IBOutlet UISearchBar *txtLaboratory;
@property (weak, nonatomic) IBOutlet UITableView *tblResults;
- (IBAction)searchResults:(id)sender;

@end

@implementation SearchViewController

MedicineService *medicineService;
NSMutableArray *searchResults;
NSString *searchMode;

-(void) viewDidLoad {
    [super viewDidLoad];
    
    self.txtGenericName.delegate = self;
    self.txtComercialName.delegate = self;
    self.txtLaboratory.delegate = self;
    self.tblResults.delegate = self;
    self.tblResults.dataSource = self;
    self.tblResults.hidden = YES;
    
    medicineService = [[MedicineService alloc] init];
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
    
    if ([searchMode isEqualToString:@"generic"]) {
         self.txtGenericName.text = selected;
        [self.txtGenericName resignFirstResponder];
    } else if ([searchMode isEqualToString:@"comercial"]) {
        self.txtComercialName.text = selected;
        [self.txtComercialName resignFirstResponder];
    } else {
        self.txtLaboratory.text = selected;
        [self.txtLaboratory resignFirstResponder];
    }
    
    self.tblResults.hidden = YES;
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([[segue identifier] isEqualToString:@"ShowResults"]) {
        SearchResultsViewController *results = segue.destinationViewController;
        
        results.medicines = [medicineService getMedicines:self.txtGenericName.text comercialName:self.txtComercialName.text laboratory:self.txtLaboratory.text];
    }
}

-(void)searchResults:(id)sender {
    if(self.txtGenericName.text.length == 0 && self.txtComercialName.text.length == 0 && self.txtLaboratory.text.length == 0) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Campos requeridos" message:@"Al menos uno de los tres campos debe contener un valor" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
        
        [alert show];
    } else {
        [self performSegueWithIdentifier:@"ShowResults" sender:self];
    }
}

-(void) search:(UISearchBar *)searchBar text:(NSString *)text {
    [searchResults removeAllObjects];
    
    if(text.length == 0) {
        self.tblResults.hidden = YES;
        
        return;
    }
    
    if([searchBar isEqual:self.txtGenericName]) {
        searchMode = @"generic";
        [self loadSuggested:text values:[medicineService getGenericNames:text ]];
    } else if([searchBar isEqual:self.txtComercialName]) {
        searchMode = @"comercial";
        [self loadSuggested:text values:[medicineService getComercialNames:text ]];
    } else {
        searchMode = @"laboratory";
        [self loadSuggested:text values:[medicineService getLaboratories:text ]];
    }
    
    [self.tblResults reloadData];
    self.tblResults.hidden = NO;
}

-(void) loadSuggested:(NSString *)searchText values:(NSArray *)values {
    for (NSString *value in values) {
        if([[value lowercaseString] containsString:[searchText lowercaseString]]) {
            [searchResults addObject:value];
        }
    }
}

@end
