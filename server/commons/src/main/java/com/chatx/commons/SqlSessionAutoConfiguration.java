package com.chatx.commons;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.util.Optional;

/**
 * SqlSessionFactoryBean 自动化配置, 主要是 mybatis, page-helper 两个组件的配置
 *
 * @author Jun
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(SqlSessionFactoryBean.class)
public class SqlSessionAutoConfiguration {

    /**
     * 自动配置 {@link SqlSessionFactoryBean}, 支持 mybatis, page-helper 组件.
     * <p/>
     * mybatis: 此配置类与如下配置同效果
     * <pre>
     *   mybatis:
     *     mapper-locations: classpath:mappers/*.xml
     *     configuration:
     *       map-underscore-to-camel-case: true
     *       use-generated-keys: true
     * </pre>
     *
     * @param dataSource        数据源
     * @param mybatisProperties mybatis 配置属性
     * @param pageInterceptor   分页连接器，见 {@linkplain com.github.pagehelper.PageInterceptor}
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean("sqlSessionFactory")
    @ConditionalOnClass({DataSource.class, MybatisProperties.class})
    @ConditionalOnMissingBean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource,
                                                       MybatisProperties mybatisProperties,
                                                       @Nullable @Qualifier("pageInterceptor") Interceptor pageInterceptor) {
        // 初始化 sqlSessionFactory
        var sqlSessionFactoryBean = new SqlSessionFactoryBean();

        // 默认配置 mybatis
        // 1. 默认采用下划线区分字符
        // 2. 采用 generated key (insert 语句可返回 primary id)
        var conf = mybatisProperties.getConfiguration();
        if (conf == null) {
            conf = new org.apache.ibatis.session.Configuration();
            conf.setMapUnderscoreToCamelCase(true);
            conf.setUseGeneratedKeys(true);
        }

        // mapper xml 位置置入
        var resources = mybatisProperties.resolveMapperLocations();
        if (ObjectUtils.isEmpty(resources)) {
            mybatisProperties.setMapperLocations(new String[]{"classpath:mappers/*.xml"});
            resources = mybatisProperties.resolveMapperLocations();
        }

        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(resources);
        sqlSessionFactoryBean.setConfiguration(conf);
        Optional.ofNullable(pageInterceptor).ifPresent(t -> sqlSessionFactoryBean.setPlugins(pageInterceptor));

        return sqlSessionFactoryBean;
    }
}
