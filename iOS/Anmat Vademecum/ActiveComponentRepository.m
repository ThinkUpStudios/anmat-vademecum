//
//  ActiveComponentRepository.m
//  Anmat Vademecum
//
//  Created by mag on 3/31/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "ActiveComponentRepository.h"
#import "DataBaseProvider.h"
#import "sqlite3.h"

@implementation ActiveComponentRepository

-(NSArray *) getAllNames {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = @"SELECT principio FROM principiosactivos ORDER BY principio ASC";
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        while (sqlite3_step(statement) == SQLITE_ROW) {
            char *componentNameChars = (char *) sqlite3_column_text(statement, 0);
            NSString *componentName = [[NSString alloc] initWithUTF8String:componentNameChars];
            
            [result addObject:componentName];
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

- (NSArray *) getAllNames:(NSString *)searchText {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = @"SELECT principio FROM principiosactivos WHERE principio LIKE ?001 COLLATE NOCASE ORDER BY principio ASC";
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        NSString *search = [NSString stringWithFormat:@"%%%@%%", searchText];
        
        sqlite3_bind_text(statement, 1, [search UTF8String], -1, SQLITE_STATIC);
        
        while (sqlite3_step(statement) == SQLITE_ROW) {
            char *componentNameChars = (char *) sqlite3_column_text(statement, 0);
            NSString *componentName = [self getUTF8:componentNameChars];
            
            [result addObject:componentName];
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

- (ActiveComponent *) getByName: (NSString *)name {
    ActiveComponent *result = nil;
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = [NSString stringWithFormat: @"SELECT * FROM principiosactivos WHERE principio=\"%@\" LIMIT 1", name];
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        while (sqlite3_step(statement) == SQLITE_ROW) {
            result = [self getActiveComponent:statement];
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

-(ActiveComponent *) getActiveComponent:(sqlite3_stmt *) statement {
    char *componentNameChars = (char *) sqlite3_column_text(statement, 0);
    char *actionChars = (char *) sqlite3_column_text(statement, 1);
    char *indicationChars = (char *) sqlite3_column_text(statement, 2);
    char *presentationChars = (char *) sqlite3_column_text(statement, 3);
    char *posologyChars = (char *) sqlite3_column_text(statement, 4);
    char *durationChars = (char *) sqlite3_column_text(statement, 5);
    char *contraindicationChars = (char *) sqlite3_column_text(statement, 6);
    char *observationChars = (char *) sqlite3_column_text(statement, 7);
    
    NSString *componentName = [self getUTF8:componentNameChars];
    NSString *action = [self getUTF8:actionChars];
    NSString *indication = [self getUTF8:indicationChars];
    NSString *presentation = [self getUTF8:presentationChars];
    NSString *posology = [self getUTF8:posologyChars];
    NSString *duration = [self getUTF8:durationChars];
    NSString *contraindication = [self getUTF8:contraindicationChars];
    NSString *observation = [self getUTF8:observationChars];
    
    ActiveComponent *component = [[ActiveComponent alloc] init];
    
    component.name = [self getFormattedValue:componentName];
    component.action = [self getFormattedValue:action];
    component.indication = [self getFormattedValue:indication];
    component.presentation = [self getFormattedValue:presentation];
    component.posology = [self getFormattedValue:posology];
    component.duration = [self getFormattedValue:duration];
    component.contraindication = [self getFormattedValue:contraindication];
    component.observation = [self getFormattedValue:observation];
    
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
