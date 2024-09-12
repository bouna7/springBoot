pipeline {
    agent any
     stages {
        stage('Build') {
            steps {
                sh '/usr/share/maven/bin/mvn clean install'
            }
        }
         stage('Deployer') {
            steps {
                echo 'Hello, Maven'
                sh 'mvn --version'
            }
        }
        stage('Test') {
            steps {
                sh '/usr/share/maven/bin/mvn test'

            }
        }
    }
}
