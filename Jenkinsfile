// phyllo-cicd/Jenkinsfile
@Library('phyllo-shared-lib') _   // reference shared library configured in Jenkins

pipeline {
    agent any

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
                        build job: 'builds_webapps/Web_Social_Guard'
                    }
                }
                stage('Build Connect App') {
                    steps {
                        build job: 'builds_webapps/Web_Connect_App'
                    }
                }
            }
        }
    }
}
