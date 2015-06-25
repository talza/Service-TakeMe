package app;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;


public class WebInitializer extends SpringBootServletInitializer {


    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	
    	
    	return application.sources(Application.class);
    }
    
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//    	FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
//        encodingFilter.setInitParameter("encoding", "UTF-8");
//        encodingFilter.setInitParameter("forceEncoding", "true");
//        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
//        super.onStartup(servletContext);
//  }
    
//    @Bean
//    CharacterEncodingFilter characterEncodingFilter() {
//    	  CharacterEncodingFilter filter = new CharacterEncodingFilter();
//    	  filter.setEncoding("UTF-8");
//    	  filter.setForceEncoding(true);
//    	  return filter;
//    	}    

}