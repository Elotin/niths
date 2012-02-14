package no.niths.common.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class AppConfig {

    public static final String BASE_PACKAGE      = "no.niths",
                               PERSISTENCE_PROPS = "persistence.properties";
    @Bean
    public static PropertyPlaceholderConfigurer properties(){
        final PropertyPlaceholderConfigurer ppc =
                new PropertyPlaceholderConfigurer();
        final ClassPathResource[] resources = new ClassPathResource[] {
                new ClassPathResource(PERSISTENCE_PROPS)};
        ppc.setLocations(resources);
        ppc.setIgnoreUnresolvablePlaceholders(true);


//    	 FileSystemResource fsr = new FileSystemResource("/usr/share/credentials/snith/persistence.properties");

    	// Inncomment the line below if you are not a server. :)
          FileSystemResource fsr = new FileSystemResource(System.getenv("CREDENTIAL_PATH")+"/"+PERSISTENCE_PROPS);
         ppc.setLocation(fsr);  
         ppc.setIgnoreUnresolvablePlaceholders(true);
         return ppc;

    }
}
