//
//  PregnancyViewController.m
//  VNM
//
//  Created by mag on 10/24/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import "PregnancyViewController.h"
#import "AutoLayoutCell1Line.h"
#import "Section.h"
#import "SectionRow1Line.h"
#import "PregnancyGroupService.h"
#import "PregnancyGroup.h"
#import "PregnancyComponent.h"
#import "PregnancyDetailsViewController.h"

static NSString * const AutoLayoutCellIdentifier = @"PregnancyCell";

@interface PregnancyViewController ()

@end

@implementation PregnancyViewController {
    NSMutableArray *sections;
    PregnancyGroupService *groupService;
    NSArray *groups;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    sections = [[NSMutableArray alloc] init];
    groupService = [[PregnancyGroupService alloc] init];
    
    [sections addObject:[self getIntro]];
    
    Section *groupsSection = [[Section alloc] initWithName:@"Grupos"];
    
    groups = [groupService getAll];
    
    for(PregnancyGroup *group in groups) {
        [groupsSection addRow:group.name];
    }
    
    [sections addObject:groupsSection];
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
    return sections.count;
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

-(void)tableView:(UITableView *)tableView accessoryButtonTappedForRowWithIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *selectedCell = [tableView cellForRowAtIndexPath:indexPath];
    
    [self performSegueWithIdentifier:@"ShowPregnancyDetail" sender:selectedCell];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([[segue identifier] isEqualToString:@"ShowPregnancyDetail"]) {
        PregnancyDetailsViewController *detail = segue.destinationViewController;
        
        NSIndexPath *selectedIndex = [self.tableView indexPathForCell:sender];
        
        Section *section = sections[1];
        SectionRow1Line *row = [section getRows][selectedIndex.row];
        
        PregnancyGroup *selectedGroup = nil;
        
        for(PregnancyGroup *group in groups) {
            if([group.name isEqualToString:row.line1]) {
                selectedGroup = group;
                break;
            }
        }
        
        detail.group = selectedGroup;
    }
}

- (AutoLayoutCell1Line *)basicCellAtIndexPath:(NSIndexPath *)indexPath {
    AutoLayoutCell1Line *cell = [self.tableView dequeueReusableCellWithIdentifier:AutoLayoutCellIdentifier forIndexPath:indexPath];
    
    [self configureBasicCell:cell atIndexPath:indexPath];
    
    return cell;
}

- (void)configureBasicCell:(AutoLayoutCell1Line *)cell atIndexPath:(NSIndexPath *)indexPath {
    Section *tableSection = sections[indexPath.section];
    SectionRow1Line *row = [tableSection getRows][indexPath.row];
    
    [self setContentForCell:cell line1:row.line1 blankAsDash:indexPath.row > 0];
    
    if([tableSection.name isEqualToString:@"Grupos"]) {
        cell.accessoryType =  UITableViewCellAccessoryDisclosureIndicator;
        cell.selectionStyle = UITableViewCellSelectionStyleGray;
        cell.userInteractionEnabled = YES;
    } else {
        cell.accessoryType  = UITableViewCellAccessoryNone;
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.userInteractionEnabled = NO;
    }
}

- (void)setContentForCell:(AutoLayoutCell1Line *)cell line1:(NSString *)line1 blankAsDash:(BOOL) blankAsDash {
    
    if(line1 == nil || line1.length == 0) {
        line1 = blankAsDash ? @"-" : line1;
    }
    
    [cell.lblLine1 setText:line1];
}

- (CGFloat)heightForBasicCellAtIndexPath:(NSIndexPath *)indexPath {
    static AutoLayoutCell1Line *sizingCell = nil;
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

- (void) appendLine:(NSMutableString *) stringBuilder line:(NSString *) line {
    [stringBuilder appendString:[NSString stringWithFormat:@"%@%@", line, @"\r\n"]];
}

- (Section *) getIntro {
    Section *mainSection = [[Section alloc] initWithName:@"Principios activos con su factor de riesgo según la FDA, durante el embarazo"];
    
    NSMutableString *builder = [[NSMutableString alloc] init];
    
    [self appendLine:builder line:@"La FDA según el riesgo clasifica los fármacos en las siguientes categorías:"];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"Categoría A: Estudios adecuados y bien controlados no han demostrado riesgo para el feto en el primer trimestre de embarazo y no existen evidencias de riesgo en trimestres posteriores. La posibilidad de daño fetal parece remota."];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"Categoría B: Indica una de las siguientes posibilidades:"];
    [self appendLine:builder line:@"- Estudios en animales no indican riesgo teratogénico fetal, pero esto no ha sido confirmado en embarazadas"];
    [self appendLine:builder line:@"- Estudios en animales muestran cierto potencial teratógeno, pero estudios bien controlados con gestantes no han demostrado riesgos para el feto en el primer trimestre y no existen evidencias para el feto en trimestres posteriores"];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"Categoría C: Se suele asignar a fármacos cuya administración solo debe considerarse si el beneficio esperado justifica el potencial riesgo para el feto. Indica una de las sig. posibilidades:"];
    [self appendLine:builder line:@"- Estudios sobre animales han detectado efecto teratógeno o embriocida del fármaco, pero aún no se ha ensayado en la mujer."];
    [self appendLine:builder line:@"- No se dispone de estudios ni en animales ni en mujeres."];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"Categoría D: Se dispone de evidencia de efectos teratógenos sobre el feto humano y por tanto de la existencia de un claro riesgo. Sin embargo el beneficio obtenido con estos medicamentos puede superar el riesgo esperado y hacer aconsejable su uso(situaciones límites de posible muerte materna, afecciones graves en las que no es posible usar alternativas más seguras o éstas son ineficaces…)"];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"Categoría X: Estudios en animales o en  humanos han demostrado anomalías congénitas manifiestas, existen evidencias de riesgo fetal basadas enla experiencia en embarazadas y los riesgos superan claramente cualquier posible beneficio a obtener, por lo que los fármacos están absolutamente contraindicados."];
    
    [mainSection addRow:builder];
    
    return mainSection;
}

@end