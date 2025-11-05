@Library('shared-library') _

pipeline {
    agent any

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Select target environment')
        multiSelect(name: 'WEB_APPS', choices: [
            'Web_Connect_App',
            'Web_Social_Guard'
        ], description: 'Select which apps to build & deploy')
    }

    stages {
        stage('PRE_CHECK') {
            steps {
                script {
                    preCheck(params.ENVIRONMENT)
                }
            }
        }

        stage('BUILD_AND_DEPLOY') {
            steps {
                script {
                    def apps = params.WEB_APPS.tokenize(',')
                    def parallelJobs = [:]

                    for (app in apps) {
                        parallelJobs[app] = {
                            echo "🧱 Triggering job for ${app}"
                            build job: "builds_webapps/${app}", parameters: [
                                string(name: 'ENVIRONMENT', value: params.ENVIRONMENT)
                            ]
                        }
                    }

                    parallel parallelJobs
                }
            }
        }
    }

    post {
        success {
            echo "✅ All applications built and deployed successfully!"
        }
        failure {
            echo "❌ One or more builds failed. Check logs for details."
        }
    }
}
