package finalproject.suppliersystem.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller that catches if a path is requested that does not exist
 */
@Controller
public class PageNotFoundController
{
    @RequestMapping(value = { "{path:(?!webjars|static).*$}", "{path:(?!webjars|static).*$}/**"}, headers ="Accept=text/html")
    public void unknown() throws UnknownResourceException
    {
        throw new UnknownResourceException("Page not found!");
    }
}
