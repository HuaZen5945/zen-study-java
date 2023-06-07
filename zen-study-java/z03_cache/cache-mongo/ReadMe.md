## 前期准备

### 1 docker 准备环境
```shell
docker run --name mongo_base -p 27017:27017 \
-v /Users/hua/Documents/HuaAll/docker_data/mongo/base:/data/db \
-e MONGO_INITDB_ROOT_USERNAME=admin \
-e MONGO_INITDB_ROOT_PASSWORD=admin \
-d mongo
```
### 2 进行配置
```shell
# 进入容器

# 登录
mongo -u admin -p admin
# 创建数据库
use springbucks;
# 创建用户
db.createUser(
    {
      user: "springbucks",
      pwd: "springbucks",
      roles: [
         { role: "readWrite", db: "springbucks" }
      ]
    }
)
```
