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
        
        [self verifyDataBase];
    }
    
    return self;
}

- (sqlite3 *) getDataBase {
    sqlite3 *dataBase;
    NSString *databasePath = [self getDBPath];
    
    sqlite3_open([databasePath UTF8String], &dataBase);
    
    return dataBase;
}

- (void)verifyDataBase {
    BOOL needsCopy = YES;
    NSString *sourcePath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:databaseFilename];
    NSString *destinationPath = [self getDBPath];
    
    //[[NSFileManager defaultManager] removeItemAtPath:destinationPath error:nil];
    
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
    
    return [documentsDir stringByAppendingPathComponent:databaseFilename];
}

- (NSDate *) getCreationDate:(NSString *) path {
    NSDictionary* fileAttributes = [[NSFileManager defaultManager] attributesOfItemAtPath:path error:nil];
    
    return [fileAttributes objectForKey:NSFileCreationDate];
}

@end

