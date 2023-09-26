package com.momukgback.JWT;

import com.momukgback.Entity.User;
import com.momukgback.Repository.UserRepository;
import com.momukgback.Service.JWT.CustomUserDetailService;
import com.momukgback.enums.UserRole;
import com.momukgback.error.ErrorCode;
import com.momukgback.error.exception.ForbiddenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Transactional
public class JwtTokenProvider {
    private final UserRepository userRepository;
    private final CustomUserDetailService customUserDetailService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.accessTokenExpiration}")
    private long accessTokenValidTime;
    // 리프레시 토큰 유효시간
    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenValidTime;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(String email, UserRole userRole) {
        return this.createToken(email, userRole, accessTokenValidTime);
    }

    // Refresh Token 생성.
    public String createRefreshToken(String email, UserRole userRole) {
        return this.createToken(email, userRole, refreshTokenValidTime);
    }

    // Create token
    public String createToken(String email, UserRole userRole, long tokenValid) {
        Claims claims = Jwts.claims().setSubject(email); // claims 생성 및 payload 설정
        claims.put("roles", userRole.toString()); // 권한 설정, key/ value 쌍으로 저장

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + tokenValid)) // 토큰 유효 시간 저장
                .signWith(key, SignatureAlgorithm.HS256) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserEmail(String token){
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build();
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }

    public String reissueAccessToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken);
        if (user == null) {
            throw new ForbiddenException("401", ErrorCode.ACCESS_DENIED_EXCEPTION);
        }

        return createAccessToken(user.getEmail(), user.getUserRole());
    }

    // Request의 Header에서 AccessToken 값을 가져옵니다. "authorization" : "token"
    public String resolveAccessToken(HttpServletRequest request) {
        if(request.getHeader("authorization") != null )
            return request.getHeader("authorization").substring(7);
        return null;
    }

    // Request의 Header에서 RefreshToken 값을 가져옵니다. "refreshToken" : "token"
    public String resolveRefreshToken(HttpServletRequest request) {
        if(request.getHeader("refreshToken") != null )
            return request.getHeader("refreshToken").substring(7);
        return null;
    }

    // 토큰의 유효성 + 만료일자 확인
    @SneakyThrows
    public boolean validateToken(String jwtToken) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException("JWT token has expired");
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims string is empty");
        } catch (SignatureException e) {
            throw new SignatureException("JWT signature does not match");
        }
    }

    // 어세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("authorization", "Bearer "+ accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("refreshToken", "Bearer "+ refreshToken);
    }

    // RefreshToken 존재유무 확인
    public boolean existsRefreshToken(String refreshToken) {
        // User 엔티티에서 해당 refreshToken을 가진 사용자를 조회
        User user = userRepository.findByRefreshToken(refreshToken);

        // 사용자가 존재하는지 여부를 반환
        return user != null;
    }

}
