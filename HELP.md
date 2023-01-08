# Getting Started

### Dockerfile java8

FROM openjdk:8-jdk-alpine

ENV JAVA_OPTIONS -Xmx256m

EXPOSE 8080

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


### Docker build
docker build -t erdnando/kafka-consumer:v1 .

docker build -t erdnando/kafka-producer:v1 .

### Login access opneshift
The server is accessible via web console at:
 https://console-openshift-console.apps-crc.testing

Log in as administrator:
 Username: kubeadmin
 Password: ***********************


### Accesing registry CRC
docker login -u erdnando -p $(oc whoami -t) default-route-openshift-image-registry.apps-crc.testing                                                                      

WARNING! Using --password via the CLI is insecure. Use --password-stdin.
WARNING! Your password will be stored unencrypted in /home/erdnando/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded

### docker images   

docker images     
                                                                                                                                                          
REPOSITORY                TAG            IMAGE ID       CREATED          SIZE
erdnando/kafka-consumer   v1             6371ffdd8ecf   8 minutes ago    135MB
erdnando/kafka-producer   v1             da05d8d1aa98   10 minutes ago   135MB
<none>                    <none>         7034314c4dcf   11 minutes ago   135MB
zookeeper                 3.4.14         4b03fe5b3f64   20 months ago    260MB
openjdk                   8-jdk-alpine   a3562aa0b991   3 years ago      105MB
confluentinc/cp-kafka     5.2.1          af7cf4356c58   3 years ago      568MB

### Tagging
docker tag erdnando/kafka-consumer:v1 default-route-openshift-image-registry.apps-crc.testing/amq-streams/kafka-consumer:latest                                             
docker tag erdnando/kafka-producer:v1 default-route-openshift-image-registry.apps-crc.testing/amq-streams/kafka-producer:latest  


### Pusshing
docker push default-route-openshift-image-registry.apps-crc.testing/amq-streams/kafka-producer:latest  
                                                                        
The push refers to repository [default-route-openshift-image-registry.apps-crc.testing/amq-streams/kafka-producer]
2f9039411c69: Pushed  
ceaf9e1ebef5: Pushed  
9b9b7f3d56a0: Pushed  
f1b5933fe4b5: Pushed  
latest: digest: sha256:78e7a9a98d6c672f21907c69ad4fdb8a3d49e22103eaaba4631085cb01fb00d2 size: 1159

docker push default-route-openshift-image-registry.apps-crc.testing/amq-streams/kafka-consumer:latest  
                                                                 
The push refers to repository [default-route-openshift-image-registry.apps-crc.testing/amq-streams/kafka-consumer]
364814603654: Pushed  
ceaf9e1ebef5: Mounted from amq-streams/kafka-producer  
9b9b7f3d56a0: Mounted from amq-streams/kafka-producer  
f1b5933fe4b5: Mounted from amq-streams/kafka-producer  
latest: digest: sha256:1fe55ac9d2ebb38884e84c0792a593e8fe4689d29762d1e76d2cc12eab47ac50 size: 1159

### LS Imagestreams
oc get is                                                                                                                                                                      
NAME             IMAGE REPOSITORY                                                                     TAGS     UPDATED
kafka-consumer   default-route-openshift-image-registry.apps-crc.testing/amq-streams/kafka-consumer   latest   5 seconds ago
kafka-producer   default-route-openshift-image-registry.apps-crc.testing/amq-streams/kafka-producer   latest   33 seconds ago

### Helping to be accessible 
oc set image-lookup                                                                                                                                                            
NAME            LOCAL
kafka-consumer  false
kafka-producer  false


### New app producer

oc new-app kafka-producer --name=kafka-producer    
                                                                                                                           
--> Found image da05d8d (29 minutes old) in image stream "amq-streams/kafka-producer" under tag "latest" for "kafka-producer"


--> Creating resources ...
Warning: would violate PodSecurity "restricted:v1.24": allowPrivilegeEscalation != false (container "kafka-producer" must set securityContext.allowPrivilegeEscalation=false), unrestricted
capabilities (container "kafka-producer" must set securityContext.capabilities.drop=["ALL"]), runAsNonRoot != true (pod or container "kafka-producer" must set securityContext.runAsNonRoot=
true), seccompProfile (pod or container "kafka-producer" must set securityContext.seccompProfile.type to "RuntimeDefault" or "Localhost")
   deployment.apps "kafka-producer" created
   service "kafka-producer" created
--> Success
   Application is not exposed. You can expose services to the outside world by executing one or more of the commands below:
    'oc expose service/kafka-producer'  
   Run 'oc status' to view your app.


### New app consumer

oc new-app kafka-consumer --name=kafka-consumer      
                                                                                                                          
--> Found image 69e316f (17 minutes old) in image stream "amq-streams/kafka-consumer" under tag "latest" for "kafka-consumer"


--> Creating resources ...
Warning: would violate PodSecurity "restricted:v1.24": allowPrivilegeEscalation != false (container "kafka-consumer" must set securityContext.allowPrivilegeEscalation=false), unrestricted
capabilities (container "kafka-consumer" must set securityContext.capabilities.drop=["ALL"]), runAsNonRoot != true (pod or container "kafka-consumer" must set securityContext.runAsNonRoot=
true), seccompProfile (pod or container "kafka-consumer" must set securityContext.seccompProfile.type to "RuntimeDefault" or "Localhost")
   deployment.apps "kafka-consumer" created
   service "kafka-consumer" created
--> Success
   Application is not exposed. You can expose services to the outside world by executing one or more of the commands below:
    'oc expose service/kafka-consumer'  
   Run 'oc status' to view your app.


### Exposing services via route

oc expose service/kafka-producer    
                                                                                                                                   
route.route.openshift.io/kafka-producer exposed

oc expose service/kafka-consumer     
                                                                                                                                        
route.route.openshift.io/kafka-consumer exposed

