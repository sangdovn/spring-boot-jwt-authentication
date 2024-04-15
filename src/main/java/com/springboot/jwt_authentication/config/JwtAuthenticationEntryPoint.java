package com.springboot.jwt_authentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.jwt_authentication.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest req,
            HttpServletResponse res,
            AuthenticationException authException)
            throws IOException, ServletException {

//        final ObjectMapper mapper = new ObjectMapper();
//        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        mapper.writeValue(res.getOutputStream(),
//                ErrorResponse.builder()
//                        .title("Unauthorized")
//                        .status(HttpServletResponse.SC_UNAUTHORIZED)
//                        .validationErrors(new HashMap<>())
//                        .errorMessage("Access denied")
//                        .path(req.getRequestURI())
//                        .build());

        // Create a custom error response object
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title("Unauthorized")
                .status(HttpStatus.UNAUTHORIZED.value())
                .errorMessage("Access denied")
                .path(req.getRequestURI())
                .build();

        // Return the custom error response as ResponseEntity
        ResponseEntity<ErrorResponse> entity = ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);

        // Write the ResponseEntity to the response output stream
        res.setContentType("application/json");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(new ObjectMapper().writeValueAsString(entity));
    }

}
