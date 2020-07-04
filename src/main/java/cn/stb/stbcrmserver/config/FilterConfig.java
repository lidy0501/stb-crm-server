package cn.stb.stbcrmserver.config;

import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.domain.Staff;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Configuration
public class FilterConfig implements Filter {
    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //排除登录请求
        if (request.getRequestURI().contains("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        log.info("请求拦截");
        HttpSession session = request.getSession();

        if (session.getAttribute("staffId") != null) {
            AcContext.setStaffId((String)session.getAttribute("staffId"));
            AcContext.setStaff((Staff) session.getAttribute("staff"));
        }
        filterChain.doFilter(servletRequest, servletResponse );
    }

    @Override
    public void destroy() {
    }
}
