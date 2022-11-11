sudo pkill -6 java
source /home/ubuntu/.env
SPRING_PROFILES_ACTIVE=production java -jar /home/ubuntu/demar-server/build/libs/*.jar 1>>/home/ubuntu/log/spring-log.log 2>>/home/ubuntu/log/spring-error.log &