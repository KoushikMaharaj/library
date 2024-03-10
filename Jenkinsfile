pipeline {
    agent any
    environment {
        SONAR_HOME = tool 'SonarQubeScanner'
        MVN_HOME = tool 'maven-3.9.6'
    }
    stages {
        stage('Code') {
            steps {
                git branch: 'main', url: 'https://github.com/KoushikMaharaj/library'
                echo "Code clone successfully"
            }
        }

        stage('Test') {
            steps {
               sh '$MVN_HOME/bin/mvn -f backend/pom.xml clean test'
               echo "backend tested successfully"

            }
        }

        stage('SonarQube Analysis'){
           steps{
                withSonarQubeEnv("Sonar"){
                    sh "$SONAR_HOME/bin/sonar-scanner -Dsonar.projectName=library-backend -Dsonar.projectKey=library-backend -Dsonar.sources=backend/ -Dsonar.java.binaries=backend/target/classes"
                    sh "$SONAR_HOME/bin/sonar-scanner -Dsonar.projectName=library-fronend -Dsonar.projectKey=library-frontend -Dsonar.sources=frontend/ -Dsonar.language=js"
                    echo 'Sonar qube analysis for FE and BE is done'
                }
           }
        }

        stage('SonarQube Quality Gates'){
            steps{
                timeout(time: 1, unit: "MINUTES"){
                    waitForQualityGate abortPipeline: false
                }
                echo 'SonarQube Quality Gates check for FE and BE is done'
            }

        }
        
        stage('Java Code Build'){
            steps{
                sh '$MVN_HOME/bin/mvn -f backend/pom.xml install -DskipTests'
            }
        }

        stage('Image Build'){
            steps{
                sh 'docker build backend/ -t koushik797/library_backend:latest'
                sh 'docker build frontend/ -t koushik797/library_frontend:latest'
            }
        }

        stage('Image Push'){
            steps{
                withCredentials([usernamePassword(credentialsId:"DockerhubCreds",passwordVariable:"dockerHubPass",usernameVariable:"dockerHubUser")]){
                    sh 'docker login -u ${dockerHubUser} -p ${dockerHubPass}'
                    sh 'docker push ${dockerHubUser}/library_backend:latest'
                    sh 'docker push ${dockerHubUser}/library_frontend:latest'
                }
            }
        }
        
        stage('deploy'){
            steps{
                sh 'docker-compose down && docker-compose up -d'
            }
        }
    }
}
