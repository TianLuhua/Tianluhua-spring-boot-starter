12 自定义启动器：<br>

12.1 创建一个Spring Initializr工程（只要里面有SpringBoot的启动器），然后创建四类文件<br>
12.1.1 完成核心业务类（我这里就是返回一句话，当然按照自己的需求来）：
   ~~~
   public class HelloService {
   
       private HelloProperties helloProperties;
   
       public HelloProperties getHelloProperties() {
           return helloProperties;
       }
   
       public void setHelloProperties(HelloProperties helloProperties) {
           this.helloProperties = helloProperties;
       }
   
       public String sayHello(String str) {
   
           return helloProperties.getPrefix() + str;
       }
   }
~~~
12.1.2 核心业务类对应属性的配置（XXXXProperties）：
~~~
@ConfigurationProperties(prefix = "hello")//和全局配置文件关联
public class HelloProperties {

    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
~~~
12.1.3 自动化配置类(XXXXXAutoConfigration)：
~~~
   @Configuration//说明该类为配置类
   @ConditionalOnWebApplication//前提条件
   @EnableConfigurationProperties(HelloProperties.class)//此配置和HelloProperties（又和全局配置文件关联）
   public class HelloServiceAutoConfigration {
   
       @Autowired
       private HelloProperties helloProperties;
       
       @Bean //将HelloService注入Spring容器
       public HelloService helloService() {
           HelloService helloService = new HelloService();
           helloService.setHelloProperties(helloProperties);//将HelloProperties注入到HelloService
           return helloService;
       }
   }
~~~
12.1.4 spring.factories(resources/META-INF/spring.factories):
~~~
   org.springframework.boot.autoconfigure.EnableAutoConfiguration=tianluhua.spring.boot.starter.demo.HelloServiceAutoConfigration
~~~
    
12.2 启动器工程<br>
    创建一个空工程,在pom.xml中依赖自动配置工程：
~~~
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>Tianluhua-spring-boot-starter</groupId>
    <artifactId>Tianluhua.spring.boot.starter</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>tianluhua.spring.boot.starter</groupId>
            <artifactId>tianluhua-spring-boot-starter-autoconfigrure</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>

</project>
~~~
12.3 发布：可将自己的启动器发到Maven仓库，也可以安装到本地的服务器(百度一大把)。
    
12.4 依赖自定义启动器，调用HelloService：<br>
依赖：
~~~
 <dependencies>
        <dependency>
            <groupId>Tianluhua-spring-boot-starter</groupId>
            <artifactId>Tianluhua.spring.boot.starter</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
~~~
调用：
~~~
@RestController
public class IndexController {

    @Autowired
    private HelloService helloService;
    
    @GetMapping("/index.html")
    @ResponseBody
    public String sayHello() {
        return helloService.sayHello(",TianLuhua");
    }
}
~~~
