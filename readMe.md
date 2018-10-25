# 一、项目名称【一站式web框架】
# 二、项目特点
1. 牛逼

# 三、项目简介
## （1）web部分
1. 打造一款轻量级的web架构： \
    springboot + shiro + mybatis + jpa + mongodb + redis + swagger；
2. 集群部署： \
    session缓存在redis里面；
3. TokenId登录： \
    支持web登录和api服务项目；
4. SpringCloud微服务 或者 Dubbo；（不建议使用）
5. 模块分离。

## （2）微服务部分
1. 微服务提供两种解决方案SpringCloud（Rest风格） 和 Dubbo（RPC），当然推荐使用Spring家族的开源框架，没啥理由就是信任。
2.  采用SpringCloud模块：\
    a、 Eureka 注册中心 \
    b、 消费端 ：\
        · Eureka Client 链接注册中心 \
        · Ribbon 负载均衡 \
        · Feign 访问服务端 \
        · Hystrix 熔断和断路器 \
        · 监控检查 \
    c、 服务端：\
        · Eureka Server 链接注册中心 \
        · 健康检查
    d、 可视化监控 spring cloud admin \
    e、 zuul 网关 暂不启用 

# 四、项目规范
## （1）命名规则
### 1、Module 约束
1. Module 名称单词间以横线分隔；
2. Module 命名
   1. Web结尾的是web项目
   2. App结尾的是服务先买
   3. Batch结尾的是批量项目
   4. Module结尾的是模块 \

### （2）、模块说明
### 1.commonModule 公共模块
- 存放mybatis的dao、mapping、model模块
- 存放各种工具类

### 2.dubboApiModulede dubbo API
- dubbo所有的暴露接口都放在这里

### 3.mainWeb web主体
- 项目所有的配置都放这里
- 权限体系放在这里
- 访问Swagger：http://127.0.0.1:8080/swagger-ui.html

## 4.xxxModule
- 分模块写业务逻辑，也是dubbo的消费者

## 5.xxxProvider
- dubbo的生产者


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