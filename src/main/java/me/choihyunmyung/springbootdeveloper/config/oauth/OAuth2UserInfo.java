package me.choihyunmyung.springbootdeveloper.config.oauth;

import java.util.Map;

public interface OAuth2UserInfo {
    String getEmail();
    String getName();
    Map<String, Object> getAttributes();
}
