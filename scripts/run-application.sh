sudo pkill -6 java
source /home/ubuntu/.env
nohup java -jar -Dserver.port=8080 -Dspring.profiles.active=prod /home/ubuntu/Demar/build/libs/*.jar > /dev/null 2> /dev/null < /dev/null &