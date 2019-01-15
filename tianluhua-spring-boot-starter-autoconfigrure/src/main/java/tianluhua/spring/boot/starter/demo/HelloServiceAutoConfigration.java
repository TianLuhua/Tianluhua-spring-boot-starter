package tianluhua.spring.boot.starter.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
