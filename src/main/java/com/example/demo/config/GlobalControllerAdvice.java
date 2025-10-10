package com.example.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Global ControllerAdvice to add common attributes to all views.
 *
 * This avoids having to explicitly declare these attributes in each controller.
 * Methods annotated with @ModelAttribute are executed before each request
 * and automatically add attributes to the model.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * Adds the current request URI to the model.
     * Useful for marking the active link in the navigation menu.
     *
     * @param request Current HttpServletRequest
     * @return Request URI (e.g., "/", "/products", "/about")
     */
    @ModelAttribute("requestURI")
    public String addRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * Adds the application context path to the model.
     * Useful if the application is not deployed at the root.
     *
     * @param request Current HttpServletRequest
     * @return Context path (usually "" if at root)
     */
    @ModelAttribute("contextPath")
    public String addContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    /**
     * Adds the server name to the model.
     * Can be useful for displaying environment information.
     *
     * @param request Current HttpServletRequest
     * @return Server name
     */
    @ModelAttribute("serverName")
    public String addServerName(HttpServletRequest request) {
        return request.getServerName();
    }
}
