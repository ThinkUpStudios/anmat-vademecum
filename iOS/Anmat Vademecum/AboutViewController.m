//
//  AboutViewController.m
//  VNM
//
//  Created by mag on 4/27/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "AboutViewController.h"
#import "VersionRepository.h"

@interface AboutViewController ()

@property (weak, nonatomic) IBOutlet UILabel *lblUpdatedDate;

@end

@implementation AboutViewController {
    VersionRepository *versionRepository;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    versionRepository = [[VersionRepository alloc] init];
    
    Version *version = [versionRepository getLatestVersion];
    
    self.lblUpdatedDate.text = [NSString stringWithFormat:@"Los datos se encuentran actualizados al %@", version.date];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
