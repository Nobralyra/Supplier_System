package finalproject.suppliersystem.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller that catches if a path is requested that does not exist
 * https://nixmash.com/java/custom-404-exception-handling-in-spring-mvc/
 */
@Controller
public class PageNotFoundController
{
    @RequestMapping(value = { "{path:(?!static).*$}", "{path:(?!static).*$}/**"}, headers ="Accept=text/html")
    public void unknown() throws UnknownResourceException
    {
        throw new UnknownResourceException("Page not found!");
    }
}
