
# Prerequisites 
    
    1. Install Intellij / Eclipse IDE
    2. Install Mysql version 8.0 and setup your user
    3. Install Java version 16
    4. In application.properties, please change the 
        - spring.datasource.username
        - spring.datasource.password
    4. Run com/example/user/UserServiceApplication.java
    5. If using docker, install latest docker (Optional)

Your server should be up and running in http://localhost:8080/user-service

# Steps to run if using docker

    1. Uncomment the below lines in application.properties 
        #spring.datasource.url=jdbc:mysql://mysql_db:3306/user-service?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false
        #spring.datasource.username=mysql
    2. docker compose build
    3. docker compose up

Your server should be up and running in http://localhost:8080/user-service

# API documentation 
https://documenter.getpostman.com/view/14639616/UVXerczf
