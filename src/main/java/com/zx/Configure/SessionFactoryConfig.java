package com.zx.Configure;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mapstruct.Qualifier;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Auother zhangxing
 * @Date 2019-05-23 15:17
 * @note
 */
@Configuration
@MapperScan(basePackages = "com.zx.Mapper", sqlSessionTemplateRef = "SessionTemplate")
@Slf4j
public class SessionFactoryConfig {

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.base")
    @Primary
    public DataSource testDataSource() {
        log.info("datasource init");
        return DataSourceBuilder.create().build();
    }

    /**
     * 读库
     * @param dataSource
     * @return
     * @throws Exception
     */
//    @Bean(name = "SessionFactory")
//    public SqlSessionFactory BuildSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
//            throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/*.xml"));
//        bean.setConfigLocation(
//                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis-config.xml")[0]);
//        return bean.getObject();
//    }
//
//    @Bean(name = "TransActionManager")
//    public DataSourceTransactionManager testTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "SessionTemplate")
//    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("SessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
}
