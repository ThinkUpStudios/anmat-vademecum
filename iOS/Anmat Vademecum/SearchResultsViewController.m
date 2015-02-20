//
//  SearchResultsViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "SearchResultsViewController.h"
#import "Medicine.h"

@interface SearchResultsViewController()

@property NSMutableArray *medicines;

@end

@implementation SearchResultsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.medicines = [[NSMutableArray alloc] init];
    [self loadTestData];
}

- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.medicines count];
}

- (UITableViewCell *) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView
                             dequeueReusableCellWithIdentifier:@"MedicineCell"
                             forIndexPath:indexPath];
    
    Medicine *medicine = [self.medicines objectAtIndex:indexPath.row];
    
    cell.textLabel.text = medicine.comercialName;
    cell.detailTextLabel.text = medicine.laboratory;
    
    return cell;
}

//TODO: Replace this static code by consuming data from DB applying filters from Search screen
- (void)loadTestData {
    Medicine *item1 = [[Medicine alloc] init];
    
    item1.laboratory = @"LABORATORIOS BETA SOCIEDAD ANONIMA";
    item1.comercialName = @"PSICOASTEN 10 mg";
    [self.medicines addObject:item1];
    
    Medicine *item2 = [[Medicine alloc] init];
    
    item2.laboratory = @"ROEMMERS S A I C F";
    item2.comercialName = @"ROVARTAL 10";
    [self.medicines addObject:item2];
    
    Medicine *item3 = [[Medicine alloc] init];
    
    item3.laboratory = @"ROEMMERS S A I C F";
    item3.comercialName = @"ROVARTAL 20";
    [self.medicines addObject:item3];
    
    Medicine *item4 = [[Medicine alloc] init];
    
    item4.laboratory = @"INVESTI FARMA S A";
    item4.comercialName = @"EUGLUCON";
    [self.medicines addObject:item4];
    
    Medicine *item5 = [[Medicine alloc] init];
    
    item5.laboratory = @"INVESTI FARMA S A";
    item5.comercialName = @"CLOBESOL L.A.";
    [self.medicines addObject:item5];
    
    Medicine *item6 = [[Medicine alloc] init];
    
    item6.laboratory = @"MSD ARGENTINA S R L";
    item6.comercialName = @"QUADRIDERM";
    [self.medicines addObject:item6];
    
    Medicine *item7 = [[Medicine alloc] init];
    
    item7.laboratory = @"INSTITUTO BIOLOGICO ARGENTINO S A I C";
    item7.comercialName = @"METRONIDAZOL BIOL";
    [self.medicines addObject:item7];
    
    Medicine *item8 = [[Medicine alloc] init];
    
    item8.laboratory = @"LABORATORIOS CASASCO S A I C";
    item8.comercialName = @"CALLEXE XR";
    [self.medicines addObject:item8];
    
    Medicine *item9 = [[Medicine alloc] init];
    
    item9.laboratory = @"PANALAB S A ARGENTINA";
    item9.comercialName = @"PERMITIL";
    [self.medicines addObject:item9];
    
    Medicine *item10 = [[Medicine alloc] init];
    
    item10.laboratory = @"GADOR S A";
    item10.comercialName = @"DAMSEL";
    [self.medicines addObject:item10];
    
    Medicine *item11 = [[Medicine alloc] init];
    
    item11.laboratory = @"LABORATORIO VARIFARMA S.A.";
    item11.comercialName = @"PURINETHOL";
    [self.medicines addObject:item11];
    
    Medicine *item12 = [[Medicine alloc] init];
    
    item12.laboratory = @"BAYER SOCIEDAD ANONIMA";
    item12.comercialName = @"TABCIN CALIENTE DESCONGESTIVO";
    [self.medicines addObject:item12];
    
    Medicine *item13 = [[Medicine alloc] init];
    
    item13.laboratory = @"LABORATORIO ELEA SACIFYA";
    item13.comercialName = @"PROGEST 200";
    [self.medicines addObject:item13];
    
    Medicine *item14 = [[Medicine alloc] init];
    
    item14.laboratory = @"NOVA ARGENTIA S.A.";
    item14.comercialName = @"COROVAL 5 MG";
    [self.medicines addObject:item14];
    
    Medicine *item15 = [[Medicine alloc] init];
    
    item15.laboratory = @"KLONAL S R L";
    item15.comercialName = @"PIBUPROFENO KLONAL";
    [self.medicines addObject:item15];
}

@end
