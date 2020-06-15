# crm开发规则

#1.mysql数据库
用户名：root
密码：root
端口：3306
    
#2.redis
端口：6379
密码：无
    
    
# 启动该项目是需要的准备工作
1. 启动redis
2. 创建测试数据库，测试表 
3. 安装lombok插件，用于简化创建bean的过程 
 
sql如下: 
create database crm;

CREATE TABLE `test` (
  `id` varchar(10) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `test_phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

并出入一条数据，即可访问

如：http://localhost:9911/crm/TestController/queryAllTest
   
   
   
    
#3.controller文件夹用于存放所有的Controller类
    Controller类的第一路径为  @RequestMapping("**Controller") 
    如：@RequestMapping("/TestController")
    二级路径（方法映射）用方法名
    如：@RequestMapping("/queryAllTest")
      	public List<Test> queryAllTest () {
      		return testService.queryAllTest();
      	}
      	
#4.注入的注入的Bean，全部是私有的
    如：@Autowired
      	private TestService testService;
      	
#5.base包里存放的是公用的bean,如RespResult
    RespResult是结果相应对象，用于封装结果(不强制使用，具体使用规则看里面内容)
    
#6.config文件夹用于存放配置类，如redis的配置

#7.utils文件夹用于存放常用工具，如redis工具

#8.domain文件夹里存放的数据库表对应的全量字段bean

#9.vo文件夹用于存放传输数据bean
    如：请求参数较多时，需要封装成对象，命名为 **Req放在vo文件夹下
    相应数据封装成 **Vo,放在vo文件夹下
    
#10 sql文件存放在resource下的mapper文件夹下
命名为 **DaoMapper.xml, 即要与对应的dao文件对应


    
    
    