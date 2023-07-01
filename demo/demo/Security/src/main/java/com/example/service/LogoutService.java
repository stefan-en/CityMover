package com.example.service;


import com.example.Service.UserService;
import com.example.entity.Token;
import com.example.repo.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;


@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Missing or invalid token");
        }
        jwt = authHeader.substring(7);
        Token storedToken = tokenRepository.findByToken(jwt);

        if (storedToken == null) {
            throw new ForbiddenException("Invalid token");
        }

        tokenRepository.deleteById(storedToken.getId());
        SecurityContextHolder.clearContext();
    }
}
