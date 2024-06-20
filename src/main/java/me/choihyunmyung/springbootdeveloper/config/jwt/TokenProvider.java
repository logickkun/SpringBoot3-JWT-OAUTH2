package me.choihyunmyung.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import me.choihyunmyung.springbootdeveloper.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider { // 토큰 제공자 클래스

    private final JwtProperties jwtProperties; // JwtProperties 의존성 주입

    public String generateToken(User user, Duration expiredAt) {
        // 토큰 생성 메서드
        Date now = new Date(); // 현재 시간 저장
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user); // 만료 시간 설정 후 토큰 생성
    }

    // 토큰 생성 메서드.
    private String makeToken(Date expiry, User user) {
        Date now = new Date(); // 현재 시간 저장

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 type : JWT
                .setIssuer(jwtProperties.getIssuer()) // iss : 애플리케이션 설정 값
                .setIssuedAt(now) // 현재 시간 설정
                .setExpiration(expiry) // 만료 시간 설정
                .setSubject(user.getEmail()) // sub : 유저 이메일 설정
                .claim("id", user.getId())  // 클레임에 유저 id 추가
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 시크릿 키를 이용해 H256 방식으로 서명
                .compact(); // 토큰 생성

    }

    // 토큰 유효성 검사 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀 키로 토큰 파싱
                    .parseClaimsJws(token);
            return true; // 토큰이 유효한 경우 true 반환
        } catch (Exception e) { // 파싱 중 예외 발생 시 false 반환
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token); // 토큰에서 클레임 추출

        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")); // 권한 설정

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User
                        (claims.getSubject(), "", authorities), token, authorities); // 인증 객체 생성 및 반환
    }

    // 토큰 기반으로 유저 ID를 가져오는 메서드
    public Long getUserId(String token) {
        Claims claims = getClaims(token); // 토큰에서 클레임 추출
        return claims.get("id", Long.class); // 클레임에서 유저 ID 추출 및 반환
    }

    // 클레임 조회 메서드
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey()) // 비밀 키로 토큰 파싱
                .parseClaimsJws(token)
                .getBody(); // 클레임 반환
    }
}
