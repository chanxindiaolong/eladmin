FROM mysql:8.0.19

MAINTAINER jeecgos@163.com

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./eladmin.sql /docker-entrypoint-initdb.d
COPY ./custom.cnf /etc/mysql/conf.d/custom.cnf