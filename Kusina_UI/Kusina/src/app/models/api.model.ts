export interface EventMetricsWrapper {
    EventMetrics: EventMetricsList;
}

export interface EventMetricsList {
    resultset: EventMetricsObject[];
}

export interface EventMetricsObject {
    EVENT_CATEGORIES: string;
    EVENT_ACTION: string;
    EVENT_ACTION_COUNT: number;
    EVENT_NAME: string;
    EVENT_CATEGORIES_COUNT: number;
    EVENT_EVENT_COUNT: number;
}

// User Access Models
export interface UsersList {
    total: number;
    allusers: Users[];
}

export interface Users {
    EID: string;
    ACCESS: string;
    ID: number;
    AIRID: number;
    EXPIRY_DATE: any;
    STATUS: string;
    CREATED_DATE: any;
    LAST_UPDATED_DATE: any;
    LAST_UPDATED_BY: string;
}

export interface ProjectPreferencesList {
    preferences: ProjectPreferences[];
}

export interface ProjectPreferences {
    air_id: string;
    default_dashboard_id: number;
    start_date: string;
    end_date: string;
    timezone: string;
    interval: number;
    theme: string;
    default_results_count: number;
}

// Usage Report Model
export interface UsageData {
    total: number;
    userUsage: UsageReport[];
    careerLevelDescriptionUsage: UsageReport[];
    careerTracksUsage: UsageReport[];
    geographyUsage: UsageReport[];
}

export interface UsageReport {
    USERNAME: string;
    VISITS: number;
    AVG_VISIT_DURATION: number;
    AVG_PAGES_PER_VISIT: number;
    HITS: number;
    CAREERLEVELDESCRIPTION: string;
    CAREERLEVEL: string;
    CAREERTRACKS: string;
    CAREERTRACKDESCRIPTION: string;
    GEOGRAPHY: string;
    COUNTRY: string;
}

// MyTE Reports Model
export interface MytePageVsCountryList {
    total: string;
    MyTEReports: MytePageVsCountry[];
}

export interface MytePageVsCountry {
    PAGE_URL: string;
    COUNTRY: string;
    AVG_GENERATION_TIME: number;
}

// Visitor Logs Model
export interface VisitorLogList {
    total: number;
    VisitorLogMetrics: VisitorLogMetrics[];
}

export interface VisitorLogMetrics {
    VISITS: string;
    LAST_ACTION_TIME: string;
    USER: string;
    ADDRESS: string;
    COUNTRY: string;
}

export interface VisitorLogMetricsDetails {
    BROWSER: string;
    LAST_ACTION_TIME: string;
    CLEAN_PAGEURL: string;
    PAGE_TITLE: string;
    OS: string;
}

// Pages Report Model
export interface PagesData {
    total: number;
    userPages: PagesReport[];
    careerLevelPages: PagesReport[];
    careerTracksPages: PagesReport[];
    geographyPages: PagesReport[];
}

export interface PagesReport {
    PAGE_URL: string;
    VISITS: number;
    AVG_VISIT_DURATION: number;
    AVG_PAGES_PER_VISIT: number;
    HITS: number;
    USERNAME: string;
    CAREER_LEVEL: number;
    CAREER_TRACKS: string;
    GEOGRAPHY: string;
}


//AibspBvi Report Model
export interface AibspBviData {
    total: number;
    pageCustomInfo: AibspBviReport[];
    careerTracksBySegmentName: AibspBviReport[];
    careerLevelDescriptionBySegmentName: AibspBviReport[];
    geographyBySegmentName: AibspBviReport[];
    hitsByGeography: AibspBviReport[];
    hitsByAsset: AibspBviReport[];
}

export interface AibspBviReport {
    SEGMENT_NAME: string;
    UNIQUE_VISITOR_COUNT: number;
    PAGE_URL: string;
    ACCESS_DATE_TIME: string;
    USERNAME: string;
    BROWSER: string;
    OS: string;
    GEOGRAPHY: string;
    ASSET_NAME: string;
    DEVICE: number;
    ASSET_TYPE: string;
    INDUSTRY_NAME: string;
    CAREER_TRACKS: string;
    CAREER_LEVEL: number;
    HITS: number;
} 

//Overview Report Model
export interface OverviewData{
    total: number;
    pageOverview: OverviewReport[];
    userOverview: OverviewReport[];
    referrerOverview: OverviewReport[];
    downloadOverview: OverviewReport[];
    downloadOverviewChild : OverviewReport[];
    referrerOverviewChild : OverviewReport[];
}

export interface OverviewReport{
    PAGE_URL: string;
    VISITS: number;
    UNIQUE_VISITORS: number;
    PAGEVIEWS: number;
    USERNAME: string;
    CAREER_LEVEL: number;
    CAREER_TRACKS: string;
    GEOGRAPHY: string;
    COUNTRY: string;
    REFERRER_URL: string;
    AVG_VISIT: number;
    AVG_VISIT_DURATION: number;
    AVG_PAGES_PER_VISIT: number;
    HITS: number;
}

//Events Report Model
export interface EventsData{
    total: number;
    categoryEvents: EventsReport[];
    actionEvents: EventsReport[];
    nameEvents: EventsReport[];
}

export interface EventsReport{
    EVENT_CATEGORIES: string;
    EVENT_ACTION: string;
    EVENT_NAME: string;
    TOTAL_EVENTS: number;
    TOTAL_VALUE: number;
}

//Feedbacks Report Model
export interface FeedbacksData{
    total: number;
    feedbacks: FeedbacksReport[];
}

export interface FeedbacksReport{
    EID: string;
    AIRID: number;
    ID: number;
    RATING_MODULE: string;
    RATING_SCORE: number;
    COMMENT: string;
    STATUS: string;
    CREATED_DATE: string;
    LAST_UPDATE_DATE: string;
    LAST_UPDATE_BY: string;
}

export interface NotifCount {
    total: number;
}


export interface AnnouncementsData {
    total: number;
    announcements: AnnouncementsReport[];
}

export interface AnnouncementsReport {
    TYPE: string;
    DUE_DATE: string;
    TITLE: string;
    CONTENT: string;
    STATUS: string;
    TOTAL_READ_COUNT: number;
}

export interface AnnouncementsBeforeDueDateData {
    total: number;
    announcements: AnnouncementsBeforeDueDateReport[];
}

export interface AnnouncementsBeforeDueDateReport {
    TYPE: string;
    DUE_DATE: string;
    TITLE: string;
    CONTENT: string;
    STATUS: string;
    CREATED_DATE: string;
}

export interface AnnouncementsLiveData {
    total: number;
    announcements: AnnouncementsLiveReport[];
}

export interface AnnouncementsLiveReport {
    ID: string;
    TYPE: string;
    DUE_DATE: string;
    TITLE: string;
    CONTENT: string;
    STATUS: string;
    CREATED_DATE: string;
    LAST_UPDATE_DATE: string;
    LAST_UPDATE_BY: string;
    TOTAL_READ_COUNT: string;
    READ_STATUS: string;
}

export interface HistoryId {
    id: string;
    status: string;
}

export interface ProfileData {
    total: number;
    profile: ProfileReport[];
    site_id: ProfileReport[];
}

export interface ProfileReport {
    AIRID: string;
    SITE_ID: string;
    APP_NAME: string;
    CREATED_DATE: string;
    LAST_UPDATE_DATE: string;
    LAST_UPDATE_BY: string;
}


export interface AppProfile{
    AppNameList: any;
}

//ITF Model
export interface ITFData {
    total: number;
    itfFulfillment: ITFReport[];
    itfNotification: ITFReport[];
    itfComments: ITFReport[];
}

export interface ITFReport {
    USERNAME: string;
    APPLICATION: string;
    USER_ROLE: string;
    GEOGRAPHY: string;
    COUNTRY: string;
    CAREER_LEVEL: string;
    CAREER_TRACKS: string;
    VISITS: string;
    HITS: string;
    TOTAL_TIME_SPENT: string;
}