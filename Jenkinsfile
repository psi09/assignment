pipeline
{
    parameters 
    { 
        string(name: 'branch_name', defaultValue: 'QA', description: '');
        string(name: 'service', defaultValue: 'rest-service', description: 'test service');
        
    }
    agent any
    environment
    {
        TAG = "${params.branch_name}"
        service_name = "${params.service}"
    }
stages
    {
    
    stage('Checkout')
        {
            steps
            {    
                echo "Checking out the branch : ${params.branch_name}"
                input 'Are you sure?'
                git branch: "${TAG}",credentialsId: 'bitbucket-auth',url: "git@github.com:psi09/${params.service_name}.git"
                
             }
        }

 

    stage('Build')
        {
            steps
            {   
                echo "build"
                sh '''
                source /etc/environment
                mvn clean install -DskipTests
                '''
             }
        }
    stage('jacoco and sonar publsih')
        {
            steps
            {   
                echo "build"
                sh '''
                source /etc/environment
                which mvn
                mvn sonar:sonar -Dserver.host=http://localhost:9000 -Dsonar.projectKey="${params.service_name}"
                '''
             }
        }
    stage('Create ECR')
        {
            steps
            {
               
                    // Create the docker registory for a given service name
                    echo "creating ss ECR for ${params.service_name}"
                    // Some more steps
            }
        }
     stage('Docker build')
        {
            steps
            {
                script
                {
                    // Build the docker image using a Dockerfile
                    docker.build("$IMAGE",".")
                    sh("docker tag '$LATEST' $ECRURL")
                }
            }
        }
      stage('Docker push')
        {
            steps
            {
                script
                {
                    // Push the Docker image to ECR
                 }
             }
         }
         stage ('Deploying to Environment')
         {
            steps
            {
                
                    //deploying build to kubernetes
                    echo "Deployment started"
                    sh '''
                    
                    kubectl create ns philips
                    kubectl run $PROJECT --replicas=1 --labels=run=$PROJECT --image=$ECR --port=8080 --env="$SPRING" -n philips
                    # More steps to configure it, skiiping for now..
                    '''
                
          }
    }
    }
}