//
//  ActiveComponentsViewController.m
//  Anmat Vademecum
//
//  Created by mag on 3/22/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "ActiveComponentsViewController.h"
#import "MedicineService.h"
#import "ActiveComponentViewController.h"

@interface ActiveComponentsViewController ()

@property (weak, nonatomic) IBOutlet UISearchBar *txtActiveComponent;
@property (weak, nonatomic) IBOutlet UITableView *tblResults;

@end

@implementation ActiveComponentsViewController

NSArray *testComponents;
NSMutableArray *searchResults;

- (void)viewDidLoad {
    [super viewDidLoad];
   
    self.txtActiveComponent.delegate = self;
    self.tblResults.delegate = self;
    self.tblResults.dataSource = self;
    self.tblResults.hidden = YES;
    
    testComponents = [[NSArray alloc] init];
    searchResults = [[NSMutableArray alloc] init];
    
    [self loadTestData];
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
        ActiveComponentViewController *activeComponent = segue.destinationViewController;
        NSIndexPath *selectedIndex = [self.tblResults indexPathForCell:sender];
        NSString *selectedComponent = [searchResults objectAtIndex:selectedIndex.item];
        
        activeComponent.componentName = selectedComponent;
    }
}

-(void) search:(UISearchBar *)searchBar text:(NSString *)text {
    [searchResults removeAllObjects];
    
    if(text.length == 0) {
        self.tblResults.hidden = YES;
        
        return;
    }
    
    [self loadSuggested:text values:testComponents];
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

- (void)loadTestData {
    NSString *components = @"CARBONATO DE CALCIO + FOSFATO DE MAGNESIO % + CARBOXIMETILCISTEINA + SULFATO FERROSO + CLORURO DE MAGNESIO + DEXTROSA + CIANOCOBALAMINA + ACIDO FOLICO + VITAMINA K1 + IODURO DE POTASIO + CLORURO DE POTASIO + CITRATO DE POTASIO + RETINOL + BETACAROTENO + RIBOFLAVINA + NIACINAMIDA + BIOTINA + PANTOTENATO DE CALCIO + ACIDO ASCORBICO + VITAMINA D3 + TOCOFEROL + L-CARNITINA + FOSFATO TRICALCICO + SULFATO DE MANGANESO + CARBOXIMETILCELULOSA SODICA + SULFATO DE COBRE + ZINC SULFATO + CLORURO DE CROMO + AGUA + ACIDO CITRICO + LECITINA + MOLIBDATO DE SODIO + CASEINATO DE CALCIO + TAURINA + CLORURO DE COLINA + CASEINATO DE SODIO + ACEITE DE MAIZ + ACEITE DE CANOLA + AISLADO DE PROTEINA DE SOJA + TIAMINA CLORHIDRATO + PIRIDOXINA CLORHIDRATO + FOSFATO DIBASICO DE POTASIO + SELENATO DE SODIO + DEXTRINAS Y MALTOSA + ACEITE DE COCO + HIDROXIDO DE POTASIO + CITRATO DE SODIO + DL ALFATOCOFEROL ACETATO + ACEITE DE GIRASOL + FRUCTOOLIGASACARIDO + MALTODEXTRINA DE18 + FIBRA DE SOJA FIBRIM 300";
    
    testComponents = [components componentsSeparatedByString:@"+"];
}

@end
