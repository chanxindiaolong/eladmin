FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jeecgos@163.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

WORKDIR /eladmin-system

EXPOSE 8000

ADD ./target/eladmin-system-*.jar ./app.jar

CMD sleep 60;java -Djava.security.egd=file:/dev/./urandom -jar app.jar