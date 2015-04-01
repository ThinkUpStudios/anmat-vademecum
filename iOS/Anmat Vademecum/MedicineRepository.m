//
//  MedicineRepository.m
//  Anmat Vademecum
//
//  Created by mag on 3/16/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "MedicineRepository.h"
#import "Medicine.h"
#import "DataBaseProvider.h"

@implementation MedicineRepository

-(NSArray *) getAll {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = @"SELECT * FROM medicamentos ORDER BY precio DESC";
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        while (sqlite3_step(statement) == SQLITE_ROW) {
            Medicine *medicine = [self getMedicine:statement];
            
            [result addObject:medicine];
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

-(NSArray *) getAll:(NSString *)genericName comercialName:(NSString *)comercialName laboratory:(NSString *)laboratory {
    int addedConditions = 0;
    NSMutableString *query = [[NSMutableString alloc] init];
    
    [query appendString:@"SELECT * FROM medicamentos WHERE "];
    
    if(genericName != nil && genericName.length > 0) {
        [query appendString:@"generico LIKE ?"];
        [query appendString:[self getVariableNumber:addedConditions]];
        [query appendString:@" COLLATE NOCASE"];
        addedConditions = addedConditions + 1;
    }
    
    if(comercialName != nil && comercialName.length > 0) {
        if(addedConditions > 0) {
            [query appendString:@" AND "];
        }
        
        [query appendString:@"comercial LIKE ?"];
        [query appendString:[self getVariableNumber:addedConditions]];
        [query appendString:@" COLLATE NOCASE"];
        addedConditions = addedConditions + 1;
    }
    
    if(laboratory != nil && laboratory.length > 0) {
        if(addedConditions > 0) {
            [query appendString:@" AND "];
        }
        
        [query appendString:@"laboratorio LIKE ?"];
        [query appendString:[self getVariableNumber:addedConditions]];
        [query appendString:@" COLLATE NOCASE"];
        addedConditions = addedConditions + 1;
    }
    
    [query appendString:@" ORDER BY precio DESC"];
    
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        if(genericName != nil && genericName.length > 0) {
            NSString *genericNameSearch = [NSString stringWithFormat:@"%%%@%%", genericName];
            
            sqlite3_bind_text(statement, 1, [genericNameSearch UTF8String], -1, SQLITE_STATIC);
        }
        
        if(comercialName != nil && comercialName.length > 0) {
            NSString *comercialNameSearch = [NSString stringWithFormat:@"%%%@%%", comercialName];
            int variableNumber = 0;
            
            if(genericName != nil && genericName.length > 0) {
                variableNumber = 2;
            } else {
                variableNumber = 1;
            }
            
            sqlite3_bind_text(statement, variableNumber, [comercialNameSearch UTF8String], -1, SQLITE_STATIC);
        }
        
        if(laboratory != nil && laboratory.length > 0) {
            NSString *laboratorySearch = [NSString stringWithFormat:@"%%%@%%", laboratory];
            
            sqlite3_bind_text(statement, addedConditions, [laboratorySearch UTF8String], -1, SQLITE_STATIC);
        }

        while (sqlite3_step(statement) == SQLITE_ROW) {
            Medicine *medicine = [self getMedicine:statement];
            
            [result addObject:medicine];
        }
        
        sqlite3_finalize(statement);
    }else {
        NSLog(@"Error while creating statement. '%s'", sqlite3_errmsg(database));
    }
    
    sqlite3_close(database);
    
    return result;
}

- (NSArray *) getGenericNames:(NSString *)searchText {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = @"SELECT DISTINCT generico FROM medicamentos WHERE generico LIKE ?001 COLLATE NOCASE ORDER BY generico ASC";
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        NSString *search = [NSString stringWithFormat:@"%%%@%%", searchText];
        
        sqlite3_bind_text(statement, 1, [search UTF8String], -1, SQLITE_STATIC);
        
        while (sqlite3_step(statement) == SQLITE_ROW) {
            char *genericNameChars = (char *) sqlite3_column_text(statement, 0);
            NSString *genericName = [[NSString alloc] initWithUTF8String:genericNameChars];
            
            [result addObject:genericName];
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

- (NSArray *) getComercialNames:(NSString *)searchText {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = @"SELECT DISTINCT comercial FROM medicamentos WHERE comercial LIKE ?001 COLLATE NOCASE ORDER BY comercial ASC";
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        NSString *search = [NSString stringWithFormat:@"%%%@%%", searchText];
        
        sqlite3_bind_text(statement, 1, [search UTF8String], -1, SQLITE_STATIC);
        
        while (sqlite3_step(statement) == SQLITE_ROW) {
            char *comercialNameChars = (char *) sqlite3_column_text(statement, 0);
            NSString *comercialName = [[NSString alloc] initWithUTF8String:comercialNameChars];
            
            [result addObject:comercialName];
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

- (NSArray *) getLaboratories:(NSString *)searchText {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = @"SELECT DISTINCT laboratorio FROM medicamentos WHERE laboratorio LIKE ?001 COLLATE NOCASE ORDER BY laboratorio ASC";
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        NSString *search = [NSString stringWithFormat:@"%%%@%%", searchText];
        
        sqlite3_bind_text(statement, 1, [search UTF8String], -1, SQLITE_STATIC);
        
        while (sqlite3_step(statement) == SQLITE_ROW) {
            char *laboratoryChars = (char *) sqlite3_column_text(statement, 0);
            NSString *laboratory = [[NSString alloc] initWithUTF8String:laboratoryChars];
            
            [result addObject:laboratory];
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

-(Medicine *) getMedicine:(sqlite3_stmt *) statement {
    char *idChars = (char *) sqlite3_column_text(statement, 0);
    char *certificateChars = (char *) sqlite3_column_text(statement, 1);
    char *cuitChars = (char *) sqlite3_column_text(statement, 2);
    char *laboratoryChars = (char *) sqlite3_column_text(statement, 3);
    char *gtinChars = (char *) sqlite3_column_text(statement, 4);
    char *troquelChars = (char *) sqlite3_column_text(statement, 5);
    char *comercialNameChars = (char *) sqlite3_column_text(statement, 6);
    char *formChars = (char *) sqlite3_column_text(statement, 7);
    char *genericNameChars = (char *) sqlite3_column_text(statement, 8);
    char *countryChars = (char *) sqlite3_column_text(statement, 9);
    char *requestConditionChars = (char *) sqlite3_column_text(statement, 10);
    char *trazabilityChars = (char *) sqlite3_column_text(statement, 11);
    char *presentationChars = (char *) sqlite3_column_text(statement, 12);
    char *priceChars = (char *) sqlite3_column_text(statement, 13);
    
    NSString *id = [self getUTF8:idChars];
    NSString *certificate =  [self getUTF8:certificateChars];
    NSString *cuit = [self getUTF8:cuitChars];
    NSString *laboratory =  [self getUTF8:laboratoryChars];
    NSString *gtin =  [self getUTF8:gtinChars];
    NSString *troquel = [self getUTF8:troquelChars];
    NSString *comercialName = [self getUTF8:comercialNameChars];
    NSString *form = [self getUTF8:formChars];
    NSString *genericName = [self getUTF8:genericNameChars];
    NSString *country = [self getUTF8:countryChars];
    NSString *requestCondition = [self getUTF8:requestConditionChars];
    NSString *trazability = [self getUTF8:trazabilityChars];
    NSString *presentation = [self getUTF8:presentationChars];
    NSString *price = [self getUTF8:priceChars];
    
    Medicine *medicine = [[Medicine alloc] init];
    
    medicine.id = [self getFormattedValue:id];
    medicine.certificate = [self getFormattedValue:certificate];
    medicine.cuit = [self getFormattedValue:cuit];
    medicine.laboratory = [self getFormattedValue:laboratory];
    medicine.gtin = [self getFormattedValue:gtin];
    medicine.troquel = [self getFormattedValue:troquel];
    medicine.comercialName = [self getFormattedValue:comercialName];
    medicine.form = [self getFormattedValue:form];
    medicine.genericName = [self getFormattedValue:genericName];
    medicine.country = [self getFormattedValue:country];
    medicine.requestCondition = [self getFormattedValue:requestCondition];
    medicine.trazability = [self getFormattedValue:trazability];
    medicine.presentation = [self getFormattedValue:presentation];
    medicine.price = [self getFormattedPrice:price];
    
    return medicine;
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

-(NSString *) getFormattedPrice:(NSString *)value {
    if(value == nil || value.length == 0) {
        return @"$-";
    } else {
        double price = [value doubleValue];
        NSNumberFormatter *formatter = [[NSNumberFormatter alloc] init];
        
        [formatter setNumberStyle:NSNumberFormatterCurrencyStyle];
        
        return [formatter stringFromNumber:[NSNumber numberWithDouble:price]];
    }
}

-(NSString *) getVariableNumber:(int)addedConditions {
    if(addedConditions == 0) {
        return @"001";
    } else if (addedConditions == 1) {
        return @"002";
    } else {
        return @"003";
    }
}

@end
