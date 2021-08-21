# 问卷星球项目结构说明
## 技术选型
- springboot
- Mybatis & MybatisPlus
- MySQL
- Swagger
- knife4j
- satoken
- druid
- lombok
- fastjson
- easy-captcha
- hutool

## 代码结构说明
#### questionnaire-common 公共模块
1. CorsConfig：解决跨域
2. Result：统一接口返回数据
3. ResultEnum：接口返回码和提示信息
4. properties：一些资源文件，大部分从resources中读取


#### questionnaire-tools 工具模块
1. advice：全局异常处理和统一接口返回
> 接口返回时，直接返回数据（string类型不会封装，请避免返回值为string），会自动封装成格式化后的代码。
> 如果出现异常的情况，例如用户名已被注册，抛出异常即可，没有特殊的异常，直接抛出 DefaultException("提示信息") 即可，提示信息会封装到统一接口返回中的msg属性。
2. domain：存放全局变量等
3. exception：自定义异常
4. utils：一些工具包

#### questionnaire-core 核心代码
1. config：配置类
2. modules： sys负责用户、权限，question问卷相关
3. entity中，默认是与数据库一一对应的，VO代表返回给前端的，req代表前端传递过来的参数

### 权限说明
获取当前登录的用户id：StpUtil.getLoginIdAsInt();

> yml说明
> 
> spring.profiles.active=dev dev代表开发环境 prod代表线上环境


## 功能说明
### 登录注册
1. 登录注册一体化接口：已注册的用户，会校验账号密码是否正确；未注册的用户，自动注册，返回用户信息和Token；
2. 发送邮件接口：输入邮箱发送邮件
3. 登录注册邮件接口：校验邮箱和验证码是否匹配，如果不匹配






















