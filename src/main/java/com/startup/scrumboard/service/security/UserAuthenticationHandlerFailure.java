package com.startup.scrumboard.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 *
 */
//@Service
public class UserAuthenticationHandlerFailure implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException exception) throws IOException, ServletException {

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON.getType());
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());

        final OutputStream out = httpServletResponse.getOutputStream();

        try {
            ObjectMapper mapper=new ObjectMapper();
            out.write(mapper.writeValueAsBytes("FUK!"));
            out.flush();
        } catch (IOException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            out.close();
        }
    }
}
