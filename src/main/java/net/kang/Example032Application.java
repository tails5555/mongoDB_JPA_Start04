package net.kang;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import net.kang.config.JwtFilter;

@SpringBootApplication
public class Example032Application {

	public static void main(String[] args) {
		SpringApplication.run(Example032Application.class, args);
	}

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/user/*");
		return registrationBean;
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.data.db-main")
	public DataSource mainDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.data.db-log")
	public DataSource contractDataSource() {
		return DataSourceBuilder.create().build();
	}
}
