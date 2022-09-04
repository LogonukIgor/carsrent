package by.logonuk.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ConnectionPoolConfig.class, DatabaseProperties.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan("by.logonuk")
public class ApplicationContextStarter {
}
