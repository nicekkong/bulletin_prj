package com.cplanet.toring.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = "com.cplanet.toring.mapper",
        sqlSessionFactoryRef = "sqlSessionFactory")

public class MybatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();

        factory.setDataSource(dataSource);
        factory.setMapperLocations(applicationContext.getResources("classpath*:mapper/**/*mapper.xml"));
//        factory.setTypeAliasesPackage("alias VO 패키지 경로");

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();

        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setJdbcTypeForNull(null);
        factory.setConfiguration(configuration);

        return factory.getObject();
    }
}
