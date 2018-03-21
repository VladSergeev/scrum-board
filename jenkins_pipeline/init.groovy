pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'jdk8'

    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build') {
            steps {
                sh 'mvn -T2 clean package'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
        stage('Deploy') {
            steps {
//                NOT WORKING!
//                sshagent (['61589553-6037-43c4-b308-6d3090c17007']) {
//                    sh '''
//                        cd /jenkins/data/workspace/scrum-server+1/docker
//                        docker build -t 185.185.68.238/startup .
//                        docker push 185.185.68.238/startup
//                        docker-compose pull
//                        docker-compose up -d
//                    '''
//
//                }
            }
        }
    }
}