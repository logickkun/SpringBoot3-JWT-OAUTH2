package me.choihyunmyung.springbootdeveloper.config.oauth;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes; // 네이버 사용자 정보는 response 객체 내부에 있음


    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
