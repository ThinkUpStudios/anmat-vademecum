//
//  SearchViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "SearchViewController.h"
#import "MainSearchSectionViewController.h"
#import "Medicine.h"
#import "SearchResultsViewController.h"

@interface SearchViewController()

@property (weak, nonatomic) IBOutlet UISearchBar *txtGenericName;
@property (weak, nonatomic) IBOutlet UISearchBar *txtComercialName;
@property (weak, nonatomic) IBOutlet UISearchBar *txtLaboratory;
@property (weak, nonatomic) IBOutlet UITableView *tblResults;

@end

@implementation SearchViewController

NSMutableArray *medicines;
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
    
    medicines = [[NSMutableArray alloc] init];
    searchResults = [[NSMutableArray alloc] init];
    [self loadMedicines];
    [self loadSearchData];
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
        
        results.medicines = [self getMedicines:self.txtGenericName.text comercialName:self.txtComercialName.text laboratory:self.txtLaboratory.text];
    }
}

- (void) loadMedicines {
    Medicine *item1 = [[Medicine alloc] init];
    
    item1.troquel = @"515460";
    item1.gtin = @"07795327063274";
    item1.requestCondition = @"BAJO RECETA";
    item1.trazability = @"Fuera de disposición";
    item1.presentation = @"1 FRASCO AMPOLLA por 554,03 MG";
    item1.country = @"GRAN BRETAÑA (REINO UNIDO DE GRAN BRETAÑA E IRLANDA DEL NORTE)";
    item1.genericName = @"+ FACTOR ANTIHEMOFILICO HUMANO 250 UI / FRASCO AMPOLLA";
    item1.laboratory = @"TUTEUR S A C I F I A";
    item1.comercialName = @"KOATE DVI";
    item1.certificate = @"38803";
    item1.form = @"POLVO LIOFILIZADO PARA INYECTABLE";
    item1.price = @"$ 4687,29";
    [medicines addObject:item1];
    
    Medicine *item2 = [[Medicine alloc] init];
    
    item2.troquel = @"515460";
    item2.gtin = @"07795327063274";
    item2.requestCondition = @"BAJO RECETA";
    item2.trazability = @"Fuera de disposición";
    item2.presentation = @"1 FRASCO AMPOLLA por 554,03 MG";
    item2.country = @"GRAN BRETAÑA (REINO UNIDO DE GRAN BRETAÑA E IRLANDA DEL NORTE)";
    item2.genericName = @"BENZOCAINA 10 MG + EXTRACTO SECO DE HEDERA HELIX 65 MG";
    item2.laboratory = @"LABORATORIO ELEA SACIFYA";
    item2.comercialName = @"CEDRIC POCKET";
    item2.certificate = @"54471";
    item2.form = @"CARAMELO";
    item2.price = @"$ 30";
    [medicines addObject:item2];
    
    Medicine *item3 = [[Medicine alloc] init];
    
    item3.troquel = @"515460";
    item3.gtin = @"07795327063274";
    item3.requestCondition = @"BAJO RECETA";
    item3.trazability = @"Fuera de disposición";
    item3.presentation = @"1 FRASCO AMPOLLA por 554,03 MG";
    item3.country = @"GRAN BRETAÑA (REINO UNIDO DE GRAN BRETAÑA E IRLANDA DEL NORTE)";
    item3.genericName = @"ITRACONAZOL 200 MG / CAP";
    item3.laboratory = @"LABORATORIO PABLO CASSARA S R L";
    item3.comercialName = @"ITRAC 200";
    item3.certificate = @"47246";
    item3.form = @"CAPSULA DURA";
    item3.price = @"$ 276,23";
    [medicines addObject:item3];
    
    Medicine *item4 = [[Medicine alloc] init];
    
    item4.troquel = @"515460";
    item4.gtin = @"07795327063274";
    item4.requestCondition = @"BAJO RECETA";
    item4.trazability = @"Fuera de disposición";
    item4.presentation = @"1 FRASCO AMPOLLA por 554,03 MG";
    item4.country = @"GRAN BRETAÑA (REINO UNIDO DE GRAN BRETAÑA E IRLANDA DEL NORTE)";
    item4.genericName = @"FOSFATO MONOSODICO MONOHIDRATO ,22 G / 100 ML + DEXTROSA MONOHIDRATADA 3,19 G / 100 ML + ADENINA ,027 G / 100 ML + ACIDO CITRICO ANHIDRO ,3 G / 100 ML + CITRATO DE SODIO DIHIDRATO 2,63 G / 100 ML";
    item4.laboratory = @"P.L. RIVERO Y COMPAÑIA SOCIEDAD ANONIMA";
    item4.comercialName = @"BOLSAS PARA SANGRE CFDA-1";
    item4.certificate = @"43183";
    item4.form = @"SOLUCION ESTERIL";
    item4.price = @"$ 418,27";
    [medicines addObject:item4];
    
    Medicine *item5 = [[Medicine alloc] init];
    
    item5.troquel = @"515460";
    item5.gtin = @"07795327063274";
    item5.requestCondition = @"BAJO RECETA";
    item5.trazability = @"Fuera de disposición";
    item5.presentation = @"1 FRASCO AMPOLLA por 554,03 MG";
    item5.country = @"GRAN BRETAÑA (REINO UNIDO DE GRAN BRETAÑA E IRLANDA DEL NORTE)";
    item5.genericName = @"RISPERIDONA 1 MG";
    item5.laboratory = @"LABORATORIO ELEA SACIFYA";
    item5.comercialName = @"RESTELEA";
    item5.certificate = @"43332";
    item5.form = @"COMPRIMIDO";
    item5.price = @"$ 193,92";
    [medicines addObject:item5];
    
    Medicine *item6 = [[Medicine alloc] init];
    
    item6.troquel = @"515460";
    item6.gtin = @"07795327063274";
    item6.requestCondition = @"BAJO RECETA";
    item6.trazability = @"Fuera de disposición";
    item6.presentation = @"1 FRASCO AMPOLLA por 554,03 MG";
    item6.country = @"GRAN BRETAÑA (REINO UNIDO DE GRAN BRETAÑA E IRLANDA DEL NORTE)";
    item6.genericName = @"KLEBSIELLA PNEUMONIAE 1 MILLONES / ML + ESCHERICHIA COLI ,5 MILLONES / ML + ESTREPTOCOCOS 11 MILLONES / ML + BAC.PROTEUS VULGARIS ,5 MILLONES / ML + PSEUDOMONAS AERUGINOSA 1 MILLONES / ML + MICROCOCCUS FLAVUS 4 MILLONES / ML + MICROCOCCUS CANDIDUS 3 MILLONES / ML + MICROCOCCUS CONGLOMERATUS 3 MILLONES / ML + MICROCOCCUS VARIANS 4 MILLONES / ML + STAPHYLOCOCCUS PYOGENES 12 MILLONES / ML + STAPHYLOCOCCUS EPIDERMIS 3 MILLONES / ML + SARCINA LUTEA 1 MILLONES / ML + AEROBACTER AEROGENES ,5 MILLONES / ML + PSEUDOMONAS FLUORESCENS ,5 MILLONES / ML + CORYNEBACTERIUM PSEUDODIPHTER ,5 MILLONES / ML + CORYNEBACTERIUM XEROSE ,5 MILLONES / ML + ASPERGILLUS ,5 MILLONES / ML + PENICILLIUM ,5 MILLONES / ML + EPIDERMOPHYTUS 1,25 MILLONES / ML + TRICHOPHYTUS 1,25 MILLONES / ML + MONILIA 1,25 MILLONES / ML";
    item6.laboratory = @"LABORATORIO PABLO CASSARA S R L";
    item6.comercialName = @"SUMMAVAC P";
    item6.certificate = @"29553";
    item6.form = @"INYECTABLE PARA PERFUSION";
    item6.price = @"$ 84,18";
    [medicines addObject:item6];
    
    Medicine *item7 = [[Medicine alloc] init];
    
    item7.troquel = @"515460";
    item7.gtin = @"07795327063274";
    item7.requestCondition = @"BAJO RECETA";
    item7.trazability = @"Fuera de disposición";
    item7.presentation = @"1 FRASCO AMPOLLA por 554,03 MG";
    item7.country = @"GRAN BRETAÑA (REINO UNIDO DE GRAN BRETAÑA E IRLANDA DEL NORTE)";
    item7.genericName = @"L-CISTINA 500 MG + PIRIDOXINA CLORHIDRATO 100 MG";
    item7.laboratory = @"HLB PHARMA GROUP S.A.";
    item7.comercialName = @"LOHP 500";
    item7.certificate = @"49658";
    item7.form = @"COMPRIMIDO RECUBIERTO";
    item7.price = @"$ 218,43";
    [medicines addObject:item7];
    
    Medicine *item8 = [[Medicine alloc] init];
    
    item8.troquel = @"515460";
    item8.gtin = @"07795327063274";
    item8.requestCondition = @"BAJO RECETA";
    item8.trazability = @"Fuera de disposición";
    item8.presentation = @"1 FRASCO AMPOLLA por 554,03 MG";
    item8.country = @"GRAN BRETAÑA (REINO UNIDO DE GRAN BRETAÑA E IRLANDA DEL NORTE)";
    item8.genericName = @"CARBONATO DE CALCIO 90,4 MG % + FOSFATO DE MAGNESIO 152 MG % + CARBOXIMETILCISTEINA ,109 MG % + SULFATO FERROSO 5,1 MG % + CLORURO DE MAGNESIO  CS + DEXTROSA  CS + CIANOCOBALAMINA ,4 MCG % + ACIDO FOLICO 40,9 MCG % + VITAMINA K1 9,8 MCG % + IODURO DE POTASIO 21,6 MCG % + CLORURO DE POTASIO 249 MG % + CITRATO DE POTASIO ,141 MG % + RETINOL 271 MCG % + BETACAROTENO 249 MCG % + RIBOFLAVINA 325 MCG % + NIACINAMIDA 1,6 MG % + BIOTINA 6,4 MCG % + PANTOTENATO DE CALCIO 1,3 MG % + ACIDO ASCORBICO 21,7 MG % + VITAMINA D3 53,1 MCG % + TOCOFEROL 5,572 MG % + L-CARNITINA 10,1 MG % + FOSFATO TRICALCICO 27,1 MG % + SULFATO DE MANGANESO 1,2 MG % + CARBOXIMETILCELULOSA SODICA 107 MG % + SULFATO DE COBRE 585 MCG % + ZINC SULFATO 3,4 MG % + CLORURO DE CROMO 17,4 MCG % + AGUA 80,7 G % + ACIDO CITRICO 3 G % + LECITINA ,197 MG % + MOLIBDATO DE SODIO 27,2 MCG % + CASEINATO DE CALCIO 1,5 G % + TAURINA 16,2 MG % + CLORURO DE COLINA 68,2 MG % + CASEINATO DE SODIO 3,3 G % + ACEITE DE MAIZ ,76 G % + ACEITE DE CANOLA 1,1 G % + AISLADO DE PROTEINA DE SOJA 1,25 G % + TIAMINA CLORHIDRATO 336 MCG % + PIRIDOXINA CLORHIDRATO 401 MCG % + FOSFATO DIBASICO DE POTASIO ,033 MG % + SELENATO DE SODIO 17,9 MCG % + DEXTRINAS Y MALTOSA 12,943 MG % + ACEITE DE COCO 1,066 MG % + HIDROXIDO DE POTASIO 8,4 MG % + CITRATO DE SODIO 217 MG % + DL ALFATOCOFEROL ACETATO 2,5 MG % + ACEITE DE GIRASOL 1,1 G % + FRUCTOOLIGASACARIDO 1,1 MG % + MALTODEXTRINA DE18 21,1 G % + FIBRA DE SOJA FIBRIM 300 726 MG %";
    item8.laboratory = @"ABBOTT LABORATORIES ARGENTINA S.A.";
    item8.comercialName = @"JEVITY PLUS";
    item8.certificate = @"48152";
    item8.form = @"SOLUCIÓN";
    item8.price = @"$ -";
    [medicines addObject:item8];
    
    Medicine *item9 = [[Medicine alloc] init];
    
    item9.troquel = @"515460";
    item9.gtin = @"07795327063274";
    item9.requestCondition = @"BAJO RECETA";
    item9.trazability = @"Fuera de disposición";
    item9.presentation = @"1 FRASCO AMPOLLA por 554,03 MG";
    item9.country = @"GRAN BRETAÑA (REINO UNIDO DE GRAN BRETAÑA E IRLANDA DEL NORTE)";
    item9.genericName = @"ASPIRINA 500 MG";
    item9.laboratory = @"LABORATORIOS FABRA S.A.";
    item9.comercialName = @"ASPIRINA FABRA 500";
    item9.certificate = @"44586";
    item9.form = @"COMPRIMIDO";
    item9.price = @"$ -";
    [medicines addObject:item9];
    
    Medicine *item10 = [[Medicine alloc] init];
    
    item10.troquel = @"515460";
    item10.gtin = @"07795327063274";
    item10.requestCondition = @"BAJO RECETA";
    item10.trazability = @"Fuera de disposición";
    item10.presentation = @"1 FRASCO AMPOLLA por 554,03 MG";
    item10.country = @"GRAN BRETAÑA (REINO UNIDO DE GRAN BRETAÑA E IRLANDA DEL NORTE)";
    item10.genericName = @"ERITROPOYETINA HUMANA RECOMBINANTE 2000 UI / 2 ML +";
    item10.laboratory = @"BIOSIDUS SOCIEDAD ANONIMA";
    item10.comercialName = @"HYPERCRIT";
    item10.certificate = @"41440";
    item10.form = @"POLVO LIOFILIZADO PARA INYECTABLE";
    item10.price = @"$ 99,44";
    [medicines addObject:item10];
}

-(void)loadSearchData {
    genericNames = @[@"+ FACTOR ANTIHEMOFILICO HUMANO 250 UI / FRASCO AMPOLLA", @"BENZOCAINA 10 MG + EXTRACTO SECO DE HEDERA HELIX 65 MG", @"ITRACONAZOL 200 MG / CAP", @"FOSFATO MONOSODICO MONOHIDRATO ,22 G / 100 ML + DEXTROSA MONOHIDRATADA 3,19 G / 100 ML + ADENINA ,027 G / 100 ML + ACIDO CITRICO ANHIDRO ,3 G / 100 ML + CITRATO DE SODIO DIHIDRATO 2,63 G / 100 ML", @"RISPERIDONA 1 MG", @"KLEBSIELLA PNEUMONIAE 1 MILLONES / ML + ESCHERICHIA COLI ,5 MILLONES / ML + ESTREPTOCOCOS 11 MILLONES / ML + BAC.PROTEUS VULGARIS ,5 MILLONES / ML + PSEUDOMONAS AERUGINOSA 1 MILLONES / ML + MICROCOCCUS FLAVUS 4 MILLONES / ML + MICROCOCCUS CANDIDUS 3 MILLONES / ML + MICROCOCCUS CONGLOMERATUS 3 MILLONES / ML + MICROCOCCUS VARIANS 4 MILLONES / ML + STAPHYLOCOCCUS PYOGENES 12 MILLONES / ML + STAPHYLOCOCCUS EPIDERMIS 3 MILLONES / ML + SARCINA LUTEA 1 MILLONES / ML + AEROBACTER AEROGENES ,5 MILLONES / ML + PSEUDOMONAS FLUORESCENS ,5 MILLONES / ML + CORYNEBACTERIUM PSEUDODIPHTER ,5 MILLONES / ML + CORYNEBACTERIUM XEROSE ,5 MILLONES / ML + ASPERGILLUS ,5 MILLONES / ML + PENICILLIUM ,5 MILLONES / ML + EPIDERMOPHYTUS 1,25 MILLONES / ML + TRICHOPHYTUS 1,25 MILLONES / ML + MONILIA 1,25 MILLONES / ML", @"ERITROPOYETINA HUMANA RECOMBINANTE 2000 UI / 2 ML +"];
    comercialNames = @[@"KOATE DVI", @"CEDRIC POCKET", @"ITRAC 200", @"BOLSAS PARA SANGRE CFDA-1", @"RESTELEA", @"SUMMAVAC P", @"LOHP 500", @"JEVITY PLUS", @"ASPIRINA FABRA 500", @"HYPERCRIT"];
    laboratories = @[@"TUTEUR S A C I F I A", @"LABORATORIO ELEA SACIFYA", @"LABORATORIO PABLO CASSARA S R L", @"P.L. RIVERO Y COMPAÑIA SOCIEDAD ANONIMA", @"HLB PHARMA GROUP S.A.", @"ABBOTT LABORATORIES ARGENTINA S.A.", @"LABORATORIOS FABRA S.A.", @"BIOSIDUS SOCIEDAD ANONIMA"];
}

-(void) loadRecommended:(NSString *)searchText values:(NSArray *)values {
    for (NSString *value in values) {
        if([[value lowercaseString] containsString:[searchText lowercaseString]]) {
            [searchResults addObject:value];
        }
    }
}

- (NSArray *) getMedicines: (NSString *)genericName comercialName: (NSString *)comercialName laboratory: (NSString *) laboratory {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    
    for (Medicine *medicine in medicines) {
        BOOL include = YES;
        
        if(genericName.length > 0 && ![[medicine.genericName lowercaseString] containsString:[genericName lowercaseString]]) {
            include = NO;
        }
        
        if(comercialName.length > 0 && ![[medicine.comercialName lowercaseString] containsString:[comercialName lowercaseString]]) {
            include = NO;
        }
        
        if(laboratory.length > 0 && ![[medicine.laboratory lowercaseString] containsString:[laboratory lowercaseString]]) {
            include = NO;
        }
        
        if(include) {
            [result addObject:medicine];
        }
    }
    
    return result;
}

-(void) search:(UISearchBar *)searchBar text:(NSString *)text {
    [searchResults removeAllObjects];
    
    if(text.length == 0) {
        self.tblResults.hidden = YES;
        
        return;
    }
    
    if(text.length < 3) {
        [self.tblResults reloadData];
        
        return;
    }
    
    if([searchBar isEqual:self.txtGenericName]) {
        searchMode = @"generic";
        [self loadRecommended:text values:genericNames];
    } else if([searchBar isEqual:self.txtComercialName]) {
        searchMode = @"comercial";
        [self loadRecommended:text values:comercialNames];
    } else {
        searchMode = @"laboratory";
        [self loadRecommended:text values:laboratories];
    }
    
    [self.tblResults reloadData];
    self.tblResults.hidden = NO;
}

@end
