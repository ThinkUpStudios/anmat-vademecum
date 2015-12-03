//
//  PregnancyGroupRepository.m
//  VNM
//
//  Created by mag on 12/2/15.
//  Copyright © 2015 Think Up Studios. All rights reserved.
//

#import "PregnancyComponentRepository.h"
#import "PregnancyComponent.h"
#import "DataBaseProvider.h"
#import "String.h"

@implementation PregnancyComponentRepository

-(NSArray *) getAll {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSMutableString *query = [[NSMutableString alloc] init];
    
    [query appendString: @"SELECT descripcion, principio, medicamentos, clasificacion FROM grupo INNER JOIN grupo_principio_emb ON grupo.id = grupo_principio_emb.grupo_id"];
    
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil) == SQLITE_OK) {
        while (sqlite3_step(statement) == SQLITE_ROW) {
            PregnancyComponent *component = [self getComponent:statement];
            
            [result addObject:component];
        }
        
        sqlite3_finalize(statement);
    } else {
        NSAssert1(0, @"Error on Database. '%s'", sqlite3_errmsg(database));
    }
    
    sqlite3_close(database);
    
    return result;
}

-(PregnancyComponent *) getComponent:(sqlite3_stmt *) statement {
    char *groupChars = (char *) sqlite3_column_text(statement, 0);
    char *ifaChars = (char *) sqlite3_column_text(statement, 1);
    char *medicinesChars = (char *) sqlite3_column_text(statement, 2);
    char *clasificationChars = (char *) sqlite3_column_text(statement, 3);
    
    NSString *group =  [self getUTF8:groupChars];
    NSString *ifa =  [self getUTF8:ifaChars];
    NSString *medicines = [self getUTF8:medicinesChars];
    NSString *clasification =  [self getUTF8:clasificationChars];
    
    PregnancyComponent *component = [[PregnancyComponent alloc] init];
    
    component.group = [self getFormattedValue:group];
    component.ifa = [self getFormattedValue:ifa];
    component.medicines = [self getFormattedValue:medicines];
    component.clasification = [self getFormattedValue:clasification];
    
    return component;
}

-(NSString *) getUTF8:(char *)value {
    if(value == nil) {
        return @"";
    }
    
    return [NSString stringWithUTF8String:value];
}

-(NSString *) getFormattedValue:(NSString *)value {
    if(value == nil || value.length == 0) {
        return @"-";
    } else {
        return value;
    }
}

@end
