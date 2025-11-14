#!/usr/bin/env groovy
@Library('shared-library@main') _
jobParameters=[]

// environment
jobParameters+=[
    separator(name: "BUILD_ENVIRONMENT", sectionHeader: "Build Environment", separatorStyle: "border-width: 0",
        sectionHeaderStyle: """
        background-color: #DCDCDC;
        text-align: center;
        padding: 4px;
        color: #343434;
        font-size: 22px;
        font-weight: bold;
        text-transform: uppercase;
        font-family: 'Orienta', sans-serif;
        letter-spacing: 1px;
        font-style: italic;
        """
    ),
]
if (env.JOB_NAME.endsWith('SANDBOX_PROD')) {
    jobParameters+=[
        choice(name: 'ENVIRONMENT', choices: ['select', 'sandbox', 'prod']),
    ]
}
else {
    jobParameters+=[
        choice(name: 'ENVIRONMENT', choices: ['select', 'development', 'development2', 'development3', 'development4']),
    ]
}

//DB Migrations
jobParameters+=[
    separator(name: "DB MIGRATION", sectionHeader: "DB MIGRATION", separatorStyle: "border-width: 0",
            sectionHeaderStyle: """
            background-color: #DCDCDC;
            color: #343434;
            text-align: center;
            font-size: 22px;
            font-weight: bold;
            text-transform: uppercase;
            font-family: 'Orienta', sans-serif;
            letter-spacing: 1px;
            font-style: italic;
            """
    ),
]

if (!env.JOB_NAME.endsWith('SANDBOX_PROD')) {
    jobParameters+=[
        booleanParam(name: 'REPAIR_DB', defaultValue: "false"),
    ]
}
jobParameters+=[string(name: 'BRANCH_DB_MIGRATION', defaultValue: ""),]

if (!env.JOB_NAME.endsWith('SANDBOX_PROD')) {
    jobParameters+=[
        booleanParam(name: 'REPAIR_DB_Identity_api', defaultValue: "false"),
    ]
}
jobParameters+=[string(name: 'BRANCH_DB_MIGRATION_Identity_api', defaultValue: "", description: "Not in Sandbox"),]

if (!env.JOB_NAME.endsWith('SANDBOX_PROD')) {
    jobParameters+=[
        booleanParam(name: 'REPAIR_DB_Audit_api', defaultValue: "false"),
    ]
}
jobParameters+=[string(name: 'BRANCH_DB_MIGRATION_Audit_api', defaultValue: ""),]

if (!env.JOB_NAME.endsWith('SANDBOX_PROD')) {
    jobParameters+=[
        booleanParam(name: 'REPAIR_DB_Public_Scraper', defaultValue: "false"),
    ]
}
jobParameters+=[string(name: 'BRANCH_DB_MIGRATION_Public_Scraper', defaultValue: ""),]

jobParameters+=[
    separator(name: "API GATEWAYS", sectionHeader: "API GATEWAYS", separatorStyle: "border-width: 0",
            sectionHeaderStyle: """
            background-color: #DCDCDC;
            color: #343434;
            text-align: center;
            font-size: 22px;
            font-weight: bold;
            text-transform: uppercase;
            font-family: 'Orienta', sans-serif;
            letter-spacing: 1px;
            font-style: italic;
            """
    ),
    string(name: 'BRANCH_API_GATEWAY', defaultValue: ""),
    string(name: 'BRANCH_API_GATEWAY_IDENTITY_API', defaultValue: "", description: "Not in Sandbox"),
    //string(name: 'BRANCH_API_GATEWAY_PUBLIC_API', defaultValue: ""),
    separator(name: "Services", sectionHeader: "Services", separatorStyle: "border-width: 0",
            sectionHeaderStyle: """
            background-color: #DCDCDC;
            color: #343434;
            text-align: center;
            font-size: 22px;
            font-weight: bold;
            text-transform: uppercase;
            font-family: 'Orienta', sans-serif;
            letter-spacing: 1px;
            font-style: italic;
        """
    ),

    string(name: 'BRANCH_CONNECT_API', defaultValue: ""),
    string(name: 'BRANCH_CONNECT_INTERNAL_API', defaultValue: ""),
    string(name: 'BRANCH_API_SCRAPER', defaultValue: ""),
    string(name: 'BRANCH_APP_CONNECT_LOGIN', defaultValue: ""),
    string(name: 'BRANCH_AUDIT_API', defaultValue: ""),
    string(name: 'BRANCH_BILLING_CONSUMER', defaultValue: ""),
    string(name: 'BRANCH_BROWSER_LOGIN_CONSUMER', defaultValue: ""),
    string(name: 'BRANCH_BROWSER_SCRAPER', defaultValue: ""),
    string(name: 'BRANCH_BROWSER_SCRAPER_API', defaultValue: ""),
    string(name: 'BRANCH_BULK_CREATOR_INVITE_CONSUMER', defaultValue: "", description: "Not in Sandbox"),
    string(name: 'BRANCH_WEBHOOK_CONSUMER', defaultValue: ""),
    string(name: 'BRANCH_EVENT_PROCESSOR', defaultValue: ""),
    string(name: 'BRANCH_EXTERNAL_CALLBACK_API', defaultValue: ""),
    string(name: 'BRANCH_FILE_STORAGE_CONSUMER', defaultValue: ""),
    string(name: 'BRANCH_IDENTITY_API', defaultValue: "", description: "Not in Sandbox"),
    string(name: 'BRANCH_NARAD_API', defaultValue: ""),
    string(name: 'BRANCH_WEBHOOK_ANALYSIS_CONSUMER', defaultValue: ""),
    string(name: 'BRANCH_PUBLISH_API', defaultValue: ""),
    string(name: 'BRANCH_PCA_COOKIE_UPDATE_CONSUMER', defaultValue: ""),
    string(name: 'BRANCH_PUBLIC_SCRAPER', defaultValue: ""),
    string(name: 'BRANCH_PUBLIC_SCRAPER_API', defaultValue: ""),
    string(name: 'BRANCH_RESPONSE_SCHEMA_COMPARISON_CONSUMER', defaultValue: ""),
    string(name: 'BRANCH_SCHEDULER_API', defaultValue: ""),
    string(name: 'BRANCH_SCHEDULER_PROCESSOR', defaultValue: ""),
    string(name: 'BRANCH_SCRAPER_INTERNAL_LOGIN_CONSUMER', defaultValue: ""),
    string(name: 'BRANCH_SPIDER_API', defaultValue: ""),
    string(name: 'BRANCH_TENANT_API', defaultValue: "")
]

properties([
    parameters(jobParameters)
])

pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '40'))
        disableConcurrentBuilds()
        timeout(time: 1, unit: 'HOURS')
        timestamps()
    }

    stages {

        stage('PRE_CONDITION') {
            steps {
                script {
                    if (params.ENVIRONMENT == 'select') {
                        currentBuild.result = 'ABORTED'
                        error("Please select a Enviornment!!")
                    }
                    BUILD_TRIGGER_BY = "${currentBuild.getBuildCauses()[0].userId}"
                    currentBuild.displayName = "#${params.ENVIRONMENT}, ${BUILD_TRIGGER_BY}"
                }
            }
        }

        stage('DB_MIGRATION') {
            steps {
                script {
                    if (BRANCH_DB_MIGRATION) {
                        def results = build job: "backend_build_jobs/Db_migration", wait: true, parameters: [
                            string(name: 'BRANCH', value: "${params.BRANCH_DB_MIGRATION}"),
                            booleanParam(name: 'REPAIR_DB', value: "${params.REPAIR_DB}"),
                            string(name: 'ORG', value: "phyllo"),
                            string(name: 'ENVIRONMENT', value: env.environment)]
                    }
                }
            }
        }

        stage('DB_MIGRATION_Identity_API') {
            steps {
                script {
                    if (BRANCH_DB_MIGRATION_Identity_api && env.environment != 'sandbox') {
                        def results = build job: "backend_build_jobs/DB_Migration_Identity_Api", wait: true, parameters: [
                                string(name: 'BRANCH', value: "${params.BRANCH_DB_MIGRATION_Identity_api}"),
                                booleanParam(name: 'REPAIR_DB', value: "${params.REPAIR_DB_Identity_api}"),
                                string(name: 'ORG', value: "phyllo"),
                                string(name: 'ENVIRONMENT', value: env.environment)]
                    }
                }
            }
        }

        stage('DB_MIGRATION_Audit_api') {
            steps {
                script {
                    if (BRANCH_DB_MIGRATION_Audit_api) {
                        def results = build job: "backend_build_jobs/DB_migration_audit_api", wait: true, parameters: [
                                string(name: 'BRANCH', value: "${params.BRANCH_DB_MIGRATION_Audit_api}"),
                                booleanParam(name: 'REPAIR_DB', value: "${params.REPAIR_DB_Audit_api}"),
                                string(name: 'ORG', value: "phyllo"),
                                string(name: 'ENVIRONMENT', value: env.environment)]
                    }
                }
            }
        }

        stage('DB_MIGRATION_Public_Scraper') {
            steps {
                script {
                    if (BRANCH_DB_MIGRATION_Public_Scraper) {
                        def results = build job: "backend_build_jobs/DB_migration_public_scraper", wait: true, parameters: [
                                string(name: 'BRANCH', value: "${params.BRANCH_DB_MIGRATION_Public_Scraper}"),
                                booleanParam(name: 'REPAIR_DB', value: "${params.REPAIR_DB_Public_Scraper}"),
                                string(name: 'ORG', value: "phyllo"),
                                string(name: 'ENVIRONMENT', value: env.environment)]
                    }
                }
            }
        }

        stage('API_Gateway_OpenAPI') {
            steps {
                script {
                    if (BRANCH_API_GATEWAY) {
                        def results = build job: "backend_build_jobs/Deploy_API_Gateway_Openapi", wait: true, parameters: [
                            string(name: 'BRANCH', value: "${params.BRANCH_API_GATEWAY}"),
                            string(name: 'ORG', value: "phyllo"),
                            string(name: 'ENVIRONMENT', value: env.environment)]
                    }
                }
            }
        }

        stage('API_Gateway_OpenAPI_Identity_API') {
            steps {
                script {
                    if (BRANCH_API_GATEWAY_IDENTITY_API  && env.environment != 'sandbox') {
                        def results = build job: "backend_build_jobs/Deploy_API_Gateway_Openapi_Identity_API", wait: true, parameters: [
                            string(name: 'BRANCH', value: "${params.BRANCH_API_GATEWAY_IDENTITY_API}"),
                            string(name: 'ORG', value: "phyllo"),
                            string(name: 'ENVIRONMENT', value: env.environment)]
                    }
                }
            }
        }

        //stage('API_Gateway_OpenAPI_Public_API') {
        //    steps {
        //        script {
        //            if (BRANCH_API_GATEWAY_PUBLIC_API) {
        //                def results = build job: "backend_build_jobs/Deploy_API_Gateway_Public_API", wait: true, parameters: [
        //                    string(name: 'BRANCH', value: "${params.BRANCH_API_GATEWAY_PUBLIC_API}"),
        //                    string(name: 'ORG', value: "phyllo"),
        //                    string(name: 'ENVIRONMENT', value: env.environment)]
        //            }
        //        }
        //    }
        //}

        stage('BUILD') {
            parallel {
                stage('CONNECT_API') {
                    steps {
                        script {
                            if (BRANCH_CONNECT_API) {
                                commonDiscovery("phyllo", env.environment, "connect-api", params.BRANCH_CONNECT_API)
                            }
                        }
                    }
                }
                stage('CONNECT_INTERNAL_API') {
                    steps {
                        script {
                           if (BRANCH_CONNECT_INTERNAL_API) {
                                commonDiscovery("phyllo", env.environment, "connect-internal-api", params.BRANCH_CONNECT_INTERNAL_API)
                            }
                        }
                    }
                }
                stage('API_SCRAPER') {
                    steps {
                        script {
                            if (BRANCH_API_SCRAPER) {
                                commonDiscovery("phyllo", env.environment, "api-scraper", params.BRANCH_API_SCRAPER)
                            }
                        }
                    }
                }
                stage('APP_CONNECT_LOGIN') {
                    steps {
                        script {
                            if (BRANCH_APP_CONNECT_LOGIN) {
                                commonDiscovery("phyllo", env.environment, "app-connect-login", params.BRANCH_APP_CONNECT_LOGIN)
                            }
                        }
                    }
                }
                stage('AUDIT_API') {
                    steps {
                        script {
                            if (BRANCH_AUDIT_API) {
                                commonDiscovery("phyllo", env.environment, "audit-api", params.BRANCH_AUDIT_API)
                            }
                        }
                    }
                }
                stage('BILLING_CONSUMER') {
                    steps {
                        script {
                            if (BRANCH_BILLING_CONSUMER) {
                                commonDiscovery("phyllo", env.environment, "billing-consumer", params.BRANCH_BILLING_CONSUMER)
                            }
                        }
                    }
                }
                stage('BROWSER_LOGIN_CONSUSMER') {
                    steps {
                        script {
                            if (BRANCH_BROWSER_LOGIN_CONSUMER) {
                                commonDiscovery("phyllo", env.environment, "browser-login-consumer", params.BRANCH_BROWSER_LOGIN_CONSUMER)
                            }
                        }
                    }
                }
                stage('BROWSER_SCRAPER') {
                    steps {
                        script {
                            if (BRANCH_BROWSER_SCRAPER) {
                                commonDiscovery("phyllo", env.environment, "browser-scraper", params.BRANCH_BROWSER_SCRAPER)
                            }
                        }
                    }
                }
                stage('BROWSER_SCRAPER_API') {
                    steps {
                        script {
                            if (BRANCH_BROWSER_SCRAPER_API) {
                                commonDiscovery("phyllo", env.environment, "browser-scraper-api", params.BRANCH_BROWSER_SCRAPER_API)
                            }
                        }
                    }
                }
                stage('BULK_CREATOR_INVITE_CONSUMER_IDENTITY_API') {
                    steps {
                        script {
                            if (BRANCH_BULK_CREATOR_INVITE_CONSUMER) {
                                commonDiscovery("phyllo", env.environment, "bulk-creator-invite-consumer", params.BRANCH_BULK_CREATOR_INVITE_CONSUMER)
                            }
                        }
                    }
                }
                stage('WEBHOOK_CONSUMER') {
                    steps {
                        script {
                            if (BRANCH_WEBHOOK_CONSUMER) {
                                commonDiscovery("phyllo", env.environment, "webhook-consumer", params.BRANCH_WEBHOOK_CONSUMER)
                            }
                        }
                    }
                }
                stage('EVENT_PROCESSOR') {
                    steps {
                        script {
                            if (BRANCH_EVENT_PROCESSOR) {
                                commonDiscovery("phyllo", env.environment, "event-processor", params.BRANCH_EVENT_PROCESSOR)
                            }
                        }
                    }
                }
                stage('EXTERNAL_CALLBACK_API') {
                    steps {
                        script {
                            if (BRANCH_EXTERNAL_CALLBACK_API) {
                                commonDiscovery("phyllo", env.environment, "external-callback-api", params.BRANCH_EXTERNAL_CALLBACK_API)
                            }
                        }
                    }
                }
                stage('FILE_STORAGE_CONSUMER') {
                    steps {
                        script {
                            if (BRANCH_FILE_STORAGE_CONSUMER) {
                                commonDiscovery("phyllo", env.environment, "file-storage-consumer", params.BRANCH_FILE_STORAGE_CONSUMER)
                            }
                        }
                    }
                }
                stage('IDENTITY_API') {
                    steps {
                        script {
                            if (BRANCH_IDENTITY_API) {
                                commonDiscovery("phyllo", env.environment, "identity-api", params.BRANCH_IDENTITY_API)
                            }
                        }
                    }
                }
                stage('NARAD_API') {
                    steps {
                        script {
                            if (BRANCH_NARAD_API) {
                                commonDiscovery("phyllo", env.environment, "narad-api", params.BRANCH_NARAD_API)
                            }
                        }
                    }
                }
                stage('WEBHOOK_ANALYSIS_CONSUMER') {
                    steps {
                        script {
                            if (BRANCH_WEBHOOK_ANALYSIS_CONSUMER) {
                                commonDiscovery("phyllo", env.environment, "webhook-analysis-consumer", params.BRANCH_WEBHOOK_ANALYSIS_CONSUMER)
                            }
                        }
                    }
                }
                stage('PUBLISH_API') {
                    steps {
                        script {
                            if (BRANCH_PUBLISH_API) {
                                commonDiscovery("phyllo", env.environment, "publish-api", params.BRANCH_PUBLISH_API)
                            }
                        }
                    }
                }
                stage('PCA_COOKIE_UPDATE_CONSUMER') {
                    steps {
                        script {
                            if (BRANCH_PCA_COOKIE_UPDATE_CONSUMER) {
                                commonDiscovery("phyllo", env.environment, "pca-cookies-update-consumer", params.BRANCH_PCA_COOKIE_UPDATE_CONSUMER)
                            }
                        }
                    }
                }
                stage('PUBLIC_SCRAPER') {
                    steps {
                        script {
                            if (BRANCH_PUBLIC_SCRAPER) {
                                commonDiscovery("phyllo", env.environment, "public-scraper", params.BRANCH_PUBLIC_SCRAPER)
                            }
                        }
                    }
                }
                stage('PUBLIC_SCRAPER_API') {
                    steps {
                        script {
                            if (BRANCH_PUBLIC_SCRAPER_API) {
                                commonDiscovery("phyllo", env.environment, "public-scraper-api", params.BRANCH_PUBLIC_SCRAPER_API)
                            }
                        }
                    }
                }
                stage('RESPONSE_SCHEMA_COMPARISON_CONSUMER') {
                    steps {
                        script {
                            if (BRANCH_RESPONSE_SCHEMA_COMPARISON_CONSUMER) {
                                commonDiscovery("phyllo", env.environment, "response-schema-comparison-consumer", params.BRANCH_RESPONSE_SCHEMA_COMPARISON_CONSUMER)
                            }
                        }
                    }
                }
                stage('SCHEDULER_API') {
                    steps {
                        script {
                            if (BRANCH_SCHEDULER_API) {
                                commonDiscovery("phyllo", env.environment, "scheduler-api", params.BRANCH_SCHEDULER_API)
                            }
                        }
                    }
                }
                stage('SCHEDULER_PROCESSOR') {
                    steps {
                        script {
                            if (BRANCH_SCHEDULER_PROCESSOR) {
                                commonDiscovery("phyllo", env.environment, "scheduler-processor", params.BRANCH_SCHEDULER_PROCESSOR)
                            }
                        }
                    }
                }
                stage('SCRAPER_INTERNAL_LOGIN_CONSUMER') {
                    steps {
                        script {
                            if (BRANCH_SCRAPER_INTERNAL_LOGIN_CONSUMER) {
                                commonDiscovery("phyllo", env.environment, "scraper-internal-login-consumer", params.BRANCH_SCRAPER_INTERNAL_LOGIN_CONSUMER)
                            }
                        }
                    }
                }
                stage('SPIDER_API') {
                    steps {
                        script {
                            if (BRANCH_SPIDER_API) {
                                commonDiscovery("phyllo", env.environment, "spider-api", params.BRANCH_SPIDER_API)
                            }
                        }
                    }
                }
                stage('TENANT_API') {
                    steps {
                        script {
                            if (BRANCH_TENANT_API) {
                                commonDiscovery("phyllo", env.environment, "tenant-api", params.BRANCH_TENANT_API)
                            }
                        }
                    }
                }
            }
        }
    }

}

