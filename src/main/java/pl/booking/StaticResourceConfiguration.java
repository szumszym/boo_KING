package pl.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static java.util.Arrays.asList;

/**
 * Created by Szymon on 1/7/2016.
 */
@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String directory = env.getProperty("booking.paths.uploadedFiles");
        registry
                .addResourceHandler("/files/**")
                .addResourceLocations("file://" + directory + "/");

        if (asList(env.getActiveProfiles()).contains("dev")) {
            registry
                    .addResourceHandler("*/**")
                    .addResourceLocations("classpath:/static/src/");
        } else {
            registry
                    .addResourceHandler("*/**")
                    .addResourceLocations("classpath:/static/build/");
        }
    }
}