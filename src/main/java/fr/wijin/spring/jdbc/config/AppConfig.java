package fr.wijin.spring.jdbc.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("fr.wijin.spring.jdbc")
public class AppConfig {


	//@Bean
	public DataSource getDataSourcePG() {
		var ds = new DriverManagerDataSource();
		ds.setDriverClassName(org.postgresql.Driver.class.getName());
		ds.setUrl("jdbc:postgresql://localhost:5432/mabase");
		ds.setUsername("user");
		ds.setPassword("poec2025");
		ds.setSchema("springdata");
		return ds;
	}

	@Bean
	public DataSource getDataSource() {
		var ds = new DriverManagerDataSource();
		ds.setDriverClassName(
				org.h2.Driver.class.getName());
		// "org.h2.Driver"
		ds.setUsername("sa");
		ds.setPassword("");
		/*
		 * ds.setUrl("jdbc:h2:mem:launcher" +
		 * ";INIT=RUNSCRIPT FROM "
		 * + "'classpath:/jdbc/schema.sql'"
		 * + "\\;RUNSCRIPT FROM "
		 * + "'classpath:/jdbc/test-data.sql'");
		 */
		/* ds.setUrl("jdbc:h2:file:./launcher" +
				";INIT=RUNSCRIPT FROM "
				+ "'classpath:/jdbc/schema.sql'"
				+ "\\;RUNSCRIPT FROM "
				+ "'classpath:/jdbc/test-data.sql'"); */
		ds.setUrl("jdbc:h2:file:./launcher");
		return ds;
	}

}
