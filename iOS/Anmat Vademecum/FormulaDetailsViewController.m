//
//  FormulaDetailsViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/21/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "FormulaDetailsViewController.h"

@interface FormulaDetailsViewController ()

@property NSArray *formulaComponents;

@end

@implementation FormulaDetailsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.formulaComponents = [[NSMutableArray alloc] init];
    [self loadTestData];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.formulaComponents count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView
                             dequeueReusableCellWithIdentifier:@"FormulaCell"
                             forIndexPath:indexPath];
    NSString *component = [self.formulaComponents objectAtIndex:indexPath.row];
    
    cell.textLabel.text = component;
    
    return cell;
}

//TODO: Replace this static code by getting data from selected Medicine model
- (void)loadTestData {
    NSString *formulaDetail = @"CARBONATO DE CALCIO 90,4 MG % + FOSFATO DE MAGNESIO 152 MG % + CARBOXIMETILCISTEINA ,109 MG % + SULFATO FERROSO 5,1 MG % + CLORURO DE MAGNESIO  CS + DEXTROSA  CS + CIANOCOBALAMINA ,4 MCG % + ACIDO FOLICO 40,9 MCG % + VITAMINA K1 9,8 MCG % + IODURO DE POTASIO 21,6 MCG % + CLORURO DE POTASIO 249 MG % + CITRATO DE POTASIO ,141 MG % + RETINOL 271 MCG % + BETACAROTENO 249 MCG % + RIBOFLAVINA 325 MCG % + NIACINAMIDA 1,6 MG % + BIOTINA 6,4 MCG % + PANTOTENATO DE CALCIO 1,3 MG % + ACIDO ASCORBICO 21,7 MG % + VITAMINA D3 53,1 MCG % + TOCOFEROL 5,572 MG % + L-CARNITINA 10,1 MG % + FOSFATO TRICALCICO 27,1 MG % + SULFATO DE MANGANESO 1,2 MG % + CARBOXIMETILCELULOSA SODICA 107 MG % + SULFATO DE COBRE 585 MCG % + ZINC SULFATO 3,4 MG % + CLORURO DE CROMO 17,4 MCG % + AGUA 80,7 G % + ACIDO CITRICO 3 G % + LECITINA ,197 MG % + MOLIBDATO DE SODIO 27,2 MCG % + CASEINATO DE CALCIO 1,5 G % + TAURINA 16,2 MG % + CLORURO DE COLINA 68,2 MG % + CASEINATO DE SODIO 3,3 G % + ACEITE DE MAIZ ,76 G % + ACEITE DE CANOLA 1,1 G % + AISLADO DE PROTEINA DE SOJA 1,25 G % + TIAMINA CLORHIDRATO 336 MCG % + PIRIDOXINA CLORHIDRATO 401 MCG % + FOSFATO DIBASICO DE POTASIO ,033 MG % + SELENATO DE SODIO 17,9 MCG % + DEXTRINAS Y MALTOSA 12,943 MG % + ACEITE DE COCO 1,066 MG % + HIDROXIDO DE POTASIO 8,4 MG % + CITRATO DE SODIO 217 MG % + DL ALFATOCOFEROL ACETATO 2,5 MG % + ACEITE DE GIRASOL 1,1 G % + FRUCTOOLIGASACARIDO 1,1 MG % + MALTODEXTRINA DE18 21,1 G % + FIBRA DE SOJA FIBRIM 300 726 MG %";
    
    self.formulaComponents = [formulaDetail componentsSeparatedByString:@"+"];
}

@end
