# 一 模块介绍

> * 通过模块设计实现可插拔、低耦合、"小"依赖的技术和业务组件,并提供清晰合理的代码结构

| 项目                                     | 说明                               |
|----------------------------------------|----------------------------------|
| `dts-framework`                        | 该包是技术库，每个子包，代表一个组件               |
| ---- `dts-common`                      | 定义基础 pojo 类、枚举、工具类等等             |
| ---- `dts-spring-boot-starter-mybatis` | MyBatis 拓展,代码生成,自动注入等            |
| `dts-all`                              | 基础服务模块,包含用户注册,登陆,字典管理等功能         |
| ---- `dts-all-api`                     | 基础服务模块 API，暴露给其它模块调用             |
| ---- `dts-all-biz`                     | 基础服务模块实现                         |
| `dts-receive`                          | 接收模块,处理接收端业务，接收相关配置              | |
| `dts-data`                             | 数据处理模块,处理报文解析、计算及转发。解析、计算及转发相关配置 |
| `dts-iot`                              | 微Iot模块,    实现物联网轻量化              |

# 二 功能列表
> * 待实现的，我们使用 ⭐️ 标记。

### 系统功能

| 模块                              | 标记 | 功能         | 描述                          |
|---------------------------------|----|------------|-----------------------------|
| dts-spring-boot-starter-mybatis |    | MyBatis 拓展 | MyBatis 拓展,代码生成,自动注入等       |
| dts-all                         |    | Excel 拓展   | Excel 拓展,提供公共导入方法           |
| dts-all                         |    | 异常处理       | 统一异常处理                      |
| dts-spring-boot-starter-i18n    | ⭐️ | 国际化        | 基于数据库配置的国际化                 |
| common                          | ⭐️ | 全链路监控      | 结合数据运营实现全链路监控，先于客户发现问题并解决问题 |
| common                          | ⭐️ | 统一日志注解     | 自定义注解记录请求日志                 |
| dts-all                         |    | 字典管理       | 字典基本查询及本地缓存实现               |
| dts-all                         | ⭐️ | 权限管理       | 角色菜单权限分配、设置角色按机构进行数据范围权限划分  |
| dts-all                         | ⭐️ | 注册         | 注册                          |
| dts-all                         |    | 登陆         | 登陆                          |
| dts-all                         | ⭐️ | 权限         | 权限                          |
| dts-receive                     |    | 规则引擎       | QLExpress实现的动态配置            |
| dts-all                         | ⭐️ | 日志查看       | 可查看数据接收、处理、转发、操作的详细日志       |
|                                 |    | docker部署   | docker部署                    |

# 三 开发原则
1 接口返回对象统一使用[CommonResult.java](common%2Fsrc%2Fmain%2Fjava%2Fchc%2Fdts%2Fcommon%2Fpojo%2FCommonResult.java)

2 接口分页对象统一使用[PageParam.java](common%2Fsrc%2Fmain%2Fjava%2Fchc%2Fdts%2Fcommon%2Fpojo%2FPageParam.java)和[PageResult.java](common%2Fsrc%2Fmain%2Fjava%2Fchc%2Fdts%2Fcommon%2Fpojo%2FPageResult.java)

3 数据库对象统一继承[BaseDO.java](common%2Fsrc%2Fmain%2Fjava%2Fchc%2Fdts%2Fcommon%2Fpojo%2FBaseDO.java)

4 数据库表建立时尽可能添加非空限制和唯一索引

5 工具类尽量使用Common模块中的已有的类或者引入的工具类jar包中的类,不要重复造轮子