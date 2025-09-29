package com.geqian.evalution.teachingevalution;

import com.geqian.cache.cache.SimpleCacheTemplate;
import com.geqian.file.storage.FileStorageTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.net.InetAddress;
import java.net.UnknownHostException;

@MapperScan("com.geqian.evalution.teachingevalution.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy
public class TeachingEvalutionApplication {

    public static void main(String[] args) throws UnknownHostException {
        // Spring容器启动前加载配置
        System.setProperty("server.ip", getNetworkIp());

        ConfigurableApplicationContext applicationContext = SpringApplication.run(TeachingEvalutionApplication.class, args);
        SimpleCacheTemplate<?, ?> simpleCacheTemplate = applicationContext.getBean(SimpleCacheTemplate.class);
        FileStorageTemplate fileStorageTemplate = applicationContext.getBean(FileStorageTemplate.class);
        System.out.println("当前缓存：" + simpleCacheTemplate.getClass().getSimpleName());
        System.out.println("当前文件存储：" + fileStorageTemplate.getClass().getSimpleName());
    }

    /**
     * 网络 ip
     *
     * @return
     * @throws UnknownHostException
     */
    private static String getNetworkIp() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        if (inetAddress.isLoopbackAddress()) {
            // 如果本地IP是环回地址，则获取第一个非环回地址
            InetAddress[] allAddresses = InetAddress.getAllByName(inetAddress.getHostName());
            for (InetAddress address : allAddresses) {
                if (!address.isLoopbackAddress()) {
                    return address.getHostAddress();
                }
            }
        }
        String hostAddress = inetAddress.getHostAddress();
        hostAddress = "localhost";
        return hostAddress;
    }


}
