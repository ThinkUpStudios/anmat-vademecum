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
#import "MedicinesFilter.h"

@interface SearchViewController()

@property (weak, nonatomic) IBOutlet UIButton *btnShowGenericNameHelp;
@property (weak, nonatomic) IBOutlet UISearchBar *txtGenericName;
@property (weak, nonatomic) IBOutlet UISearchBar *txtComercialName;
@property (weak, nonatomic) IBOutlet UISearchBar *txtLaboratory;
@property (weak, nonatomic) IBOutlet UISearchBar *txtForm;
@property (weak, nonatomic) IBOutlet UITableView *tblResults;
- (IBAction)showGenericNameHelp:(id)sender;
- (IBAction)searchResults:(id)sender;

@end

@implementation SearchViewController {
    MedicineService *medicineService;
    NSMutableArray *searchResults;
    NSString *searchMode;
}

-(void) viewDidLoad {
    [super viewDidLoad];
    
    self.txtGenericName.delegate = self;
    self.txtComercialName.delegate = self;
    self.txtLaboratory.delegate = self;
    self.txtForm.delegate = self;
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
    } else if ([searchMode isEqualToString:@"laboratory"]) {
        self.txtLaboratory.text = selected;
        [self.txtLaboratory resignFirstResponder];
    } else {
        self.txtForm.text = selected;
        [self.txtForm resignFirstResponder];
    }
    
    self.tblResults.hidden = YES;
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([[segue identifier] isEqualToString:@"ShowResults"]) {
        SearchResultsViewController *results = segue.destinationViewController;
        MedicinesFilter *filter = [[MedicinesFilter alloc] init];
        
        filter.genericName = self.txtGenericName.text;
        filter.comercialName = self.txtComercialName.text;
        filter.laboratory = self.txtLaboratory.text;
        results.searchFilter = filter;
    }
}

- (IBAction)showGenericNameHelp:(id)sender {
    NSString *header=@"En la búsqueda de medicamentos por nombre genérico, puede buscar por más de un criterio aplicando las siguientes condiciones lógicas:";
    NSString *and= @"#: Utilizando este caracter como separador entre cada criterio, la búsqueda equivale a un 'Y' lógico. Ejemplo: acido # ibu # cafeina equivale a acido Y ibu Y cafeina.";
    NSString *or = @"?: Utilizando este caracter como separador entre cada criterio, la búsqueda equivale a un 'O' lógico. Ejemplo: ibu ? cafeina equivale a ibu O cafeina.";
    
    
    NSString *helpMessage=[NSString stringWithFormat:@"%@\n\n%@\n%@", header, and, or];
    
    UIAlertController *helpSheet = [UIAlertController alertControllerWithTitle:@"Búsqueda por Nombre Genérico" message:helpMessage preferredStyle:UIAlertControllerStyleActionSheet];
    
    helpSheet.popoverPresentationController.sourceView = self.btnShowGenericNameHelp;
    helpSheet.popoverPresentationController.sourceRect = self.btnShowGenericNameHelp.bounds;
    
    UIAlertAction *okAction = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }];
    
    [helpSheet addAction:okAction];
    
    [self
     presentViewController:helpSheet animated:YES completion:nil];
}

-(void)searchResults:(id)sender {
    if(self.txtGenericName.text.length == 0 && self.txtComercialName.text.length == 0 && self.txtLaboratory.text.length == 0 && self.txtForm.text.length == 0) {
        UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"Campos requeridos" message:@"Al menos uno de los tres campos debe contener un valor" preferredStyle:UIAlertControllerStyleAlert];
        UIAlertAction *okAction = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:nil];
        
        [alert addAction:okAction];
        [self presentViewController:alert animated:YES completion:nil];
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
        [self loadSuggested:[medicineService getGenericNames:text ]];
    } else if([searchBar isEqual:self.txtComercialName]) {
        searchMode = @"comercial";
        [self loadSuggested:[medicineService getComercialNames:text ]];
    } else if([searchBar isEqual:self.txtLaboratory]) {
        searchMode = @"laboratory";
        [self loadSuggested:[medicineService getLaboratories:text ]];
    } else {
        searchMode = @"form";
        [self loadSuggested:[medicineService getForms:text ]];
    }
    
    [self.tblResults reloadData];
    self.tblResults.hidden = NO;
}

-(void) loadSuggested:(NSArray *)values {
    for (NSString *value in values) {
        [searchResults addObject:value];
    }
}

@end
