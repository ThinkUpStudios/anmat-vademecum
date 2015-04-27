//
//  UpdatesViewController.m
//  VNM
//
//  Created by mag on 4/27/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "UpdatesViewController.h"
#import "AnmatDataService.h"
#import "MenuViewController.h"
#import "DataBaseProvider.h"

@interface UpdatesViewController ()

@property (weak, nonatomic) IBOutlet UILabel *lblProgress;

@end

@implementation UpdatesViewController {
    AnmatDataService *dataService;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    dataService = [[AnmatDataService alloc] init];
    
    [dataService checkUpdateAvailable:^(BOOL existsNewUpdate, NSError *error) {
        if(existsNewUpdate) {
            [self setProgress:@"Obteniendo nueva versión..."];
            [self requestNewData];
        } else {
            [self performSegueWithIdentifier:@"ShowMenu" sender:self];
        }
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

-(BOOL)shouldAutorotate {
    return NO;
}

- (void) requestNewData {
    [dataService processNewData:^(AnmatData *data, NSError *error) {
        if(data) {
            [self setProgress:@"Información obtenida. Actualizando..."];
            
            NSData *decodedContent = [[NSData alloc] initWithBase64EncodedString:data.content options:0];
            
            if(decodedContent.length == data.contentSize) {
                [[DataBaseProvider instance] updateDataBase:decodedContent];
            }
        } else {
            [self setProgress:@"Ha ocurrido un problema al obtener la información..."];
        }
    
        [self performSegueWithIdentifier:@"ShowMenu" sender:self];
    }];
}

-(void) setProgress:(NSString *) progress {
    self.lblProgress.text = progress;
}

@end
