package com.zx.Configure;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Auother zhangxing
 * @Date 2019-05-23 15:53
 * @note
 */
@Configuration
@Slf4j
public class DataSourceConfig {

//    @Value("${spring.datasource.base.url}")
//    private String url;
//
//    @Value("${spring.datasource.base.username}")
//    private String username;
//
//    @Value("${spring.datasource.base.password}")
//    private String password;
//
//    @Bean(name = "dataSource")
//    public DataSource getDataSource (){
//        DruidDataSource datasource = new DruidDataSource();
//        datasource.setUrl(url);
//        datasource.setUsername(username);
//        datasource.setPassword(password);
//        return datasource;
//    }

//    @Bean(name = "dataSource")
//    public DataSource druidBaseDataSource() {
//        DruidDataSource datasource = new DruidDataSource();
//        datasource.setUrl(this.baseUrl);
//        datasource.setUsername(baseUsername);
//        datasource.setPassword(basePassword);
//        datasource.setDriverClassName(baseDriverClassName);
//        List<String> connectionInitSqlsList = new ArrayList<>();
//        connectionInitSqlsList.add(connectionInitSqls);
//        datasource.setConnectionInitSqls(connectionInitSqlsList);
//        datasource.setInitialSize(initialSize);
//        datasource.setMinIdle(minIdle);
//        datasource.setMaxActive(maxActive);
//        datasource.setMaxWait(maxWait);
//        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//        datasource.setValidationQuery(validationQuery);
//        datasource.setTestWhileIdle(testWhileIdle);
//        datasource.setTestOnBorrow(testOnBorrow);
//        datasource.setTestOnReturn(testOnReturn);
//        datasource.setPoolPreparedStatements(poolPreparedStatements);
//        log.info("datasource init success");
//        return datasource;
//    }
}
