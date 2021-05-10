package finalproject.suppliersystem.core.auditing;

import org.springframework.data.domain.AuditorAware;

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
public class AuditorAwareImplementation  implements AuditorAware<String>
{
    @Override
    public Optional<String> getCurrentAuditor()
    {
        return Optional.of("Admin");
    }
}
