// phyllo-cicd/Jenkinsfile
@Library('phyllo-shared-lib') _   // reference shared library configured in Jenkins

pipeline {
    agent any

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Select environment to build and deploy')
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Git branch to build')
    }

    stages {
        stage('Pre Checks') {
            steps {
                script {
                    preCheck()
                }
            }
        }

        stage('Build All Webapps') {
            parallel {
                stage('Build Social Guard') {
                    steps {
                        script {
                            build job: 'builds_webapps/Web_Social_Guard',
                                parameters: [
                                    string(name: 'ENVIRONMENT', value: params.ENVIRONMENT),
                                    string(name: 'BRANCH_NAME', value: params.BRANCH_NAME)
                                ],
                                wait: true,
                                propagate: true
                        }
                    }
                }
                stage('Build Connect App') {
                    steps {
                        script {
                            build job: 'builds_webapps/Web_Connect_App',
                                parameters: [
                                    string(name: 'ENVIRONMENT', value: params.ENVIRONMENT),
                                    string(name: 'BRANCH_NAME', value: params.BRANCH_NAME)
                                ],
                                wait: true,
                                propagate: true
                        }
                    }
                }
            }
        }
    }
}
