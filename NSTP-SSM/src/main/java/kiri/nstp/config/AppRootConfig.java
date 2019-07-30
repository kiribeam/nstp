package kiri.nstp.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@ComponentScan(basePackages="kiri.nstp",excludeFilters= {
			@Filter(classes= {Controller.class, ControllerAdvice.class, Configuration.class })})
@PropertySource({"classpath:property/jdbc.properties", 
	"classpath:property/env.properties"})
@MapperScan("kiri.nstp.dao")
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class AppRootConfig {
	
	@Bean
	public PropertySourcesPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean(value="dataSource", destroyMethod="close")
	@Lazy(false)
	public DataSource getDataSource(@Value("${jdbcDriver}") String driverClass,
			@Value("${jdbcUrl}") String jdbcUrl,
			@Value("${jdbcUser}") String username,
			@Value("${jdbcPassword}") String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClass);
        config.addDataSourceProperty("cachePrepStmts", "true"); //是否自定义配置，为true时下面两个参数才生效
        config.addDataSourceProperty("prepStmtCacheSize", "25"); //连接池大小默认25，官方推荐250-500
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); //单条语句最大长度默认256，官方推荐2048
        config.addDataSourceProperty("useServerPrepStmts", "true"); //新版本MySQL支持服务器端准备，开启能够得到显著性能提升
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("useLocalTransactionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
		HikariDataSource dataSource = new HikariDataSource(config);
		return dataSource;
	}
	
	//TODO close level 1 cache and open redis cache
	@Bean
	@Lazy(false)
	public SqlSessionFactoryBean newSqlSessionFactory(DataSource dataSource) throws IOException{
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		Resource[] mapperLocations = new PathMatchingResourcePatternResolver().
				getResources("classpath:mapper/*.xml");
		bean.setMapperLocations(mapperLocations);
		bean.setTypeAliasesPackage("kiri.nstp.*");
		//set Camel auto reflect
		org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
		config.setMapUnderscoreToCamelCase(true);
		config.setCacheEnabled(true);
		config.setLazyLoadingEnabled(false);
		LogFactory.useLog4JLogging();
		bean.setConfiguration(config);
		
		PageInterceptor pi= new PageInterceptor();
		Properties ps = new Properties();
		ps.setProperty("resonable", "true");
		pi.setProperties(ps);
		bean.setPlugins(new Interceptor[] {pi});
		
		return bean;
	}
	@Bean("transcationManager")
	public DataSourceTransactionManager getManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	


}
