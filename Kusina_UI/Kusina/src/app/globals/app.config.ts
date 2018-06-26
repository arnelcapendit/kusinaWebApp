import { Injectable, OnInit } from '@angular/core';
import * as moment from 'moment';
@Injectable()
export class AppConfig {
    /**SAMPLE */
    SAMPLE_CONFIG = {
        COLUMNS: {
            POSTS: ['userId', 'id', 'title', 'body', 'button'],
        },
        REPORTS: {
            POSTS: 'POSTS',
        },
        COLUMN_TITLE: {
            POSTS: ['userId', 'id', 'title', 'body'],
        },
    };

    /**Snackbar Duration */
    SNACKBAR = {
        SNACKBAR_DURATION: 2000,
    };

    /** Report Type **/
    REPORT_TYPE = {
        VISITOR_LOGS: 'visitorLogs',
        MYTE_PAGEVSCOUNTRIES: 'myTE',
        PAGES_BY_USER: 'userPages',
        PAGES_BY_LEVEL: 'careerLevelPages',
        PAGES_BY_CAREERTRACKS: 'careerTracksPages',
        PAGES_BY_GEOGRAPHY: 'geographyPages',
        USAGE_BY_USER: 'userUsage',
        USAGE_BY_LEVEL: 'careerLevelDescriptionUsage',
        USAGE_BY_CAREERTRACKS: 'careerTracksUsage',
        USAGE_BY_GEOGRAPHY: 'geographyUsage',
        PAGES_CUSTOM_INFO: 'pageCustomInfo',
        HITS_BY_GEOGRAPHY: 'hitsByGeography',
        HITS_BY_ASSET: 'hitsByAsset',
        GEOGRAPHY_BY_SEGMENT_NAME: 'geographyBySegmentName',
        CAREERLEVEL_BY_SEGMENT_NAME: 'careerLevelDescriptionBySegmentName',
        CAREERTRACKS_BY_SEGMENTNAME: 'careerTracksBySegmentName',
        OVERVIEW_USERMETRICS: 'userOverview',
        OVERVIEW_PAGEMETRICS: 'pageOverview',
        OVERVIEW_REFERRERSMETRICS: 'referrerOverview',
        OVERVIEW_DOWNLOADMETRICS: 'downloadOverview',
        OVERVIEW_REFERRERSMETRICS_DETAILS: 'referrerOverviewChild',
        OVERVIEW_DOWNLOADMETRICS_DETAILS: 'downloadOverviewChild',
        EVENT_ACTIONS: 'actionEvents',
        EVENT_CATEGORIES: 'categoryEvents',
        EVENT_NAMES: 'nameEvents',
        USER_ACCESS: 'users',
        FEEDBACKS: 'feedbacks',
        ANNOUNCEMENTS: 'announce',
        NOTIFICATIONS: 'announcelive',
        PROFILE: 'profile',
        FULFILLMENT: 'itfFulfillment',
        NOTIFICATION: 'itfNotification',
        COMMENTS: 'itfComments'

    };

    /** Filter **/
    FILTER = {
        PAGES_BY_USER: 'user',
        PAGES_BY_LEVEL: 'careerLevel',
        PAGES_BY_CAREERTRACKS: 'careerTracks',
        PAGES_BY_GEOGRAPHY: 'geography',
        USAGE_BY_USER: 'user',
        USAGE_BY_LEVEL: 'careerLevelDescription',
        USAGE_BY_CAREERTRACKS: 'careerTracks',
        USAGE_BY_GEOGRAPHY: 'geography',
        PAGES_CUSTOM_INFO: 'page url',
        HITS_BY_GEOGRAPHY: 'segment name',
        HITS_BY_ASSET: 'segment name',
        GEOGRAPHY_BY_SEGMENT_NAME: 'segment name',
        CAREERLEVEL_BY_SEGMENT_NAME: 'segment name',
        CAREERTRACKS_BY_SEGMENTNAME: 'segment name',
        OVERVIEW_USERMETRICS: 'user',
        OVERVIEW_PAGEMETRICS: 'page url',
        OVERVIEW_REFERRERSMETRICS: 'referrers name',
        OVERVIEW_DOWNLOADMETRICS: 'page url',
        OVERVIEW_REFERRERSMETRICS_DETAILS: 'user',
        OVERVIEW_DOWNLOADMETRICS_DETAILS: 'user',
        EVENT_CATEGORIES: 'event categories',
        EVENT_ACTIONS: 'event actions',
        EVENT_NAMES: 'event names',
        USER_ACCESS: 'user',
        FEEDBACKS: 'feedbacks',
        ANNOUNCEMENTS: 'announce',
        NOTIFICATIONS: 'announcelive',
        PROFILE: 'profile'
    };

    /** Filter Metrics **/
    FILTER_METRICS = {
        USAGE_BY_LEVEL: 'careerLevel',
        USAGE_BY_CAREERTRACKS: 'careerTrackDescription',
        USAGE_BY_GEOGRAPHY: 'country'
    };

    /** Search Placeholder */
    SEARCH_PLACEHOLDER = {
        USAGE_BY_USER : 'username',
        USAGE_BY_LEVEL: 'career level description',
        USAGE_BY_LEVEL_DETAILS: 'career level',
        USAGE_BY_CAREERTRACKS: 'careertracks',
        USAGE_BY_CAREERTRACKS_DETAILS: 'careertracks description',
        USAGE_BY_GEOGRAPHY: 'geography',
        USAGE_BY_GEOGRAPHY_DETAILS: 'country',
        VISITORLOGS: 'visitor',
        MYTE_PAGEVSCOUNTRIES: 'page url and country',
        PAGES_BY_USER : 'page url',
        PAGES_BY_USER_DETAILS : 'username',
        PAGES_BY_LEVEL: 'page url',
        PAGES_BY_LEVEL_DETAILS: 'career level',
        PAGES_BY_CAREERTRACKS: 'page url',
        PAGES_BY_CAREERTRACKS_DETAILS: 'careertracks',
        PAGES_BY_GEOGRAPHY: 'page url',
        PAGES_BY_GEOGRAPHY_DETAILS: 'geography',
        PAGES_CUSTOM_INFO: 'field names',
        HITS_BY_GEOGRAPHY: 'segment name',
        HITS_BY_ASSET: 'segment name',
        GEOGRAPHY_BY_SEGMENT_NAME: 'segment name',
        CAREERLEVEL_BY_SEGMENT_NAME: 'segment name and career level ',
        CAREERTRACKS_BY_SEGMENTNAME: 'segment name careertracks and industry name',
        OVERVIEW_USERMETRICS: 'user name, career level, careertracks, geography and country',
        OVERVIEW_PAGEMETRICS: 'page url',
        OVERVIEW_REFERRERSMETRICS: 'referrers url',
        OVERVIEW_REFERRERSMETRICS_DETAILS: 'user',
        OVERVIEW_DOWNLOADMETRICS: 'page url',
        OVERVIEW_DOWNLOADMETRICS_DETAILS: 'user',
        EVENT_ACTIONS: 'event actions',
        USER_ACCESS: 'field names',
        FEEDBACKS: 'field names',
        ANNOUNCEMENTS: 'field names',
        PROFILE: 'field names',
        NOTIFICATIONS: 'field names',
        ITF: 'field names'
    };

    /**ESO Configuration */
    ESO_CONFIG = {
        LOCAL: {
            clientId: '3817.piwik.kusina.beta.web',
            instance: 'https://federation-sts-stage.accenture.com/oauth/ls/connect/authorize',
            redirect_uri: 'http://localhost:4200',
            scope: 'openid profile',
            responseType: 'id_token'
        },
        DEV: {
            clientId: '3817.piwik.kusina.beta.web',
            instance: 'https://federation-sts-stage.accenture.com/oauth/ls/connect/authorize',
            redirect_uri: 'https://inframetrics.ciostage.accenture.com/beta/',
            scope: 'openid profile',
            responseType: 'id_token'
        },
        STAGING: {
            clientId: '3817.piwik.kusina.beta.web',
            instance: 'https://federation-sts-stage.accenture.com/oauth/ls/connect/authorize',
            redirect_uri: 'https://inframetrics.ciostage.accenture.com/piwik/',
            scope: 'openid profile',
            responseType: 'id_token'
        },
        STAGING_IP: {
            clientId: '3817.piwik.kusina.beta.web',
            instance: 'https://federation-sts-stage.accenture.com/oauth/ls/connect/authorize',
            redirect_uri: 'http://10.69.42.9/piwik/',
            scope: 'openid profile',
            responseType: 'id_token'
        },
        PRODUCTION: {
            clientId: '3817.piwik.kusina.beta.web',
            instance: 'https://federation-sts.accenture.com/oauth/ls/connect/authorize',
            redirect_uri: 'https://inframetrics.accenture.com/piwik/',
            scope: 'openid profile',
            responseType: 'id_token'
        }
    };


    /** HTTP */
    HTTP_PARAMS= {
        HEADER: {
            'Content-Type': 'application/json; charset=utf-8',
            'Accept': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Head': '*'
        },
        PARAMETERS: {
            PARAM_EID: 'eid',
            PARAM_NONCE: 'nonce',
            PARAM_AUTHTIME: 'authtime',
            PARAM_EXP: 'exp',
            PARAM_TIMENOW: 'now',
            PARAM_AIRID: 'airid',
            PARAM_GTE: 'gte',
            PARAM_LTE: 'lte',
            PARAM_ID: 'id',
            PARAM_NOW: 'now',
            PARAM_CUSTRPTTYPE: 'reportType',
            PARAM_PAGEURL: 'pageurl',
            PARAM_EIDINSERT: 'eid2',
            PARAM_ACCESS: 'access',
            PARAM_EXPDATE: 'expiry',
            PARAM_ACCESSSTATUS: 'status',
            PARAM_CREATEDDATE: 'createdDate',
            PARAM_UPDATEDDATE: 'updatedDate',
            PARAM_UPDATEDBY: 'updatedBy',
            PARAM_EIDDELETE: 'eidDelete',
            PARAM_EIDSEARCH: 'eidSearch',
            PARAM_FILTERFOR: 'filterFor',
            PARAM_FILTERMETRICS: 'filterMetrics',
            PARAM_FROM: 'from',
            PARAM_SIZE: 'size',
            PARAM_SEARCH: 'search',
            PARAM_FILTER: 'filter',
            PARAM_RATING_MODULE: 'ratingModule',
            PARAM_RATING_SCORE: 'ratingScore',
            PARAM_RATING_COMMENTS: 'ratingComment',
            PARAM_STATUS: 'status',
            PARAM_TYPE: 'type',
            PARAM_DUE_DATE: 'dueDate',
            PARAM_TITLE: 'title',
            PARAM_CONTENT: 'content',
            PARAM_DOCID: 'docId',
            PARAM_ACTION_TYPE: 'actionType',
            PARAM_SITE_ID: 'idSite',
            PARAM_APP_NAME: 'appName',
            PARAM_TEAM: 'team',
            PARAM_TIMEZONE: 'timezone',
        }
    };

    /** HTTP */
    HTTP_CONFIG= {
        HEADER: {
            'Content-Type': 'application/json; charset=utf-8',
            'Accept': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Head': '*'
        },
        PARAMETERS: {
            PARAM_EID: 'eid=',
            PARAM_NONCE: 'nonce=',
            PARAM_AUTHTIME: 'authtime=',
            PARAM_EXP: 'exp=',
            PARAM_TIMENOW: 'now=',
            PARAM_AIRID: 'airid=',
            PARAM_GTE: 'gte=',
            PARAM_LTE: 'lte=',
            PARAM_ID: 'id=',
            PARAM_NOW: 'now',
            PARAM_CUSTRPTTYPE: 'reportType=',
            PARAM_PAGEURL: 'pageurl=',
            PARAM_EIDINSERT: 'eid2=',
            PARAM_ACCESS: 'access=',
            PARAM_EXPDATE: 'expiry=',
            PARAM_ACCESSSTATUS: 'status=',
            PARAM_CREATEDDATE: 'createdDate=',
            PARAM_UPDATEDDATE: 'updatedDate=',
            PARAM_UPDATEDBY: 'updatedBy=',
            PARAM_EIDDELETE: 'eidDelete=',
            PARAM_EIDSEARCH: 'eidSearch=',
            PARAM_FILTERFOR: 'filterFor=',
            PARAM_FILTERMETRICS: 'filterMetrics=',
            PARAM_FILTER: 'filter=',
            PARAM_TEAM: 'team=',
        }
    };
    /**EXPORT */
    EXPORT_TYPE= {
        PAGE_METRICS: 'pageMetrics',
        USER_METRICS: 'userMetrics',
        REFERRERS_METRICS: 'referrersMetrics',
        DOWNLOAD_METRICS: 'downloadMetrics',
        USER_USAGE: 'userUsage',
        VISITOR_LOG: 'visitorLogs',
        EVENT_ACTION: 'events'
    };

    /** Webserver */
    WEB_SERVER = {
        LOCAL : 'http://localhost:8080/beta/rest',
        DEV: 'https://inframetrics.ciostage.accenture.com/tomcat/beta/rest',
        STAGING: 'https://inframetrics.ciostage.accenture.com/tomcat/kusina/rest',
        STAGING_IP: 'http://10.69.42.9/tomcat/kusina/rest',
        STAGING43: 'http://10.69.41.101:8080/kusina/rest',
        STAGING44: 'http://inframetrics.ciostage.accenture.com:8080/kusina/rest/',
        PROD: 'https://inframetrics.accenture.com/tomcat/kusina/rest'
    };

    /** Tracker */
    TRACKER_PAGE = {
        LOCAL : 'http://localhost:4200',
        DEV: 'https://inframetrics.ciostage.accenture.com/beta',
        STAGING: 'https://inframetrics.ciostage.accenture.com/piwik',
        PROD: 'https://inframetrics.accenture.com/piwik'
    };

    /** Pages */
    WEB_PAGES = {
        ROOT_PAGE: '/',
        DASHBOARD_PAGE: '/dashboard',
        ERROR_PAGE: '/error',
        CUSTOMREPORT_PAGE: '/custom',
        SERVICEDOWN: '/servicedown',
        LOGOUTPAGE: '/logoutpage',
        LOGINPAGE: '/loginpage',
        APIDOWN: '/servicedown',
        LOGOUT: '/logoutpage',
        SETTINGS: '/settings'
    };

    ES_MAPPING = {
        GEOGRAPHY: 'Geography',
        EID: 'Username',
        CAREERLEVEL: 'CareerLevel',
        VISITS: 'Visits',
        PAGEURL: 'PageURL',
        HITS: 'Hits',
        USER: 'User'
    };

    COLUMN_MAPPING = {
        OVERVIEW_PAGE_METRICS: [
            this.ES_MAPPING.PAGEURL
            ,   this.ES_MAPPING.VISITS
            ,       this.ES_MAPPING.USER
            ,           this.ES_MAPPING.HITS
        ]
    };


    /** COLUMNS */
    COLUMNS = {
        MYTE_PAGEVSCOUNTRIES: ['PAGE_URL', 'COUNTRY', 'AVG_GENERATION_TIME'],
        OVERVIEWPAGEMETRICS: ['PAGE_URL', 'VISITS', 'UNIQUE_VISITORS', 'PAGEVIEWS'],
        OVERVIEWUSERMETRICS: ['USERNAME', 'CAREER_LEVEL', 'CAREER_TRACKS', 'GEOGRAPHY', 'COUNTRY', 'VISITS'],
        OVERVIEWREFERRERSMETRICS: ['VIEW_USER_NAME', 'REFERRER_URL', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        OVERVIEWREFERRERSMETRICSDETAILS: ['USER', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        OVERVIEWDOWNLOADMETRICS: ['VIEW_USER', 'PAGE_URL', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        OVERVIEWDOWNLOADMETRICSDETAILS: ['USER', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        // USAGEBYUSER: ['USERNAME', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        EVENTACTIONS:['EVENT_CATEGORIES','EVENT_ACTION','TOTAL_EVENTS'],
        VISITORS: ['VIEW_ACTION', 'LAST_ACTION_TIME', 'USER', 'ADDRESS', 'COUNTRY'],
        VISITORSDETAILS: ['PAGE_TITLE', 'CLEAN_PAGEURL', 'BROWSER', 'OS', 'LAST_ACTION_TIME'],
        PAGESCUSTOMINFO: [
            'PAGE_URL',
            'ACCESS_DATE_TIME',
            'USER_ID',
            'BROWSER',
            'OS',
            'GEOGRAPHY',
            'ASSET_NAME',
            'DEVICE',
            'ASSET_TYPE',
            'SEGMENT_NAME',
            'INDUSTRY_NAME',
            'CAREER_TRACKS',
            'CAREER_LEVEL'
        ],
        GEOGRAPHYBYSEGMENTNAME: ['SEGMENT_NAME', 'GEOGRAPHY', 'UNIQUE_VISITOR_COUNT'],
        CAREERLEVELBYSEGMENTNAME: ['SEGMENT_NAME', 'CAREER_LEVEL', 'UNIQUE_VISITOR_COUNT'],
        CAREERTRACKSBYSEGMENTNAME: ['SEGMENT_NAME', 'CAREER_TRACKS', 'INDUSTRY_NAME', 'HITS', 'UNIQUE_VISITOR_COUNT'],
        HITSBYGEOGRAPHY: ['SEGMENT_NAME', 'GEOGRAPHY', 'HITS'],
        HITSBYASSET: ['SEGMENT_NAME', 'INDUSTRY_NAME', 'ASSET_TYPE', 'HITS'],
        PAGESVSUSER: ['VIEW_USER', 'PAGE_URL', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        DETAILSPAGESVSUSER: ['USERNAME', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        PAGESVSGEOGRAPHY: ['VIEW_GEOGRAPHY', 'PAGE_URL', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        DETAILSPAGESVSGEOGRAPHY: ['GEOGRAPHY', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        PAGESVSCAREERTRACKS: ['VIEW_CAREER_TRACKS', 'PAGE_URL', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        DETAILSPAGESVSCAREERTRACKS: ['CAREER_TRACKS', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        PAGESVSLEVEL: ['VIEW_CAREER_LEVEL', 'PAGE_URL', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        DETAILSPAGESVSLEVEL: ['CAREER_LEVEL', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],

        USAGEBYSUSER: ['USERNAME', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        USAGEBYSGEOGRAPHY: ['VIEW_COUNTRY', 'GEOGRAPHY', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        DETAILSUSAGEBYSGEOGRAPHY: ['COUNTRY', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        USAGEBYSCAREERTRACKS: ['VIEW_CAREERTRACKS', 'CAREERTRACKS', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        DETAILSUSAGEBYSCAREERTRACKS: ['CAREERTRACK_DESCRIPTION', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        USAGEBYSLEVEL: ['VIEW_LEVEL', 'CAREER_LEVEL_DESCRIPTION', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        DETAILSUSAGEBYSLEVEL: ['CAREER_LEVEL', 'VISITS', 'AVG_VISIT_DURATION', 'AVG_PAGES_PER_VISIT', 'HITS'],
        USERACCESS: [
            'EID',
            'TEAM',
            'ACCESS',
            'ID',
            'AIRID',
            'EXPIRY_DATE',
            'STATUS',
            'CREATED_DATE',
            'LAST_UPDATED_DATE',
            'LAST_UPDATED_BY',
            'USER_ACTIONS'
        ],
        PROJECTPREFERENCES: [
          'air_id',
          'default_dashboard_id',
          'start_date',
          'end_date',
          'timezone',
          'interval',
          'theme',
          'default_results_count'
        ],
        FEEDBACKS: [
            'EID',
            'AIRID',
            'ID',
            'RATING_MODULE',
            'RATING_SCORE',
            'COMMENT',
            'STATUS',
            'CREATED_DATE'
        ],
        ANNOUNCEMENT: [
            'TYPE',
            'DUE_DATE',
            'TITLE',
            'STATUS',
            'TOTAL_READ_COUNT',
            'CREATED_DATE',
            'LAST_UPDATE_DATE',
            'LAST_UPDATE_BY',
            'ACTION'
        ],
        NOTIFICATIONS: [
            'TITLE',
            'TYPE',
            'CREATED_DATE'
        ],
        PROFILE: [
            'AIRID',
            'SITE_ID',
            'APP_NAME',
            'CREATED_DATE',
            'LAST_UPDATE_DATE',
            'LAST_UPDATE_BY',
            'ACTIONS'
        ],
        ITF: [
            'USERNAME',
            'APPLICATION',
            'USER_ROLE',
            'GEOGRAPHY',
            'COUNTRY',
            'CAREER_LEVEL',
            'CAREER_TRACKS',
            'VISITS',
            'HITS',
            'TOTAL_TIME_SPENT'
        ]
    };
    /** CUSTOM REPORTS */
    CUSTOM_REPORTS = {
        CATEGORIES_SPECIAL: ['CLIENT', 'OVERVIEW', 'PAGES', 'USAGE', 'VISITORS', 'ACTIONS'],
        CATEGORIES_GLOBAL: ['OVERVIEW', 'PAGES', 'USAGE', 'VISITORS', 'ACTIONS'],
        CATEGORY_ITEMS: {
            CLIENT: 'CLIENT',
            OVERVIEW: 'OVERVIEW',
            USAGE: 'USAGE',
            PAGES: 'PAGES',
            VISITORS: 'VISITORS',
            ACTIONS: 'ACTIONS'
        },
        REPORTS: {
            PAGE_VS_COUNTRIES: 'PAGE VS COUNTRIES',
            PAGE_METRICS: 'OVERVIEW PAGE METRICS',
            OVERVIEW_ARRAY_TAB: ['PAGES METRICS', 'USER METRICS', 'REFERRERS METRICS', 'DOWNLOAD METRICS'],
            USAGE_BY_USER: 'USAGE BY USER',
            PAGES_VS_USER: 'PAGE VS USER',
            DETAILS_PAGES_VS_USER: 'DETAILS PAGE VS USER',
            EVENTS: ['EVENT ACTIONS'],
            VISITORS: 'VISITOR LOGS',
            VISITORSDETAILS: 'VISITOR LOGS DETAILS',
            PAGES_ARRAY_TAB: ['PAGES VS USER', 'PAGES VS LEVEL', 'PAGES VS CAREERTRACKS', 'PAGES VS GEOGRAPHY'],
            USAGE_ARRAY_TAB: ['USAGE BY USER', 'USAGE BY LEVEL', 'USAGE BY CAREERTRACKS', 'USAGE BY GEOGRAPHY'],
            AIBSP_ARRAY_TAB: [
                'PAGES CUSTOM INFO',
                'GEOGRAPHY BY SEGMENT NAME',
                'CAREERLEVEL BY SEGMENT NAME',
                'CAREERTRACKS BY SEGMENTNAME',
                'HITS BY GEOGRAPHY',
                'HITS BY ASSET'
            ],
            ITF_ARRAY_TAB: [
                'FULFILLMENT CONNECT USAGE',
                'NOTIFICATION COUNT',
                'COMMENTS CLICK COUNT'
            ]
        },
        OVERVIEW_TAB: {
            PAGE_METRICS: 'PAGES METRICS',
            USER_METRICS: 'USER METRICS',
            REFERRERS_METRICS: 'REFERRERS METRICS',
            DETAILS_REFERRERS_METRICS: 'DETAILS REFERRERS METRICS',
            DOWNLOAD_METRICS: 'DOWNLOAD METRICS',
            DETAILS_DOWNLOAD_METRICS: 'DETAILS DOWNLOAD METRICS'
        },
        AIBSP_TAB: {
            PAGES_CUSTOM_INFO: 'PAGES CUSTOM INFO',
            GEOGRAPHY_BY_SEGMENT_NAME: 'GEOGRAPHY BY SEGMENT NAME',
            CAREERLEVEL_BY_SEGMENT_NAME: 'CAREERLEVEL BY SEGMENT NAME',
            CAREERTRACKS_BY_SEGMENTNAME: 'CAREERTRACKS BY SEGMENTNAME',
            HITS_BY_GEOGRAPHY: 'HITS BY GEOGRAPHY',
            HITS_BY_ASSET: 'HITS BY ASSET'
        },
        AIBSP_REPORT_TYPE: {
            hitsByAsset: 'hitsByAsset',
            hitsByGeography: 'hitsByGeography',
            geographyBySegmentName: 'geographyBySegmentName',
            careerLevelBySegmentName: 'careerLevelBySegmentName',
            pageCustomInfo: 'pageCustomInfo',
            careerTracksBySegmentName: 'careerTracksBySegmentName'
        },
        PAGES_TAB: {
            PAGES_VS_USER: 'PAGES VS USER',
            DETAILS_PAGES_VS_USER: 'DETAILS PAGE VS USER',
            PAGES_VS_LEVEL: 'PAGES VS LEVEL',
            DETAILS_PAGES_VS_LEVEL: 'DETAILS PAGES VS LEVEL',
            PAGES_VS_CAREERTRACKS: 'PAGES VS CAREERTRACKS',
            DETAILS_PAGES_VS_CAREERTRACKS: 'DETAILS PAGES VS CAREERTRACKS',
            PAGES_VS_GEOGRAPHY: 'PAGES VS GEOGRAPHY',
            DETAILS_PAGES_VS_GEOGRAPHY: 'DETAILS PAGES VS GEOGRAPHY',
        },
        USAGE_TAB: {
            USAGE_BY_USER: 'USAGE BY USER',
            DETAILS_USAGE_BY_USER: 'DETAILS USAGE BY USER',
            USAGE_BY_LEVEL: 'USAGE BY LEVEL',
            DETAILS_USAGE_BY_LEVEL: 'DETAILS USAGE BY LEVEL',
            USAGE_BY_CAREERTRACKS: 'USAGE BY CAREERTRACKS',
            DETAILS_USAGE_BY_CAREERTRACKS: 'DETAILS USAGE BY CAREERTRACKS',
            USAGE_BY_GEOGRAPHY: 'USAGE BY GEOGRAPHY',
            DETAILS_USAGE_BY_GEOGRAPHY: 'DETAILS USAGE BY GEOGRAPHY',
        },
        EVENTS_TAB: {
            EVENT_CATEGORIES: 'EVENT_CATEGORIES',
            EVENT_ACTIONS: 'EVENT_ACTIONS',
            EVENT_NAMES: 'EVENT_NAMES'
        },
        ITF_TAB: {
            FULFILLMENT_CONNECT_USAGE: 'FULFILLMENT CONNECT USAGE',
            NOTIFICATION_COUNT: 'NOTIFICATION COUNT',
            COMMENTS_CLICK_COUNT: 'COMMENTS CLICK COUNT'
        }

    };

    NOTIFICATIONS = {
        NOTIFICATION: 'NOTIFICATIONS'
    };

    SETTINGS = {
        SETTINGS_TAB: {
            USER_ACCESS: 'USER ACCESS',
            FEEDBACKS: 'FEEDBACKS',
            ANNOUNCEMENTS: 'ANNOUNCEMENTS',
            PROFILE: 'PROFILE',
            PROJECT_PREFERENCES: 'PROJECT PREFERENCES',
            SETTINGS_ARRAY_TAB: ['USER ACCESS', 'FEEDBACKS', 'ANNOUNCEMENTS']
            // SETTINGS_ARRAY_TAB: ['USER ACCESS', 'PROJECT PREFERENCES']
        }
    };

    FEEDBACKS = {
        FEEDBACKS: 'FEEDBACKS',
        FEEDBACKS_ARRAY_TAB: ['FEEDBACKS']
    };

    SPECIAL_CLIENTS = {
        AIRID_LIST: [ '2700', '4202', '4766', '5238'],
        MYTE_AIRID:'2700',
        AIBSP:'4202',
        BVI:'4766',
        ITF:'5238'
    };

    /** PARAMETERS */
    PARAMETERS = {
        CUSTRPTTYPE: 'reportType',
        APPNAME: 'appName',
        LTE: 'lte',
        GTE: 'gte',
        NOW: 'now',
        APPID: 'id',
        AIRID: 'airid',
        PAGEURL: 'pageURL',
        ACTION: 'action'
    };

    /** DATERANGEPICKER */
    dateNow = new Date();
    DATEPICKEROPTIONS= {
        CALENDARFORMAT: 'mm/dd/yyyy',
        MOMENTFORMAT: 'L',
        INLINE: true,
        SHOWINPUTFIELD: true,
        SELECTORHEIGHT: '200px',
        SELECTORWIDTH: '200px'
    };

    DATERANGEPICKER = {
        PERIODS: {
            DAY: 'Day',
            CUSTOM: 'Custom'
        },
        DEFAULT_DATE: {
            date: {
                year: this.dateNow.getFullYear(),
                month: new Date().getMonth() + 1,
                day: this.dateNow.getDate()
            },
            epoc: this.dateNow.getTime() / 1000,
            formatted: moment(this.dateNow).format(this.DATEPICKEROPTIONS.MOMENTFORMAT)
        }
    };
    /**PIWIK */
    PIWIK_ENVIRONMENTS = {
        STAGING: {URL:"//webmetrics.ciostage.accenture.com/",ID:937},
        PRODUCTION: {URL:"//webtag.accenture.com/", ID:3}
    }

    /** URI */
    private ACTIVE_SERVER: string = this.WEB_SERVER.DEV;
    private SET_TRACKER: string = this.TRACKER_PAGE.DEV;
    private NOREST: string = this.getActiveServerWithOutRest();

    init(server:string):void{
        this.ACTIVE_SERVER = server;
        this.NOREST = this.getActiveServerWithOutRest();
    }

    getActiveServer():string{
        return this.ACTIVE_SERVER;
    }

    getActiveTracker(): string {
        return this.SET_TRACKER;
    }

    getActiveServerWithOutRest():string{
        return this.ACTIVE_SERVER.substring(0,this.ACTIVE_SERVER.indexOf('rest')-1);
    }

    URI = {
        getSessionEidURI: this.getActiveServer()+'/getSessionEid?eid=',
        setGetSessionEidURI:this.getActiveServer()+'/setGetSessionEid?eid=', 
        setPostSessionEidURI:this.getActiveServer()+'/setPostSessionEid', 
        getUserByEid: this.getActiveServer()+'/piwik'+'/getUserByEid?eid=',
        getAppSettings: this.getActiveServer()+'/getAppSettings',
        getAppPreferences: this.getActiveServer()+'/getAppPreferences?',
        getAllDataByEid: this.getActiveServer()+'/dashboard_onload?eid=', 
        getAllDataByParams: this.getActiveServer()+'/dashboard?', 
        saveUserSessionDetails: this.getActiveServerWithOutRest()+'/saveUserSession?',
        destroySession: this.getActiveServer() +'/logout?',
        getCustomReport: this.getActiveServer() + '/custom_reports?',
        getCustomRptOverviewPage: this.getActiveServer() + '/custom_reports/overview/pagemetrics?',
        getCustomRptOverviewUser: this.getActiveServer() + '/custom_reports/overview/usermetrics?',
        getCustomRptOverviewReferrer: this.getActiveServer() + '/custom_reports/overview/referrersmetrics?',
        getCustomRptOverviewDownload: this.getActiveServer() + '/custom_reports/overview/downloadmetrics?',
        getOverview: this.getActiveServer() + '/custom_reports/overview?',
        getCustomRptUsage: this.getActiveServer() + '/custom_reports/usageV3?',
        getCustomRptUsageChild: this.getActiveServer() + '/custom_reports/usage_child?',
        getCustomRptUsageDetails: this.getActiveServer() + '/custom_reports/usage_details?',
        getCustomRptMyTeOnload: this.getActiveServer() + '/custom_reports/onload/myTe?',
        getCustomRptMyTe: this.getActiveServer() + '/custom_reports/myTe?',
        getCustomRptMyTeV2: this.getActiveServer() + '/custom_reports/myTeV2?',
        getCustomEventAction: this.getActiveServer() + '/custom_reports/event_metrics?',
        getCustomEvents: this.getActiveServer() + '/custom_reports/events?',
        getCustomVisitors: this.getActiveServer() + '/visitorLogsV2?',
        getCustomVisitors3: this.getActiveServer() + '/visitorLogsV3?',
        getCustomVisitorsChild: this.getActiveServer() + '/visitorLogs_child?',
        exportCustomReportToCsv: this.getActiveServer() + '/export/customReports?',
        exportMyTeToCsv: this.getActiveServer() + '/export/myTe?',
        exportVisualizationToCsv: this.getActiveServer() + '/export/visualization?',
        getAibsp: this.getActiveServer() + '/custom_reports/?',
        getAibspBvi: this.getActiveServer() + '/custom_reports/aibspbvi?',
        getPageVsUser: this.getActiveServer() + '/custom_reports/pagesV2?',
        getPageVsUser2: this.getActiveServer() + '/custom_reports/pagesV3?',
        getPagesChild: this.getActiveServer() + '/custom_reports/pages_child?',
        getPageVsUserDetails: this.getActiveServer() + '/custom_reports/pages_details?',
        getUserAccess: this.getActiveServer() + '/getUserAccess?',
        getAllUsers: this.getActiveServer() + '/getAllUsers?',
        getUsers: this.getActiveServer() + '/getUsers',
        getUser: this.getActiveServer() + '/getSingleUserByEid?',
        insertUserDetails: this.getActiveServer() + '/insertUserDetails?',
        editUserDetails: this.getActiveServer() + '/updateUserDetails?',
        deleteUserDetails: this.getActiveServer() + '/deleteUserDetails?',
        exportToCsv: this.getActiveServer() + '/export/new/customReports?',
        addFeedbacks: this.getActiveServer() + '/addFeedback?',
        getFeedbacks: this.getActiveServer() + '/feedbacks?',
        getAnnouncements: this.getActiveServer() + '/announce?',

        addAnnouncements: this.getActiveServer() + '/add_announcement?',
        editAnnouncements: this.getActiveServer() + '/edit_announcement?',
        deleteAnnouncements: this.getActiveServer() + '/delete_announcement?',

        notifications: this.getActiveServer() + '/notifications?',
        getAnnouncementsBeforeDueDate: this.getActiveServer() + '/all/announce_duedate?',
        postReadAction: this.getActiveServer() + '/histories?',
        getAllAnnounceLive: this.getActiveServer() + '/announce/live?',
        getHistoryId: this.getActiveServer() + '/historyId?',
        deleteHistory: this.getActiveServer() + '/delete/histories?',

        getAppProfile: this.getActiveServer() + '/profile?',
        addAppProfile: this.getActiveServer() + '/add_profile?',
        editAppProfile: this.getActiveServer() + '/edit_profile?',
        deleteAppProfile: this.getActiveServer() + '/delete_profile?',

        getAllId: this.getActiveServer() + '/get_all_airid?',
        getSiteId: this.getActiveServer() + '/get_site_id?',

        getDataByEid: this.getActiveServer()+'/dashboard_onload?',
        getCustomReportITF: this.getActiveServer()+'/custom_reports/itf?'
    };


}
