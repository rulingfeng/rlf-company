//package com.rlf.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import io.seata.rm.datasource.DataSourceProxy;
//import io.seata.spring.annotation.GlobalTransactionalInterceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.aop.Advisor;
//import org.springframework.aop.aspectj.AspectJExpressionPointcut;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
///**
// * 数据源代理
// * @author 小富 ，公众号：程序员内点事
// */
//@Configuration
//@ConditionalOnClass(DruidDataSource.class)
//@AutoConfigureBefore(DataSourceAutoConfiguration.class)
//public class DataSourceConfiguration {
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.m1")
//    public DataSource druidDataSource(){
//        DruidDataSource druidDataSource = new DruidDataSource();
//        return druidDataSource;
//    }
//
//    @Primary
//    @Bean("dataSource")
//    public DataSourceProxy dataSource(DataSource druidDataSource){
//        return new DataSourceProxy(druidDataSource);
//    }
//
//
//
//
//
//
////    @Bean
////    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy)throws Exception{
////        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
////        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
////        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
////        .getResources("classpath*:/mapper/*.xml"));
////        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
////        return sqlSessionFactoryBean.getObject();
////    }
//
//}
