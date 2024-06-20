//package me.choihyunmyung.springbootdeveloper.config; // 패키지 선언
//
//import lombok.RequiredArgsConstructor; // Lombok 어노테이션: final 필드에 대한 생성자 자동 생성
//import me.choihyunmyung.springbootdeveloper.service.UserDetailService;
//import org.springframework.context.annotation.Bean; // Spring Bean 등록을 위한 어노테이션
//import org.springframework.context.annotation.Configuration; // Spring Configuration을 위한 어노테이션
//import org.springframework.security.authentication.AuthenticationManager; // 인증 관리 클래스
//import org.springframework.security.authentication.ProviderManager; // Provider 기반의 AuthenticationManager 구현체
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // DAO 기반의 AuthenticationProvider
//import org.springframework.security.config.annotation.web.builders.HttpSecurity; // HTTP 보안 설정
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // 웹 보안 활성화 어노테이션
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer; // 웹 보안 커스터마이징 인터페이스
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // HTTP 설정 추상 클래스
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 비밀번호 암호화를 위한 BCrypt 인코더
//import org.springframework.security.web.SecurityFilterChain; // 보안 필터 체인 설정
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // 경로 매칭을 위한 유틸 클래스
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console; // H2 콘솔 경로를 위한 유틸 메서드
//
//@Configuration // Spring Configuration 어노테이션
//@EnableWebSecurity // Spring Security 웹 보안 활성화
//@RequiredArgsConstructor // Lombok 어노테이션: final 필드에 대한 생성자 자동 생성
//public class WebSecurityConfig {
//
//    private final UserDetailService userService; // 사용자 상세 서비스를 위한 필드
//
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring() // 웹 보안 무시 설정
//                .requestMatchers(toH2Console()) // H2 콘솔에 대한 요청 무시
//                .requestMatchers(new AntPathRequestMatcher("/static/**")); // 정적 자원에 대한 요청 무시
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        return http
//                .authorizeHttpRequests(auth -> auth // HTTP 요청에 대한 인가 설정
//                        .requestMatchers(
//                                new AntPathRequestMatcher("/login"), // /login 경로 허용
//                                new AntPathRequestMatcher("/signup"), // /signup 경로 허용
//                                new AntPathRequestMatcher("/user") // /user 경로 허용
//                        ).permitAll() // 위 경로들 모두 접근 허용
//                        .anyRequest().authenticated()) // 나머지 요청은 인증 필요
//                .formLogin(formLogin -> formLogin // 폼 로그인 설정
//                        .loginPage("/login") // 커스텀 로그인 페이지 설정
//                        .defaultSuccessUrl("/articles") // 로그인 성공 시 이동할 기본 경로 설정
//                )
//                .logout(logout -> logout // 로그아웃 설정
//                        .logoutSuccessUrl("/login") // 로그아웃 성공 시 이동할 경로 설정
//                        .invalidateHttpSession(true) // 세션 무효화
//                )
//                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
//                .build(); // 보안 필터 체인 빌드
//
//    }
//
//
//    // 커스텀 인증 제공자 OAuth2 와 통합.
//    @Bean
//    public AuthenticationManager authenticationManager(
//            HttpSecurity http,
//            BCryptPasswordEncoder bCryptPasswordEncoder,
//            UserDetailService userDetailService) throws Exception {
//
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // DAO 기반의 인증 제공자 생성
//        authProvider.setUserDetailsService(userService); // 사용자 상세 서비스 설정
//        authProvider.setPasswordEncoder(bCryptPasswordEncoder); // 비밀번호 인코더 설정
//        return new ProviderManager(authProvider); // 인증 관리자로 ProviderManager 반환
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder(); // BCrypt 비밀번호 인코더 반환
//    }
//
//}
