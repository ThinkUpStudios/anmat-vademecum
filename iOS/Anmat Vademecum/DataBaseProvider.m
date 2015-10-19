//
//  DataBaseProvider.m
//  Anmat Vademecum
//
//  Created by mag on 3/31/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "DataBaseProvider.h"

@implementation DataBaseProvider {
    NSString *databaseFilename;
}

+ (id)instance {
    static DataBaseProvider *instance = nil;
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        instance = [[self alloc] init];
    });
    
    return instance;
}

- (id)init {
    if ((self = [super init])) {
        databaseFilename = @"anmat.sqlite";
        
        [self verifyDataBase:false];
    }
    
    return self;
}

- (sqlite3 *) getDataBase {
    sqlite3 *dataBase;
    NSString *databasePath = [self getDBPath];
    
    sqlite3_open([databasePath UTF8String], &dataBase);
    
    return dataBase;
}

-(void)updateDataBase:(NSData *)data {
    NSError *error;
     NSString *destinationPath = [self getDBPath];
    
    [[NSFileManager defaultManager] removeItemAtPath:destinationPath error:nil];
    
    if (error != nil) {
        NSLog(@"%@", [error localizedDescription]);
    }
    
    [[NSFileManager defaultManager] createFileAtPath:destinationPath contents:data attributes:nil];
}

- (void)verifyDataBase:(BOOL) overwrite {
    NSString *destinationPath = [self getDBPath];
    
    if(overwrite || ![[NSFileManager defaultManager] fileExistsAtPath:destinationPath]) {
        NSError *error;
        NSString *sourcePath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:databaseFilename];
        [[NSFileManager defaultManager] copyItemAtPath:sourcePath toPath:destinationPath error:&error];
        
        if (error != nil) {
            NSLog(@"%@", [error localizedDescription]);
        }
    }
}

- (NSString *) getDBPath {
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory , NSUserDomainMask, YES);
    NSString *documentsDir = [paths objectAtIndex:0];
    
    return [documentsDir stringByAppendingPathComponent:databaseFilename];
}

- (NSDate *) getCreationDate:(NSString *) path {
    NSDictionary* fileAttributes = [[NSFileManager defaultManager] attributesOfItemAtPath:path error:nil];
    
    return [fileAttributes objectForKey:NSFileCreationDate];
}

@end

