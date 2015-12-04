package app;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class WebInitializer extends SpringBootServletInitializer {


    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	
    	
    	return application.sources(Application.class);
    }   
}