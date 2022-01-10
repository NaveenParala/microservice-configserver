#Start with a base image containing java runtime
FROM  openjdk:8

#information around who maintain the image
MAINTAINER wba.com

#add the applicaation's jar to the container
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar
 
#execute the application
ENTRYPOINT ["java","-jar","/accounts-0.0.1-SNAPSHOT.jar"]  