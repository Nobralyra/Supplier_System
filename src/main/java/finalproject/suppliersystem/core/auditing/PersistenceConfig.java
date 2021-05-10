package finalproject.suppliersystem.core.auditing;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 * Guides how to setup auditing with JPA in the project:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing
 * http://progressivecoder.com/spring-boot-jpa-auditing-example-with-auditoraware-interface/
 * https://rashidi.github.io/spring-boot-data-audit/
 * https://devkonline.com/tutorials/content/jpa-auditing-springboot
 *
 * Is taken from our last project: PADC_project_Commander_Con
 * https://github.com/Nobralyra/PADC_project_Commander_Con
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "utcDateTimeProvider")
public class PersistenceConfig
{
    @Bean
    public AuditorAware<String> auditorAware()
    {
        return new AuditorAwareImplementation();
    }

    /**
     * https://www.baeldung.com/java-zone-offset
     * https://stackoverflow.com/questions/55250489/spring-jpa-hibernate-cet-timezone-for-auditingentitylistener
     * EnableJpaAuditing has a parametre "dateTimeProviderRef". We make a bean, that provides
     * the correct wintertime for auditing in database.
    */
    @Bean
    public DateTimeProvider utcDateTimeProvider() {
        ZoneOffset zoneOffSet= ZoneOffset.of("+02:00");
        OffsetDateTime date = OffsetDateTime.now(zoneOffSet);
        return () -> Optional.of(LocalDateTime.now(ZoneId.from(date)));
    }
}
