//
//  VersionRepository.m
//  VNM
//
//  Created by mag on 4/27/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "VersionRepository.h"
#import "DataBaseProvider.h"

@implementation VersionRepository

-(Version *)getLatestVersion {
    Version *result = nil;
    sqlite3 *database = [[DataBaseProvider instance] getDataBase];
    NSString *query = @"SELECT * FROM version LIMIT 1";
    sqlite3_stmt *statement;
    
    if (sqlite3_prepare_v2(database, [query UTF8String], -1, &statement, nil)
        == SQLITE_OK) {
        while (sqlite3_step(statement) == SQLITE_ROW) {
            result = [self getVersion:statement];
        }
        
        sqlite3_finalize(statement);
    }
    
    sqlite3_close(database);
    
    return result;
}

-(Version *) getVersion:(sqlite3_stmt *) statement {
    int versionNumber = sqlite3_column_int(statement, 0);
    char *dateChars = (char *) sqlite3_column_text(statement, 1);
    NSString *date = [self getUTF8:dateChars];
    Version *version = [[Version alloc] init];
    
    version.number = versionNumber;
    version.date = date;
    
    return version;
}

-(NSString *) getUTF8:(char *)value {
    if(value == nil) {
        return @"";
    }
    
    return [NSString stringWithUTF8String:value];
}

@end
