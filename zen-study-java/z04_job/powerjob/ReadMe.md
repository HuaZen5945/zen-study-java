# power-job

## 介绍

power-job是一个分布式任务管理系统，这里可以看作阿里scheduleX的精简般。<br>
[官方文档](https://www.yuque.com/powerjob/guidence/ysug77) <br/>
[github地址](https://github.com/PowerJob/PowerJob)

## simple层

是powerjob使用的简单示例。

## mmc

mmc的简易计费编排系统，简易般的任务调度系统。配合分库分表参数，
有一定的的使用和学习价值。

### docker 启动摘抄

```shell
docker run -d \
--restart=always \
--name powerjob-server \
-p 7700:7700 -p 10086:10086 -p 10010:10010 \
-e TZ="Asia/Shanghai" \
-e JVMOPTIONS="" \
-e PARAMS="--spring.profiles.active=daily-url=jdbc:mysql://127.0.0.1:3306/powerjob-daily?useUnicode=true&characterEncoding=UTF-8 --spring.datasource.core.username=root --spring.datasource.core.password=123456" \
-v ~/docker/powerjob-server:/root/powerjob/server -v ~/.m2:/root/.m2 \
tjqq/powerjob-server:latest
```