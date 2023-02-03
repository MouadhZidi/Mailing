package com.arabsoft.mailing.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String pathInfo = request.getRequestURI();
		System.out.println("pathInfo"+pathInfo);
		if (      pathInfo.contains("/swagger-ui.html")
				||pathInfo.contains("/GSA/ListAll")
				||pathInfo.contains("/mailing/sendemail")
				||pathInfo.contains("/mailing/sendemailattachement")
				||pathInfo.contains("/mailing/sendemailhtml")
				||pathInfo.contains("/mailing/sendemailinlineimage")
				||pathInfo.contains("/mailing/sendemailcombined")
				||pathInfo.contains("/EmailSender/findByAdress")
				||pathInfo.contains("/EmailTemplate/ListAll")
				||pathInfo.contains("/swagger-ui/index.html")
				||pathInfo.contains("/swagger-ui/swagger-ui.css")
				||pathInfo.contains("/swagger-ui/swagger-ui-standalone-preset.js")
				||pathInfo.contains("/swagger-ui/swagger-ui-bundle.js")
				||pathInfo.contains("/swagger-ui/swagger-ui-standalone-preset.js")
				||pathInfo.contains("/swagger-ui/favicon-32x32.png")
				||pathInfo.contains("/swagger-ui/favicon-16x16.png")
				||pathInfo.contains("/favicon.ico")
				||pathInfo.contains("/v3/api-docs")
				) {
		} else {
			final String requestTokenHeader = request.getHeader("Authorization");
			String username = null;
			String jwtToken = null;

			System.out.println("request :"+requestTokenHeader);
			System.out.println("requestTokenHeader startwith:"+requestTokenHeader.startsWith("Bearer "));
			
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				try {
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
					System.out.println("username :"+username);
				} catch (IllegalArgumentException e) {
					throw e;
				} catch (ExpiredJwtException e) {
					throw e;
				}
			} else {
				throw new ServletException("Pas de token");
			}

			if (username != null) {
				if (!jwtTokenUtil.validateToken(jwtToken, username)) {
					throw new ServletException("Token non valide");
				}
			} else {
				throw new ServletException("Token non valide");
			}
			System.out.println("requestTokenHeader :"+requestTokenHeader);
		}
		
		chain.doFilter(request, response);
	}
}
