## 运行 Mybatis Generator
[使用参考](https://blog.csdn.net/qq_48581332/article/details/123674440)

### 命令行运行
```shell
java -jar mybatis-generator-core-x.x.x.jar -configfile generatorConfig.xml
```

### Maven Plugin
1. pom文件中插件配置
2. mybatis核心配置文件配置
3. 使用mybatis插件执行命令
```shell
mvn mybatis-generator:generate
${basedir}/src/main/resources/generatorConfig.xml
```