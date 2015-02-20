//
//  Medicine.h
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Medicine : NSObject

@property int id;
@property NSString *certificate;
@property NSString *cuit;
@property NSString *laboratory;
@property NSString *gtin;
@property NSString *troquel;
@property NSString *comercialName;
@property NSString *genericName;
@property NSString *form;
@property NSString *country;
@property NSString *requestCondition;
@property NSString *trazability;
@property NSString *presentation;
@property double price;

@end
