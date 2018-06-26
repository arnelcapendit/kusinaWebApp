//CORES
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

//GLOBALS
import { AppConfig } from './globals/app.config';

//DEPENDENCIES
import { ChartModule } from 'angular-highcharts';
import { Adal5Service, Adal5HTTPService } from 'adal-angular5';
import { HttpClientModule, HttpClient, HttpHeaders } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CdkTableModule } from '@angular/cdk/table';
import { MyDatePickerModule } from 'mydatepicker';
import { Moment } from 'moment';
import { NguCarouselModule } from '@ngu/carousel';
import { AngularDraggableModule } from 'angular2-draggable';
import { ColorPickerModule } from 'ngx-color-picker';
import { MarkdownModule } from 'angular2-markdown';
import { AngularcliStarRatingModule } from 'angularcli-star-rating';
import { Angular2PiwikModule } from 'angular2piwik';

//SERVICES
import { MsgHandlerService } from './services/msg-handler.service';
import { SidenavService } from './services/sidenav.service';
import { DateRangePickerService } from './services/daterange.service';
import { AppProfileService } from './services/app-profile.service';
import { ChartService } from './services/chart.service';
import { CustomReportService } from './services/custom-report.service';
import { DataSourceService } from './services/datasource.service';
import { ReportCategoryService } from './services/report-category.service';
import { UserAccessService } from './services/user-access.service';
import { UserService } from './services/user.service';
import { ToolBarService } from './services/toolbar.service';
import { ProgressBarService } from './services/progressbar.service';
import { VisualizationSideNavService } from './services/custom-dashboard/visualization-sidenav.service';
import { UsageReportService } from './services/custom-reports/usage-report.service';
import { VisitorReportService } from './services/custom-reports/visitor-report.service';
import { ExportService } from './services/export.service';
import { MyteReportService } from './services/custom-reports/myte-report.service';
import { PageReportService } from './services/custom-reports/page-report.service';
import { AibspBviReportService } from './services/custom-reports/aibspbvi-report.service';
import { OverviewReportService } from './services/custom-reports/overview-report.service';
import { ActionReportService } from './services/custom-reports/action-report.service';
import { FeedbackService } from './services/feedback.service';
import { AnnouncementsService } from './services/announcement.service';
import { AuthenticationService } from './services/eso-authentication/authentication.service';
import { GlobalFilterService } from './services/global-filters/global-filters.service';
import { NotificationService } from './services/notification.service';
import { ProfileService } from './services/profile.service';
import { VisualizationService } from './services/custom-dashboard/visualization.service';
import { VisualizationBoundsService } from './services/custom-dashboard/visualization-bounds.service';
import { ItfReportService } from './services/custom-reports/itf-report.service';


//PIPES
import { ConvertToTwoDecimalPipe } from './pipes/convert-to-two-decimal';
import { ChangeToLinkPipe } from './pipes/change-to-link.pipe';
import { CountSpanTime } from './pipes/count-span-time.pipe';
import { SearchPipe } from './pipes/search.pipe';
import { ChangeToSpacePipe } from './pipes/change-to-space.pipe';

// MATERIAL
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatStepperModule,
} from '@angular/material';

// DIRECTIVES
import { FocusDirective } from './materials/date-range-picker/focus.directive';

// COMPONENTS
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { SidenavDisplayNameComponent } from './materials/sidenav-display-name/sidenav-display-name.component';
import { ProfileNameMenuComponent } from './materials/profile-name-menu/profile-name-menu.component';
import { DateRangePickerComponent } from './materials/date-range-picker/date-range-picker.component';
import { DateRangePickerDialogueComponent} from './materials/date-range-picker/date-range-picker-dialogue.component';
import { NavbarComponent } from './materials/navbar/navbar.component';
import { HighchartComponent } from './materials/highchart/highchart.component';
import { MetricComponent } from './materials/metric/metric.component';
import { ErrorComponent } from './pages/error/error.component';
import { CustomComponent } from './pages/custom/custom.component';
import { TableComponent } from './materials/table/table.component';
import { MyteClientComponent } from './materials/reports/myte-client/myte-client.component';
import { ToggleButtonComponent } from './materials/reports/toggle-button/toggle-button.component';
import { GlobalOverviewComponent } from './materials/reports/global-overview/global-overview.component';
import { GlobalUsageComponent } from './materials/reports/global-usage/global-usage.component';
import { DownloadReportComponent } from './materials/reports/download-report/download-report.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { GlobalActionsComponent } from './materials/reports/global-actions/global-actions.component';
import { GlobalVisitorsComponent } from './materials/reports/global-visitors/global-visitors.component';
import { OverviewPageMetricsComponent } from './materials/reports/global-overview/page-metrics/page-metrics.component';
import { OverviewUserMetricsComponent } from './materials/reports/global-overview/user-metrics/user-metrics.component';
import { OverviewReferrersMetricsComponent } from './materials/reports/global-overview/referrers-metrics/referrers-metrics.component';
import { OverviewDownloadMetricsComponent } from './materials/reports/global-overview/download-metrics/download-metrics.component';
import { ReferrersDetailsComponent } from './materials/reports/global-overview/referrers-metrics/details/details.component';
import { DownloadDetailsComponent } from './materials/reports/global-overview/download-metrics/details/details.component';
import { AibspClientComponent } from './materials/reports/aibsp-client/aibsp-client.component';
import { AibspPageCustomInfoComponent } from './materials/reports/aibsp-client/page-custom-info/page-custom-info.component';
import { AibspCareerLevelComponent } from './materials/reports/aibsp-client/career-level/career-level.component';
import { AibspCareerTracksComponent } from './materials/reports/aibsp-client/career-tracks/career-tracks.component';
import { AibspGeographyComponent } from './materials/reports/aibsp-client/geography/geography.component';
import { AibspHitsByAssetComponent } from './materials/reports/aibsp-client/hits-by-asset/hits-by-asset.component';
import { AibspHitsByGeographyComponent } from './materials/reports/aibsp-client/hits-by-geography/hits-by-geography.component';
import { UserAccessComponent } from './pages/settings/user-access/user-access.component';
import { ProjectPreferencesComponent } from './pages/settings/project-preferences/project.preferences.component';
import { LoaderComponent } from './materials/loader/loader.component';
import { DialogComponent } from './materials/dialog/dialog.component';
import { GlobalPagesComponent } from './materials/reports/global-pages/global-pages.component';
import { PagesVsUserDetailsComponent } from './materials/reports/global-pages/page-vs-user/details/details.component';
import { PagesVsUserComponent } from './materials/reports/global-pages/page-vs-user/page-vs-user.component';
import { PagesVsLevelComponent } from './materials/reports/global-pages/page-vs-level/page-vs-level.component';
import { PagesVsLevelDetailsComponent } from './materials/reports/global-pages/page-vs-level/details/details.component';
import { PagesVsCareertrackComponent } from './materials/reports/global-pages/page-vs-careertrack/page-vs-careertrack.component';
import { PagesVsCareertrackDetailsComponent } from './materials/reports/global-pages/page-vs-careertrack/details/details.component';
import { PagesVsGeographyComponent } from './materials/reports/global-pages/page-vs-geography/page-vs-geography.component';
import { PagesVsGeographyDetailsComponent } from './materials/reports/global-pages/page-vs-geography/details/details.component';
import { UserAddDialogComponent } from './pages/settings/user-access/user-add-dialog/user-add-dialog.component';
import { UserEditDialogComponent } from './pages/settings/user-access/user-edit-dialog/user-edit-dialog.component';
import { UserRemoveDialogComponent } from './pages/settings/user-access/user-remove-dialog/user-remove-dialog.component';
import { UsageByUserComponent } from './materials/reports/global-usage/usage-by-user/usage-by-user.component';
import { UsageByLevelComponent } from './materials/reports/global-usage/usage-by-level/usage-by-level.component';
import { UsageByLevelDetailsComponent } from './materials/reports/global-usage/usage-by-level/details/details.component';
import { UsageByCareertrackComponent } from './materials/reports/global-usage/usage-by-careertrack/usage-by-careertrack.component';
import { UsageByCareertrackDetailsComponent } from './materials/reports/global-usage/usage-by-careertrack/details/details.component';
import { UsageByGeographyComponent } from './materials/reports/global-usage/usage-by-geography/usage-by-geography.component';
import { UsageByGeographyDetailsComponent } from './materials/reports/global-usage/usage-by-geography/details/details.component';
import { TableFilterComponent } from './materials/table-filter/table-filter.component';
import { DetailsComponent } from './materials/reports/global-visitors/details/details.component';
import { EventActionsMetricsComponent } from './materials/reports/global-actions/event-actions/event-actions-component';
import { FeedbackComponent } from './materials/feedback/feedback.component';
import { FeedbacksComponent } from './pages/settings/feedbacks/feedbacks.component';
import { AnnouncementComponent } from './materials/announcement/announcement/announcement.component';
import { AnnouncementSettingsComponent } from './pages/settings/announcement-settings/announcement-settings.component';
import { AnnouncementFormComponent } from './pages/settings/announcement-settings/announcement-form/announcement-form.component';
import { LoginComponent } from './pages/login/login.component';
import { NotificationsComponent } from './pages/notifications/notifications.component';
import { NotificationDetailsComponent } from './pages/notifications/notification-details/notification-details.component';
import { AnnouncementIconComponent } from './materials/announcement/announcement-icon/announcement-icon.component';
import { SidenavComponent } from './materials/sidenav/sidenav.component';
import { OnboardingComponent } from './pages/onboarding/onboarding.component';
import { ProfileSettingsComponent } from './pages/settings/profile-settings/profile-settings.component';
import { ProfileFormsComponent } from './pages/settings/profile-settings/profile-forms/profile-forms.component';
import { UserFormComponent } from './pages/settings/user-access/user-form/user-form.component';
import { CustomDashboardComponent } from './pages/custom-dashboard/custom-dashboard.component';
import { VisualizationContainerComponent } from './materials/visualization-container/visualization-container.component';
import { VisualizationActionsComponent } from './materials/visualization-container/visualization-actions/visualization-actions.component';
import { VisualizationConfigureComponent } from './materials/visualization-container/visualization-events/visualization-configure/visualization-configure.component';
import { VisualizationAddComponent } from './materials/visualization-container/visualization-events/visualization-add/visualization-add.component';
import { VisualizationMetricFormComponent } from './materials/visualization-container/visualization-forms/visualization-metric-form/visualization-metric-form.component';
import { VisualizationCardsFormComponent } from './materials/visualization-container/visualization-forms/visualization-cards-form/visualization-cards-form.component';
import { VisualizationMarkdownFormComponent } from './materials/visualization-container/visualization-forms/visualization-markdown-form/visualization-markdown-form.component';
import { VisualizationHomeFormComponent } from './materials/visualization-container/visualization-forms/visualization-home-form/visualization-home-form.component';
import { VisualizationMetricOptionComponent } from './materials/visualization-container/visualization-options/visualization-metric-option/visualization-metric-option.component';
import { ConditionalFormattingComponent } from './materials/visualization-container/visualization-utilities/conditional-formatting/conditional-formatting.component';
import { VisualizationCardOptionComponent } from './materials/visualization-container/visualization-options/visualization-card-option/visualization-card-option.component';
import { CardDivisionComponent } from './materials/visualization-container/visualization-utilities/card-division/card-division.component';
import { VisualizationMarkdownOptionComponent } from './materials/visualization-container/visualization-options/visualization-markdown-option/visualization-markdown-option.component';
import { VisualizationHighchartOptionComponent } from './materials/visualization-container/visualization-options/visualization-highchart-option/visualization-highchart-option.component';
import { VisualizationHighchartFormComponent } from './materials/visualization-container/visualization-forms/visualization-highchart-form/visualization-highchart-form.component';
import { LoginLoadingService } from './services/eso-authentication/loading-login.service';
import { DataPrivacyComponent } from './materials/dialog/data-privacy/data-privacy.component';
import { ItfClientComponent } from './materials/reports/itf-client/itf-client.component';
import { FulfillmentComponent } from './materials/reports/itf-client/fulfillment/fulfillment.component';
import { NotificationComponent } from './materials/reports/itf-client/notification/notification.component';
import { CommentsComponent } from './materials/reports/itf-client/comments/comments.component';


@NgModule({
  entryComponents: [
    DateRangePickerComponent,
    DateRangePickerDialogueComponent,
    DialogComponent,
    VisualizationContainerComponent,
    ConditionalFormattingComponent,
    CardDivisionComponent
  ],
  declarations: [
    AppComponent,
    NotFoundComponent,
    DashboardComponent,
    SidenavDisplayNameComponent,
    ProfileNameMenuComponent,
    FocusDirective,
    DateRangePickerComponent,
    DateRangePickerDialogueComponent,
    NavbarComponent,
    HighchartComponent,
    MetricComponent,
    ErrorComponent,
    CustomComponent,
    TableComponent,
    MyteClientComponent,
    ToggleButtonComponent,
    GlobalOverviewComponent,
    GlobalUsageComponent,
    DownloadReportComponent,
    SettingsComponent,
    OverviewPageMetricsComponent,
    OverviewUserMetricsComponent,
    OverviewReferrersMetricsComponent,
    OverviewDownloadMetricsComponent,
    GlobalActionsComponent,
    GlobalVisitorsComponent,
    AibspClientComponent,
    AibspPageCustomInfoComponent,
    AibspCareerLevelComponent,
    AibspCareerTracksComponent,
    AibspGeographyComponent,
    AibspHitsByAssetComponent,
    AibspHitsByGeographyComponent,
    UserAccessComponent,
    ProjectPreferencesComponent,
    LoaderComponent,
    DialogComponent,
    GlobalPagesComponent,
    PagesVsUserDetailsComponent,
    PagesVsUserComponent,
    PagesVsLevelComponent,
    PagesVsLevelDetailsComponent,
    PagesVsCareertrackComponent,
    PagesVsCareertrackDetailsComponent,
    PagesVsGeographyComponent,
    PagesVsGeographyDetailsComponent,
    UsageByUserComponent,
    UsageByLevelComponent,
    UsageByLevelDetailsComponent,
    UsageByCareertrackComponent,
    UsageByCareertrackDetailsComponent,
    UsageByGeographyComponent,
    UsageByGeographyDetailsComponent,
    DialogComponent,
    UserAddDialogComponent,
    UserEditDialogComponent,
    UserRemoveDialogComponent,
    TableFilterComponent,
    ChangeToSpacePipe,
    ConvertToTwoDecimalPipe,
    DetailsComponent,
    ReferrersDetailsComponent,
    DownloadDetailsComponent,
    EventActionsMetricsComponent,
    FeedbacksComponent,
    FeedbackComponent,
    AnnouncementComponent,
    AnnouncementSettingsComponent,
    AnnouncementFormComponent,
    LoginComponent,
    ChangeToLinkPipe,
    CountSpanTime,
    NotificationsComponent,
    NotificationDetailsComponent,
    AnnouncementIconComponent,
    SidenavComponent,
    OnboardingComponent,
    ProfileSettingsComponent,
    ProfileFormsComponent,
    UserFormComponent,
    CustomDashboardComponent,
    VisualizationContainerComponent,
    VisualizationActionsComponent,
    VisualizationConfigureComponent,
    VisualizationAddComponent,
    VisualizationMetricFormComponent,
    VisualizationCardsFormComponent,
    VisualizationMarkdownFormComponent,
    VisualizationHomeFormComponent,
    VisualizationMetricOptionComponent,
    SearchPipe,
    ConditionalFormattingComponent,
    VisualizationCardOptionComponent,
    CardDivisionComponent,
    VisualizationMarkdownOptionComponent,
    VisualizationHighchartOptionComponent,
    VisualizationHighchartFormComponent,
    DataPrivacyComponent,
    ItfClientComponent,
    FulfillmentComponent,
    NotificationComponent,
    CommentsComponent
  ],
  imports: [
    ColorPickerModule,
    AngularDraggableModule,
    NguCarouselModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    AngularFontAwesomeModule,
    FormsModule,
    ReactiveFormsModule,
    MyDatePickerModule,
    ChartModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    AngularcliStarRatingModule,
    MarkdownModule.forRoot()
  ],
  providers: [
    VisualizationSideNavService,
    GlobalFilterService,
    AuthenticationService,
    MsgHandlerService,
    SidenavService,
    DateRangePickerService,
    AppProfileService,
    ChartService,
    CustomReportService,
    DataSourceService,
    ReportCategoryService,
    UserService,
    ToolBarService,
    ProgressBarService,
    Adal5Service,
    UserAccessService,
    UsageReportService,
    VisitorReportService,
    ExportService,
    MyteReportService,
    PageReportService,
    AibspBviReportService,
    OverviewReportService,
    ActionReportService,
    FeedbackService,
    AnnouncementsService,
    NotificationService,
    ProfileService,
    VisualizationService,
    VisualizationBoundsService,
    LoginLoadingService,
    ItfReportService,
    {
      provide: Adal5HTTPService,
      useFactory: Adal5HTTPService.factory,
      deps: [HttpClient, Adal5Service]
    },
    AppConfig,

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
