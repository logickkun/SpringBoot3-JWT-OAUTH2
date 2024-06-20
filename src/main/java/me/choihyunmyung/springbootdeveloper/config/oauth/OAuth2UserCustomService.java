package me.choihyunmyung.springbootdeveloper.config.oauth;

import lombok.RequiredArgsConstructor;
import me.choihyunmyung.springbootdeveloper.domain.User;
import me.choihyunmyung.springbootdeveloper.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User  = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo;

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        } else {
            System.out.println("우리는 구글과 페이스북만 지원해요");
            throw new OAuth2AuthenticationException("Unsupported provider");
        }

        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();

        User user = saveOrUpdate(email, name);

        return new PrincipalDetail(user, oAuth2User.getAttributes());
    }

    private User saveOrUpdate(String email, String name) {
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .build());

        return userRepository.save(user);
    }

}
