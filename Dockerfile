FROM java:8
LABEL maintainer=“mathiarasan.k@gmail.com”
VOLUME /tmp
EXPOSE 8080
ADD target/hrmgmt-0.0.1-SNAPSHOT.jar hrmgmt-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","hrmgmt-0.0.1-SNAPSHOT.jar"]