package pl.booking;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/**
 * Created by Szymon on 1/6/2016.
 */
@Configuration
public class HibernateConfiguration {

    @Autowired
    SessionFactory factory;

    @Bean
    public SessionFactory hibernateFactory() {
        return new LocalSessionFactoryBean().getObject();
    }

}
