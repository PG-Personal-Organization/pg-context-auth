package pg.context.auth.spring.configuration.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pg.context.auth.domain.user.User;
import pg.context.auth.domain.user.UserDao;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static pg.context.auth.spring.configuration.auth.JwtExpire.ACCESS_TOKEN;

/**
 * The type Jwt token refresher.
 */
@Service
public class JwtTokenRefresher {
    private static final String AUTH_BEARER = "Bearer";
    private final UserDao userDao;
    private final String secretKey;

    /**
     * Instantiates a new Jwt token refresher.
     *
     * @param userDao   the user dao
     * @param secretKey the secret key
     */
    @Autowired
    public JwtTokenRefresher(final UserDao userDao, final @Value("${jwt.secret}") String secretKey) {
        this.userDao = userDao;
        this.secretKey = secretKey;
    }

    /**
     * Attempt refresh token.
     *
     * @param request  the request
     * @param response the response
     */
    public void attemptRefreshToken(final HttpServletRequest request, final HttpServletResponse response) {
        String authorizationHeader = request.getHeader("Authorization-Refresh");

        if (!Strings.isNullOrEmpty(authorizationHeader) && authorizationHeader.startsWith(AUTH_BEARER + " ")) {
            String token = authorizationHeader.replace(AUTH_BEARER + " ", "");
            try {

                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(token);

                Claims body = claimsJws.getBody();

                String userId = body.get("userId", String.class);

                User user = userDao.findById(userId);

                String accessToken = Jwts.builder()
                        .setSubject(user.getUsername())
                        .claim("authorities", user.getRoles())
                        .claim("userId", userId)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN.getAmount()))
                        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                        .compact();

                response.addHeader("Authorization", AUTH_BEARER + " " + accessToken);
                response.setStatus(299);

            } catch (final Exception e) {
                if (e.getClass().equals(ExpiredJwtException.class)) {
                    response.setStatus(499);
                    response.setContentType(String.format("Token %s has expired, please login again", token));
                } else if (e.getClass().equals(SignatureException.class))
                    throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
                else throw e;
            }
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Authorization-Refresh token hasn't been provided with request");
    }
}
