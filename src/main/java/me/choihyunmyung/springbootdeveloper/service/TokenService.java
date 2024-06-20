package me.choihyunmyung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.choihyunmyung.springbootdeveloper.config.jwt.TokenProvider;
import me.choihyunmyung.springbootdeveloper.domain.RefreshToken;
import me.choihyunmyung.springbootdeveloper.domain.User;
import me.choihyunmyung.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;


    public String createNewAccessToken(String refreshToken) {

        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }




}
