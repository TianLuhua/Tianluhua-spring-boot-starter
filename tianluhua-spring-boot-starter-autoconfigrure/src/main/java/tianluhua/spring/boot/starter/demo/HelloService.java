package tianluhua.spring.boot.starter.demo;


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
