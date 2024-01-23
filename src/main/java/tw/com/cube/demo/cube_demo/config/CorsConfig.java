package tw.com.cube.demo.cube_demo.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Configuration;

@Configuration
@WebFilter("/*")
public class CorsConfig implements Filter {
  public void init(FilterConfig filterConfig) {}

  public void destroy() {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    httpServletResponse.setHeader(
        "Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
    httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
    httpServletResponse.setHeader(
        "Access-Control-Allow-Headers",
        "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    chain.doFilter(request, response);
  }
}
