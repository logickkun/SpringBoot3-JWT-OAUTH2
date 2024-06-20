package me.choihyunmyung.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Getter
public class JwtFactory {

    private String subject = "test@email.com"; // 기본 subject 설정
    private Date issuedAt = new Date(); // 현재 시간을 issuedAt으로 설정
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis()); // 만료 시간은 현재 시간으로부터 14일 후로 설정
    private Map<String, Object> claims = Collections.emptyMap(); // 기본 클레임은 빈 맵으로 설정

    // 빌더 패턴을 사용해 설정이 필요한 데이터만 선택 설정
    @Builder
    public JwtFactory(String subject, Date issuedAt, Date expiration, Map<String, Object> claims) {

        // 빌더 패턴을 사용한 생성자
        // 입력된 값이 null이 아닌 경우 해당 값으로 설정, 그렇지 않으면 기본 값으로 설정
        this.subject = subject != null ? subject : this.subject; // subject 설정
        this.issuedAt = issuedAt != null ? issuedAt : this.issuedAt; // issuedAt 설정
        this.expiration = expiration != null ? expiration : this.expiration; // expiration 설정
        this.claims = claims != null ? claims : this.claims; // claims 설정
    }

    // 기본 값으로 설정된 JwtFactory 객체를 반환하는 정적 메서드
    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build(); // 빌더 패턴을 사용해 기본 값으로 설정된 JwtFactory 객체 생성 및 반환
    }

    // jjwt 라이브러리를 사용해 JWT 토큰 생성
    public String createToken(JwtProperties jwtProperties) {
        // JWT 토큰을 생성하는 메서드
        // JwtProperties 객체를 사용하여 토큰의 발급자와 비밀 키를 설정하고, 설정된 필드 값들을 사용해 토큰을 생성
        return Jwts.builder()
                .setSubject(subject) // subject 설정
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더의 타입을 JWT로 설정
                .setIssuer(jwtProperties.getIssuer()) // 토큰 발급자 설정
                .setIssuedAt(issuedAt) // 발급 시간 설정
                .setExpiration(expiration) // 만료 시간 설정
                .addClaims(claims) // 클레임 추가
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명 알고리즘 및 비밀 키 설정
                .compact(); // JWT 토큰 생성 및 반환
    }
}

