//
//  MedicineRepository.m
//  Anmat Vademecum
//
//  Created by mag on 3/16/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "MedicineRepository.h"
#import "sqlite3.h"
#import "Medicine.h"

@interface MedicineRepository()

@property (nonatomic, strong) NSString *databaseFilename;

@end

@implementation MedicineRepository

- (id)init {
    if ((self = [super init])) {
        self.databaseFilename = @"anmat.sqlite";

        [self verifyDataBase];
    }
    
    return self;
}

-(NSArray *) getAll {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [self getDatabase];
    NSString *query = @"SELECT * FROM medicines ORDER BY precio DESC";
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
    
    [query appendString:@"SELECT * FROM medicines WHERE "];
    
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
    sqlite3 *database = [self getDatabase];
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

-(NSArray *) getAll:(NSString *)genericName {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [self getDatabase];
    NSString *query = @"SELECT * FROM medicines WHERE generico LIKE ?001 COLLATE NOCASE ORDER BY precio DESC";
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        NSString *genericNameSearch = [NSString stringWithFormat:@"%%%@%%", genericName];
        
        sqlite3_bind_text(statement, 1, [genericNameSearch UTF8String], -1, SQLITE_STATIC);
        
        while (sqlite3_step(statement) == SQLITE_ROW) {
            Medicine *medicine = [self getMedicine:statement];
            
            [result addObject:medicine];
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

- (NSArray *) getGenericNames:(NSString *)searchText {
    NSMutableArray *result = [[NSMutableArray alloc] init];
    sqlite3 *database = [self getDatabase];
    NSString *query = @"SELECT DISTINCT generico FROM medicines WHERE generico LIKE ?001 COLLATE NOCASE";
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
    sqlite3 *database = [self getDatabase];
    NSString *query = @"SELECT DISTINCT comercial FROM medicines WHERE comercial LIKE ?001 COLLATE NOCASE";
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
    sqlite3 *database = [self getDatabase];
    NSString *query = @"SELECT DISTINCT laboratorio FROM medicines WHERE laboratorio LIKE ?001 COLLATE NOCASE";
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

-(void)verifyDataBase {
    BOOL needsCopy = YES;
    NSString *sourcePath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:self.databaseFilename];
    NSString *destinationPath = [self getDBPath];
    
    if([[NSFileManager defaultManager] fileExistsAtPath:destinationPath]) {
        NSDate *sourceCreationDate = [self getCreationDate:sourcePath];
        NSDate *destinationCreationDate = [self getCreationDate:destinationPath];
        
        if([sourceCreationDate compare:destinationCreationDate] == NSOrderedSame) {
            needsCopy = NO;
        }
    }
    
    if (needsCopy) {
        NSError *error;
        [[NSFileManager defaultManager] copyItemAtPath:sourcePath toPath:destinationPath error:&error];
        
        if (error != nil) {
            NSLog(@"%@", [error localizedDescription]);
        }
    }
}

- (NSString *) getDBPath {
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory , NSUserDomainMask, YES);
    NSString *documentsDir = [paths objectAtIndex:0];
    
    return [documentsDir stringByAppendingPathComponent:self.databaseFilename];
}

-(NSDate *) getCreationDate:(NSString *) path {
    NSDictionary* fileAttributes = [[NSFileManager defaultManager] attributesOfItemAtPath:path error:nil];
    
    return [fileAttributes objectForKey:NSFileCreationDate];
}

-(sqlite3 *) getDatabase {
    sqlite3 *database;
    NSString *databasePath = [self getDBPath];
    
    sqlite3_open([databasePath UTF8String], &database);
    
    return database;
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
    double price = sqlite3_column_double (statement, 13);
    
    NSString *id = [NSString stringWithCString:idChars encoding:NSISOLatin1StringEncoding];
    NSString *certificate =  [NSString stringWithCString:certificateChars encoding:NSISOLatin1StringEncoding];
    NSString *cuit = [NSString stringWithCString:cuitChars encoding:NSISOLatin1StringEncoding];
    NSString *laboratory =  [NSString stringWithCString:laboratoryChars encoding:NSISOLatin1StringEncoding];
    NSString *gtin =  [NSString stringWithCString:gtinChars encoding:NSISOLatin1StringEncoding];
    NSString *troquel = [NSString stringWithCString:troquelChars encoding:NSISOLatin1StringEncoding];
    NSString *comercialName = [NSString stringWithCString:comercialNameChars encoding:NSISOLatin1StringEncoding];
    NSString *form = [NSString stringWithCString:formChars encoding:NSISOLatin1StringEncoding];
    NSString *genericName = [NSString stringWithCString:genericNameChars encoding:NSISOLatin1StringEncoding];
    NSString *country = [NSString stringWithCString:countryChars encoding:NSISOLatin1StringEncoding];
    NSString *requestCondition = [NSString stringWithCString:requestConditionChars encoding:NSISOLatin1StringEncoding];
    NSString *trazability = [NSString stringWithCString:trazabilityChars encoding:NSISOLatin1StringEncoding];
    NSString *presentation = [NSString stringWithCString:presentationChars encoding:NSISOLatin1StringEncoding];
    
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
    medicine.price = price;
    
    return medicine;
}

-(NSString *) getFormattedValue:(NSString *)value {
    if(value == nil || value.length == 0) {
        return @"-";
    } else {
        return value;
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
