package com.minibox.conf;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cm){
        return new EhCacheCacheManager(cm);
    }

    @Bean
    public EhCacheManagerFactoryBean ehcache(){
        EhCacheManagerFactoryBean ehcacheFactoryBean =
                new EhCacheManagerFactoryBean();
        ehcacheFactoryBean.setConfigLocation(
                new ClassPathResource("ehcache/ehcache.xml"));
        ehcacheFactoryBean.setShared(true);
        return ehcacheFactoryBean;
    }
}
