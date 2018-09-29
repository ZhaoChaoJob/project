# 一、项目名称【一站式web框架】
# 二、项目特点
1. 牛逼

# 三、目宗旨
1. 打造一款轻量级的web架构
2. 支持集群部署
3. 支持tokenId登录

# 四项目资料
## （1）、包含义
com.geotmt ===> 基础路径 \
admin ==> 权限管理部分 \
commons ==> 基础工具包 \
config ==> 配置管理 \
generator ==> 代码生成器 \
project ==> 业务包

## （2）、模块说明
### 1.commonModule 公共模块
- 存放mybatis的dao、mapping、model模块
- 存放各种工具类

### 2.dubboApiModulede dubbo API
- dubbo所有的暴露接口都放在这里

### 3.mainWeb web主体
- 项目所有的配置都放这里
- 权限体系放在这里

## 4.xxxModule
- 分模块写业务逻辑，也是dubbo的消费者

## 5.xxxProvider
- dubbo的生产者

# 五、module划分
1. Web结尾的是web项目
2. App结尾的是服务先买
3. Batch结尾的是批量项目
4. Module结尾的是模块 \
之后对接多家机构，可以使用module来进行管理

# 项目依赖
1. zookeeper[dubbo引用]
2. mysql


# 注意
1. maven 模块依赖的形式，需要重新打包才能刷新到新代码



# 参考资料
https://github.com/zhuzhegithub/SpringBootUnity

https://github.com/ityouknow/spring-boot-examples/tree/master/spring-boot-mybatis-annotation/src/main/java/com/neo/web

强大的工具集 https://github.com/AbrahamCaiJin/CommonUtilLibrary
lombok 需要下载插件，不然idea不识别