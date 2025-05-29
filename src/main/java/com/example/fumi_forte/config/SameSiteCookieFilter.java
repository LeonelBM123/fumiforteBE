
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;


@Component
public class SameSiteCookieFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Ejecuta la cadena de filtros primero
        chain.doFilter(request, response);

        // Luego modifica las cookies de la respuesta
        if (response instanceof HttpServletResponse httpResp) {
            for (String header : httpResp.getHeaders("Set-Cookie")) {
                if (header.startsWith("JSESSIONID") && !header.contains("SameSite")) {
                    httpResp.setHeader("Set-Cookie", header + "; SameSite=None");
                }
            }
        }
    }
}
