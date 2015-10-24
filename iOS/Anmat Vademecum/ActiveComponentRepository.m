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
#import "String.h"

@implementation ActiveComponentRepository

-(NSArray *) getAllNames {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = @"SELECT ifa FROM principio_activo ORDER BY ifa ASC";
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        while (sqlite3_step(statement) == SQLITE_ROW) {
            char *componentNameChars = (char *) sqlite3_column_text(statement, 0);
            NSString *componentName = [String trim:[[NSString alloc] initWithUTF8String:componentNameChars]];
            
            if(componentName.length > 0) {
                [result addObject:componentName];
            }
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

- (NSArray *) getAllNames:(NSString *)searchText {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = [NSString stringWithFormat: @"SELECT ifa, otros_nombres FROM principio_activo WHERE ifa LIKE \"%%%@%%\" COLLATE NOCASE OR otros_nombres LIKE \"%%%@%%\" COLLATE NOCASE ORDER BY ifa ASC", [String trim:searchText], [String trim:searchText]];
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        while (sqlite3_step(statement) == SQLITE_ROW) {
            char *componentNameChars = (char *) sqlite3_column_text(statement, 0);
            NSString *componentName = [String trim:[self getUTF8:componentNameChars]];

            if(componentName.length > 0 && ![result containsObject:componentName]) {
                [result addObject:componentName];
            }
            
            char *otherNamesChars = (char *) sqlite3_column_text(statement, 1);
            NSString *otherNames = [String trim:[self getUTF8:otherNamesChars]];
            
            NSArray *otherNamesParts = [otherNames componentsSeparatedByString:@"#"];
            
            for (NSString *otherName in otherNamesParts) {
                if(otherName.length > 0 && ![result containsObject:otherName]) {
                    [result addObject:otherName];
                }
            }
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

- (ActiveComponent *) getByName: (NSString *)name {
    ActiveComponent *result = nil;
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = [NSString stringWithFormat: @"SELECT * FROM principio_activo WHERE ifa=\"%@\" COLLATE NOCASE OR otros_nombres LIKE \"%%%@%%\" COLLATE NOCASE LIMIT 1", [String trim:name], [String trim:name]];
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

- (NSArray *) getAllIdentifiers:(NSString *)name {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = [NSString stringWithFormat: @"SELECT ifa, otros_nombres FROM principio_activo WHERE ifa=\"%@\" COLLATE NOCASE OR otros_nombres LIKE \"%%%@%%\" COLLATE NOCASE  LIMIT 1", [String trim:name], [String trim:name]];
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        while (sqlite3_step(statement) == SQLITE_ROW) {
            char *componentNameChars = (char *) sqlite3_column_text(statement, 0);
            char *otherNamesChars = (char *) sqlite3_column_text(statement, 1);
            NSString *componentName = [String trim:[self getUTF8:componentNameChars]];
            
            if(componentName.length > 0) {
                [result addObject:componentName];
            }
            
            NSString *otherNames = [String trim:[self getUTF8:otherNamesChars]];
            
            if(otherNames.length > 0) {
                NSArray *otherNamesSplitted = [otherNames componentsSeparatedByString:@"#"];
                
                [result addObjectsFromArray:otherNamesSplitted];
            }
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

-(ActiveComponent *) getActiveComponent:(sqlite3_stmt *) statement {
    char *ifaChars = (char *) sqlite3_column_text(statement, 0);
    char *clasificationChars = (char *) sqlite3_column_text(statement, 1);
    char *pharmacologyChars = (char *) sqlite3_column_text(statement, 2);
    char *actionChars = (char *) sqlite3_column_text(statement, 3);
    char *cineticChars = (char *) sqlite3_column_text(statement, 4);
    char *indicationChars = (char *) sqlite3_column_text(statement, 5);
    char *posologyChars = (char *) sqlite3_column_text(statement, 6);
    char *contraindicationChars = (char *) sqlite3_column_text(statement, 7);
    char *interactionChars = (char *) sqlite3_column_text(statement, 8);
    char *reactionChars = (char *) sqlite3_column_text(statement, 9);
    char *referenceChars = (char *) sqlite3_column_text(statement, 10);
    char *additionalInfoChars = (char *) sqlite3_column_text(statement, 11);
    char *bibliographyChars = (char *) sqlite3_column_text(statement, 12);
    
    NSString *ifa = [self getUTF8:ifaChars];
    NSString *clasification = [self getUTF8:clasificationChars];
    NSString *pharmacology = [self getUTF8:pharmacologyChars];
    NSString *action = [self getUTF8:actionChars];
    NSString *cinetic = [self getUTF8:cineticChars];
    NSString *indication = [self getUTF8:indicationChars];
    NSString *posology = [self getUTF8:posologyChars];
    NSString *contraindication = [self getUTF8:contraindicationChars];
    NSString *interaction = [self getUTF8:interactionChars];
    NSString *reaction = [self getUTF8:reactionChars];
    NSString *reference = [self getUTF8:referenceChars];
    NSString *additionalInfo = [self getUTF8:additionalInfoChars];
    NSString *bibliography = [self getUTF8:bibliographyChars];
    
    ActiveComponent *component = [[ActiveComponent alloc] init];
    
    component.ifa = [self getFormattedValue:ifa];
    component.clasification = [self getFormattedValue:clasification];
    component.pharmacology = [self getFormattedValue:pharmacology];
    component.action = [self getFormattedValue:action];
    component.cinetic = [self getFormattedValue:cinetic];
    component.indication = [self getFormattedValue:indication];
    component.posology = [self getFormattedValue:posology];
    component.contraindication = [self getFormattedValue:contraindication];
    component.interaction = [self getFormattedValue:interaction];
    component.reaction = [self getFormattedValue:reaction];
    component.reference = [self getFormattedValue:reference];
    component.additionalInfo = [self getFormattedValue:additionalInfo];
    component.bibliography = [self getFormattedValue:bibliography];
    
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
        return @"";
    } else {
        return value;
    }
}

@end
