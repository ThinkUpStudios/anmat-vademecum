//
//  InformationViewController.m
//  VNM
//
//  Created by mag on 4/27/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "InformationViewController.h"
#import "InformationDetailViewController.h"
#import "AutoLayoutCell1Line.h"
#import "Section.h"
#import "SectionRow1Line.h"

@interface InformationViewController ()

@end

@implementation InformationViewController {
   NSMutableArray *informationTitles;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    informationTitles = [[NSMutableArray alloc] init];

    [informationTitles addObject:@"Prescripción por nombre genérico"];
    [informationTitles addObject:@"Uso racional de medicamentos"];
    [informationTitles addObject:@"Sistema nacional de trazabilidad"];
    [informationTitles addObject:@"Medicamentos y embarazo"];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)tableView:(UITableView *)tableView accessoryButtonTappedForRowWithIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *selectedCell = [tableView cellForRowAtIndexPath:indexPath];

    NSString *title = [informationTitles objectAtIndex:indexPath.item];
    NSString *segueIdentifier = @"";
    
    if([title isEqualToString:@"Prescripción por nombre genérico"]) {
        segueIdentifier = @"ShowPrescription";
    } else if([title isEqualToString:@"Uso racional de medicamentos"]) {
        segueIdentifier = @"ShowRational";
    } else if([title isEqualToString:@"Sistema nacional de trazabilidad"]) {
        segueIdentifier = @"ShowTrazability";
    } else {
        segueIdentifier = @"ShowPregnancy";
    }
    
    [self performSegueWithIdentifier:segueIdentifier sender:selectedCell];
}

-(void)tableView:(UITableView *)tableView willDisplayHeaderView:(UIView *)view forSection:(NSInteger)section {
    UITableViewHeaderFooterView *headerIndexText = (UITableViewHeaderFooterView *)view;
    
    headerIndexText.backgroundView.backgroundColor = [UIColor colorWithRed:239/255.0 green:239/255.0 blue:239/255.0 alpha:255.0];
    
    headerIndexText.textLabel.textColor = [UIColor colorWithRed:38/255.0 green:98/255.0 blue:140/255.0 alpha:255.0];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if(![[segue identifier] isEqualToString:@"ShowPregnancy"]) {
        InformationDetailViewController *detail = segue.destinationViewController;
        
        NSIndexPath *selectedIndex = [self.tableView indexPathForCell:sender];
        NSString *title = [informationTitles objectAtIndex:selectedIndex.item];
        
        detail.title = title;
        
        Section* section = [[Section alloc] init];
        
        if([[segue identifier] isEqualToString:@"ShowPrescription"]) {
            section = [self getPrescriptionSection];
        } else if ([[segue identifier] isEqualToString:@"ShowRational"]) {
            section = [self getRationalSection];
        } else {
            section = [self getTrazabilitySection];
        }
        
        detail.section = section;
    }
}

- (Section *) getPrescriptionSection {
    Section *mainSection = [[Section alloc] initWithName:@"¿Qué es el nombre genérico?"];
    
    NSMutableString *builder = [[NSMutableString alloc] init];
    
    [self appendLine:builder line:@"Nombre genérico es la denominación de un principio activo, monodroga  o de una asociación de principios activos a dosis fijas adoptada por la autoridad sanitaria (ANMAT)  o, en su defecto, la denominación común internacional recomendada por la Organización Mundial de la Salud."];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"Según la ley 25.649 es obligatorio que toda receta médica exprese el nombre genérico del medicamento o denominación común internacional, seguida de forma farmacéutica y dosis/ unidad, con detalle del grado de concentración."];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"De esta forma, el farmacéutico debe ofrecer todas las opciones disponibles."];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"La receta puede indicar, además del nombre genérico, el nombre o marca comercial. En ese caso, si el consumidor lo solicita, el farmacéutico debe sustituir la misma por una especialidad medicinal de menor precio que contenga los mismos principios activos, concentración, forma farmacéutica y similar cantidad de unidades."];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"La libertad de prescripción y de dispensa está garantizada por la elección del principio activo y no sobre especialidades de referencia o de marca."];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"El uso de nombre genérico es obligatorio en:"];
    [self appendLine:builder line:@"     a) Todo envase, rótulo, prospecto o cualquier documento utilizado por la industria farmacéutica para información médica o promoción de las especialidades medicinales;"];
    [self appendLine:builder line:@"     b) Todos los textos normativos, inclusive registros y autorizaciones relativas a la elaboración, fraccionamiento, comercialización, exportación e importación de medicamentos;"];
    [self appendLine:builder line:@"     c) Toda publicidad o propaganda dirigida al público en general."];
    
    [mainSection addRow:builder];
    
    return mainSection;
}

- (Section *) getRationalSection {
    Section *mainSection = [[Section alloc] initWithName:@"Uso Racional de Medicamentos"];
    
    NSMutableString *builder = [[NSMutableString alloc] init];
    
    [self appendLine:builder line:@"\"Los pacientes deben recibir la medicación adecuada a sus necesidades clínicas, en las dosis correspondientes a sus requisitos individuales, durante un período de tiempo adecuado y al menor coste posible para ellos y para la comunidad\". (OMS, 1985)."];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"En el proceso de la prescripción de medicamentos es importante:"];
    [self appendLine:builder line:@"    - Definir el o los problemas del paciente."];
    [self appendLine:builder line:@"    - Especificar los objetivos terapéuticos."];
    [self appendLine:builder line:@"    - Diseñar un esquema terapéutico apropiado para cada paciente."];
    [self appendLine:builder line:@"    - Escribir la receta (iniciar el tratamiento) según lo dispuesto por Ley de Promoción de la Utilización de Medicamentos por Nombre Genérico N° 25649."];
    [self appendLine:builder line:@"    - Brindar información, instrucciones y advertencias."];
    [self appendLine:builder line:@"    - Supervisar la evolución del tratamiento."];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"El uso apropiado de los medicamentos impacta directamente en la salud de las personas, y por ejemplo en el caso de los antibióticos, el uso adecuado contribuye a combatir la resistencia de los microorganismos a los antibióticos."];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"Cabe señalar que, en la Argentina por Resolución conjunta del Ministerio de Salud de la Nación N° 834/2015 y del Ministerio de Agricultura, Ganadería y Pesca N° 391/2015 se establece la Estrategia para el Control de la Resistencia Antimicrobiana, y uno de sus ejes de trabajo es la correcta administración de los antibióticos."];
    
    [mainSection addRow:builder];
    
    return mainSection;
}

- (Section *) getTrazabilitySection {
    Section *mainSection = [[Section alloc] initWithName:@"¿Qué es el sistema de Trazabilidad?"];
    
    NSMutableString *builder = [[NSMutableString alloc] init];
    
    [self appendLine:builder line:@"Es un sistema de control que consiste en la identificación de cada uno de los medicamentos que van a ser comercializados y su seguimiento a través de toda la cadena de distribución (laboratorios, distribuidoras, operadores logísticos, farmacias, establecimientos asistenciales)  para erradicar los que sean ilegítimos."];
    [self appendLine:builder line:@""];
    [self appendLine:builder line:@"Si tenes dudas de cualquier tipo referidas a medicamentos, alimentos, productos médicos, cosmétcos, domisanitarios, reactivos de diagnóstico, faltantes, trámites o legislación comunicate ANMAT Responde al 0800-333-1234 o vía mail a responde@anmat.gov.ar"];
    
    [mainSection addRow:builder];
    
    return mainSection;
}

- (void) appendLine:(NSMutableString *) stringBuilder line:(NSString *) line {
    [stringBuilder appendString:[NSString stringWithFormat:@"%@%@", line, @"\r\n"]];
}

@end
