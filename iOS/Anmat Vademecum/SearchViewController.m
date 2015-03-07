//
//  SearchViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "SearchViewController.h"
#import "MainSearchSectionViewController.h"

@interface SearchViewController()

@property (weak, nonatomic) IBOutlet UISearchBar *txtGenericName;
@property (weak, nonatomic) IBOutlet UISearchBar *txtComercialName;
@property (weak, nonatomic) IBOutlet UISearchBar *txtLaboratory;
@property (weak, nonatomic) IBOutlet UITableView *tblResults;

@end

@implementation SearchViewController

NSMutableArray *searchResults;
NSArray *genericNames;
NSArray *comercialNames;
NSArray *laboratories;
NSString *searchMode;

-(void) viewDidLoad {
    [super viewDidLoad];
    
    self.txtGenericName.delegate = self;
    self.txtComercialName.delegate = self;
    self.txtLaboratory.delegate = self;
    self.tblResults.delegate = self;
    self.tblResults.dataSource = self;
    self.tblResults.hidden = YES;
    
    searchResults = [[NSMutableArray alloc] init];
    [self loadTestData];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

-(void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText {
    [searchResults removeAllObjects];
    
    if(searchText.length == 0) {
        self.tblResults.hidden = YES;
        
        return;
    }
    
    if(searchText.length < 3) {
        [self.tblResults reloadData];
        
        return;
    }
    
    if([searchBar isEqual:self.txtGenericName]) {
        searchMode = @"generic";
        [self loadRecommended:searchText values:genericNames];
    } else if([searchBar isEqual:self.txtComercialName]) {
        searchMode = @"comercial";
        [self loadRecommended:searchText values:comercialNames];
    } else {
        searchMode = @"laboratory";
        [self loadRecommended:searchText values:laboratories];
    }
    
    [self.tblResults reloadData];
    self.tblResults.hidden = NO;
}

-(void)searchBarCancelButtonClicked:(UISearchBar *)searchBar {
    [searchBar resignFirstResponder];
    self.tblResults.hidden = YES;
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
    } else if ([searchMode isEqualToString:@"comercial"]) {
        self.txtComercialName.text = selected;
    } else {
        self.txtLaboratory.text = selected;
    }
    
    self.tblResults.hidden = YES;
}

-(void) loadRecommended:(NSString *)searchText values:(NSArray *)values {
    for (NSString *value in values) {
        if([[value lowercaseString] containsString:[searchText lowercaseString]]) {
            [searchResults addObject:value];
        }
    }
}

-(void)loadTestData {
    genericNames = @[@"+ FACTOR ANTIHEMOFILICO HUMANO 250 UI / FRASCO AMPOLLA", @"BENZOCAINA 10 MG + EXTRACTO SECO DE HEDERA HELIX 65 MG", @"ITRACONAZOL 200 MG / CAP", @"FOSFATO MONOSODICO MONOHIDRATO ,22 G / 100 ML + DEXTROSA MONOHIDRATADA 3,19 G / 100 ML + ADENINA ,027 G / 100 ML + ACIDO CITRICO ANHIDRO ,3 G / 100 ML + CITRATO DE SODIO DIHIDRATO 2,63 G / 100 ML", @"RISPERIDONA 1 MG", @"KLEBSIELLA PNEUMONIAE 1 MILLONES / ML + ESCHERICHIA COLI ,5 MILLONES / ML + ESTREPTOCOCOS 11 MILLONES / ML + BAC.PROTEUS VULGARIS ,5 MILLONES / ML + PSEUDOMONAS AERUGINOSA 1 MILLONES / ML + MICROCOCCUS FLAVUS 4 MILLONES / ML + MICROCOCCUS CANDIDUS 3 MILLONES / ML + MICROCOCCUS CONGLOMERATUS 3 MILLONES / ML + MICROCOCCUS VARIANS 4 MILLONES / ML + STAPHYLOCOCCUS PYOGENES 12 MILLONES / ML + STAPHYLOCOCCUS EPIDERMIS 3 MILLONES / ML + SARCINA LUTEA 1 MILLONES / ML + AEROBACTER AEROGENES ,5 MILLONES / ML + PSEUDOMONAS FLUORESCENS ,5 MILLONES / ML + CORYNEBACTERIUM PSEUDODIPHTER ,5 MILLONES / ML + CORYNEBACTERIUM XEROSE ,5 MILLONES / ML + ASPERGILLUS ,5 MILLONES / ML + PENICILLIUM ,5 MILLONES / ML + EPIDERMOPHYTUS 1,25 MILLONES / ML + TRICHOPHYTUS 1,25 MILLONES / ML + MONILIA 1,25 MILLONES / ML", @"ERITROPOYETINA HUMANA RECOMBINANTE 2000 UI / 2 ML +"];
    comercialNames = @[@"KOATE DVI", @"CEDRIC POCKET", @"ITRAC 200", @"BOLSAS PARA SANGRE CFDA-1", @"RESTELEA", @"SUMMAVAC P", @"LOHP 500", @"JEVITY PLUS", @"ASPIRINA FABRA 500", @"HYPERCRIT"];
    laboratories = @[@"TUTEUR S A C I F I A", @"LABORATORIO ELEA SACIFYA", @"LABORATORIO PABLO CASSARA S R L", @"P.L. RIVERO Y COMPAÑIA SOCIEDAD ANONIMA", @"HLB PHARMA GROUP S.A.", @"ABBOTT LABORATORIES ARGENTINA S.A.", @"LABORATORIOS FABRA S.A.", @"BIOSIDUS SOCIEDAD ANONIMA"];
}

@end
