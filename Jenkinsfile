pipeline {
	agent any

	tools {
		jdk 'jdk-25'
	}

	options {
		timestamps()
		timeout(time: 45, unit: 'MINUTES')
		disableConcurrentBuilds()
		buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '5'))
	}

	environment {
		GRADLE = './gradlew --console=plain --stacktrace'
	}

	stages {
		//stage('Hello') {
		//	steps {
		//		echo "Building ${env.JOB_NAME} #${env.BUILD_NUMBER} on ${env.NODE_NAME}"
		//		sh 'java -version'
		//		sh 'git log -1 --oneline'
		//	}
		//}
		//stage('clean workspace') {
		//	steps {
		//		cleanWs()
		//	}
		//}
		stage('Check tools') {
			steps {
				sh 'java -version'
				sh 'echo ANDROID_HOME=%ANDROID_HOME%'
				script {
					if (!env.ANDROID_HOME) {
						error 'ANDROID_HOME is not set.'
					}
				}
				sh "${GRADLE} --version"
			}
		}
		stage('Unit tests') {
			steps {
				catchError(buildResult: 'UNSTABLE', stageResult: 'UNSTABLE') {
					sh "${GRADLE} testDebugUnitTest"
				}
			}
			post {
				always {
					junit testResults: 'app/build/test-results/testDebugUnitTest/*.xml', allowEmptyResults: false
				}
			}
		}
		stage('Build debug APK') {
			steps {
				sh "${GRADLE} assembleDebug"
			}
			post {
				success {
					archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
				}
			}
		}
	}

	post {
		always {
			echo "Finished with result: ${currentBuild.currentResult}"
		}
		failure {
			echo 'Build failed: open the first red stage and read the log from the bottom.'
		}
	}
}