//
//  PregnancyViewController.m
//  VNM
//
//  Created by mag on 10/24/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import "PregnancyViewController.h"
#import "AutoLayoutCellWithDetail.h"
#import "Section.h"

static NSString * const AutoLayoutCellIdentifier = @"AutoLayoutCellWithDetail";

@interface PregnancyViewController ()

@end

@implementation PregnancyViewController {
    NSMutableArray *sections;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    sections = [[NSMutableArray alloc] init];
    
    Section *mainSection = [[Section alloc] initWithName:@"Principios activos con su factor de riesgo según la FDA, durante el embarazo"];
    
    NSMutableString *builder = [[NSMutableString alloc] init];
    
    [self appendLine:builder line:@"La FDA según el riesgo clasifica los fármacos en las siguientes categorías:"];
    [self appendLine:builder line:@"Categoría A: Estudios adecuados y bien controlados no han demostrado riesgo para el feto en el primer trimestre de embarazo y no existen evidencias de riesgo en trimestres posteriores. La posibilidad de daño fetal parece remota."];
    [self appendLine:builder line:@"Categoría B: Indica una de las siguientes posibilidades:"];
    [self appendLine:builder line:@"- Estudios en animales no indican riesgo teratogénico fetal, pero esto no ha sido confirmado en embarazadas"];
    [self appendLine:builder line:@"- Estudios en animales muestran cierto potencial teratógeno, pero estudios bien controlados con gestantes no han demostrado riesgos para el feto en el primer trimestre y no existen evidencias para el feto en trimestres posteriores"];
    [self appendLine:builder line:@"Categoría C: Se suele asignar a fármacos cuya administración solo debe considerarse si el beneficio esperado justifica el potencial riesgo para el feto. Indica una de las sig. posibilidades:"];
    [self appendLine:builder line:@"- Estudios sobre animales han detectado efecto teratógeno o embriocida del fármaco, pero aún no se ha ensayado en la mujer."];
    [self appendLine:builder line:@"- No se dispone de estudios ni en animales ni en mujeres."];
    [self appendLine:builder line:@"Categoría D: Se dispone de evidencia de efectos teratógenos sobre el feto humano y por tanto de la existencia de un claro riesgo. Sin embargo el beneficio obtenido con estos medicamentos puede superar el riesgo esperado y hacer aconsejable su uso(situaciones límites de posible muerte materna, afecciones graves en las que no es posible usar alternativas más seguras o éstas son ineficaces...)"];
    
    [mainSection addRow:builder detail:@""];
    
    Section *antibioticsSection = [[Section alloc] initWithName:@"Antibióticos"];
    
    [antibioticsSection addRow:@"Cefuroxima, Cefaclor, Cefixima, Cefradina, Etc" detail:@"B Y D en 1° trimestre"];
    [antibioticsSection addRow:@"Cefradina" detail:@"A, B"];
    
    Section *analgesicsSection = [[Section alloc] initWithName:@"Analgésicos y Antiinflamatorios"];
    
    [analgesicsSection addRow:@"Paracetamol" detail:@"B"];
    [analgesicsSection addRow:@"Acido Meclofenamico" detail:@"A, B"];
    
    [sections addObject:mainSection];
    [sections addObject:antibioticsSection];
    [sections addObject:analgesicsSection];
}

- (void) appendLine:(NSMutableString *) stringBuilder line:(NSString *) line {
    [stringBuilder appendString:[NSString stringWithFormat:@"%@%@", line, @"\r\n"]];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if(sections.count > 0) {
        Section *tableSection = sections[section];
        
        return [tableSection getRows].count;
    } else {
        return 0;
    }
}

- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView {
    if(sections.count > 0) {
        return sections.count;
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

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    return [self basicCellAtIndexPath:indexPath];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return [self heightForBasicCellAtIndexPath:indexPath];
}

-(void)tableView:(UITableView *)tableView willDisplayHeaderView:(UIView *)view forSection:(NSInteger)section {
    UITableViewHeaderFooterView *headerIndexText = (UITableViewHeaderFooterView *)view;
    
    headerIndexText.backgroundView.backgroundColor = [UIColor colorWithRed:239/255.0 green:239/255.0 blue:239/255.0 alpha:255.0];
    
    headerIndexText.textLabel.textColor = [UIColor colorWithRed:38/255.0 green:98/255.0 blue:140/255.0 alpha:255.0];
}

-(NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    Section *tableSection = sections[section];
    
    return tableSection.name;
}

- (AutoLayoutCellWithDetail *)basicCellAtIndexPath:(NSIndexPath *)indexPath {
    AutoLayoutCellWithDetail *cell = [self.tableView dequeueReusableCellWithIdentifier:AutoLayoutCellIdentifier forIndexPath:indexPath];
    
    [self configureBasicCell:cell atIndexPath:indexPath];
    
    return cell;
}

- (void)configureBasicCell:(AutoLayoutCellWithDetail *)cell atIndexPath:(NSIndexPath *)indexPath {
    Section *tableSection = sections[indexPath.section];
    SectionRow *row = [tableSection getRows][indexPath.row];
    
    NSString *value = row.value;
    NSString *detail = row.detail;
    
    [self setContentForCell:cell value:value detail:detail];
}

- (void)setContentForCell:(AutoLayoutCellWithDetail *)cell value:(NSString *)value detail:(NSString *)detail {
    value = value ?: NSLocalizedString(@"[-]", nil);
    detail = detail ?: NSLocalizedString(@"[-]", nil);
    
    [cell.lblTitle setText:value];
    [cell.lblSubtitle setText:detail];
}

- (CGFloat)heightForBasicCellAtIndexPath:(NSIndexPath *)indexPath {
    static AutoLayoutCellWithDetail *sizingCell = nil;
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        sizingCell = [self.tableView dequeueReusableCellWithIdentifier:AutoLayoutCellIdentifier];
    });
    
    [self configureBasicCell:sizingCell atIndexPath:indexPath];
    
    return [self calculateHeightForConfiguredSizingCell:sizingCell];
}

- (CGFloat)calculateHeightForConfiguredSizingCell:(UITableViewCell *)sizingCell {
    [sizingCell setNeedsLayout];
    [sizingCell layoutIfNeeded];
    
    CGSize size = [sizingCell.contentView systemLayoutSizeFittingSize:UILayoutFittingCompressedSize];
    
    sizingCell.bounds = CGRectMake(0.0f, 0.0f, CGRectGetWidth(self.tableView.frame), CGRectGetHeight(sizingCell.bounds));
    
    return size.height + 1.0f;
}

@end
