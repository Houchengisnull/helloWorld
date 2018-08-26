package hc.web.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(
		basePackages = "hc.web.mapper", 					// MapperRegister将扫描路径下mapper
		sqlSessionFactoryRef = "hcSqlSessionFactory"			// 
		)
public class HcDataSourceConfig {
	
	@Bean(name = "hcDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.hc")
    @Primary
    public DataSource hcDatasource() {
        return DataSourceBuilder.create().build();
    }

	@Bean(name = "hcSqlSessionFactory")
    @Primary
    public SqlSessionFactory hcSqlSessionFactory(@Qualifier("hcDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
//	        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:"));
        return bean.getObject();
    }
	
	 /**
     * 配置事务管理器
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "hcTransactionManger")
    @Primary
    public DataSourceTransactionManager hcTransactionManger(@Qualifier("hcDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 模版
     *
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "hcSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate hcSqlSessionTemplate(@Qualifier("hcSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
