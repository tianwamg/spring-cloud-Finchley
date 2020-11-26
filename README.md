# Spring-Cloud-Finchley
基于Finchley版本的微服务

### 项目结构
 - eureka-server 服务注册与发现
 - eureka-provider(2)  服务提供者
 - eureka-ribbon 服务消费负载均衡组件之ribbon
 - eureka-feign 服务消费负载均衡之feign
 - eureka-hytrix 断路器
 - hystrix-dashboard 断路器监控
 - hystrix-turbine 断路器聚合监控
 - eureka-zuul 路由网关
 - config-server 配置中心服务端
 - config-client 配置中心客户端
 - config-bus 消息总线
 - sleuth-zipkin 服务链路追踪

### 注意事项
1. hystrix监控显示需要先请求接口路径，否则不显示
2. zipkin server 下载[地址](https://repo1.maven.org/maven2/io/zipkin/java/zipkin-server/2.12.9/zipkin-server-2.12.9-exec.jar) 
