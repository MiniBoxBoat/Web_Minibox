package com.minibox.conf;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource({"classpath:dbconfig.properties"})
public class RootContextConfig {

    @Autowired
    private Environment env;

    @Value("classpath:h2/schema.sql")
    private String schemaScript;

    @Value("classpath:h2/data-prepare.sql")
    private String dataPrepareScript;

    @Bean
    @Profile("pro")
    public DataSource proDataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        WallConfig wallConfig = new WallConfig();
        wallConfig.setDir("META-INF/druid/wall/mysql");
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType("mysql");
        wallFilter.setConfig(wallConfig);
        List<Filter> filters = new ArrayList<>();
        filters.add(wallFilter);

        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(10000);
        statFilter.setLogSlowSql(true);
        filters.add(statFilter);

        dataSource.setProxyFilters(filters);

        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
//        dataSource.setFilters(env.getProperty("filters"));
        dataSource.setMaxActive(Integer.parseInt(env.getProperty("maxActive")));
        dataSource.setInitialSize(Integer.parseInt(env.getProperty("initialSize")));
        dataSource.setMaxWait(Long.parseLong(env.getProperty("maxWait")));
        dataSource.setMinIdle(Integer.parseInt(env.getProperty("minIdle")));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getProperty("timeBetweenEvictionRunsMillis")));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("minEvictableIdleTimeMillis")));
        dataSource.setValidationQuery(env.getProperty("validationQuery"));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("testWhileIdle")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("testOnBorrow")));
        dataSource.setTestOnReturn(Boolean.parseBoolean(env.getProperty("testOnReturn")));
        dataSource.setPoolPreparedStatements(Boolean.parseBoolean(env.getProperty("poolPreparedStatements")));
        dataSource.setMaxOpenPreparedStatements(Integer.parseInt(env.getProperty("maxOpenPreparedStatements")));
        return dataSource;
    }

    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2)
                .addScript(schemaScript).addScript(dataPrepareScript)
                .build();
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:sqlMapper/*.xml"));

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(true);
        configuration.setCallSettersOnNulls(true);
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate(DataSourceTransactionManager dataSourceTransactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(dataSourceTransactionManager);
        return transactionTemplate;
    }
}
