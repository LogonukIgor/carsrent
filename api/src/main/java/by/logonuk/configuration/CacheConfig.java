package by.logonuk.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager cacheManagerRoles() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("roles");
        cacheManager.setCaffeine(cacheProperties());
        return cacheManager;
    }

    @Bean
    public CacheManager cacheManagerClassification() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("classification");
        cacheManager.setCaffeine(cacheProperties());
        return cacheManager;
    }

    public Caffeine<Object, Object> cacheProperties() {
        return Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(20)
                .expireAfterAccess(24, TimeUnit.HOURS)
                .weakKeys()
                .recordStats();
    }
}
