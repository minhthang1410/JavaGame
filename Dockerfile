FROM tomcat:8.5.83-jdk8

ADD ./target/JavaCBJS-1.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
