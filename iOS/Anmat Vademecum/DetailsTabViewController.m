//
//  DetailsTabViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "DetailsTabViewController.h"
#import "SearchResultsViewController.h"
#import "MainDetailsViewController.h"
#import "MedicineService.h"
#import "Medicine.h"

@interface DetailsTabViewController ()

@end

@implementation DetailsTabViewController

MedicineService *medicineService;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.navigationItem.title = @"Detalle";
    
    UIBarButtonItem *btnRelated = [[UIBarButtonItem alloc] init];
    UIImage *imgRelated = [UIImage imageNamed:@"Recommended"];
    
    [btnRelated setImage:imgRelated];
    [btnRelated setTarget:self];
    [btnRelated setAction:@selector(showRelated:)];
    
    self.navigationItem.rightBarButtonItem = btnRelated;
    
    if ([self respondsToSelector:@selector(edgesForExtendedLayout)]) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        self.extendedLayoutIncludesOpaqueBars = NO;
        self.automaticallyAdjustsScrollViewInsets = NO;
    }
    
    medicineService = [[MedicineService alloc] init];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

-(void) showRelated:(id) sender {
    SearchResultsViewController *searchResults = (SearchResultsViewController *) [self.storyboard instantiateViewControllerWithIdentifier:@"SearchResultsViewController"];
    
    MainDetailsViewController *mainDetails = (MainDetailsViewController *)[self.viewControllers firstObject];
    
    searchResults.medicines = [medicineService getSimilarMedicines:mainDetails.medicine];
    searchResults.title = @"Mismo Principio Activo";
    
    [self.navigationController pushViewController:searchResults animated:YES];
}

@end
