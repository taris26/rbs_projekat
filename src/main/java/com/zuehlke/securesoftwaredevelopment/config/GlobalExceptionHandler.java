package com.zuehlke.securesoftwaredevelopment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        String username = request.getRemoteUser();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String remoteAddr = request.getRemoteAddr();
        LOG.warn("Access denied: user='{}', method='{}', uri='{}', remoteAddr='{}', message='{}'",
                username, method, uri, remoteAddr, ex.getMessage());
        auditLogger.audit("Access denied for uri=" + uri + ", method=" + method);
        return "error";
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleSQLException(SQLException ex, HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String remoteAddr = request.getRemoteAddr();
        LOG.error("SQL error: method='{}', uri='{}', remoteAddr='{}', message='{}'",
                method, uri, remoteAddr, ex.getMessage());
        return "error";
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNumberFormatException(NumberFormatException ex, HttpServletRequest request) {
        String uri = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        LOG.warn("Invalid number format - possible injection attempt: uri='{}', remoteAddr='{}', message='{}'",
                uri, remoteAddr, ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String remoteAddr = request.getRemoteAddr();
        LOG.error("Unhandled exception: method='{}', uri='{}', remoteAddr='{}', exception='{}'",
                method, uri, remoteAddr, ex.getMessage(), ex);
        return "error";
    }
}
