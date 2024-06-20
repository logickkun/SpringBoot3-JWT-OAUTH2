package me.choihyunmyung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.choihyunmyung.springbootdeveloper.config.oauth.PrincipalDetail;
import me.choihyunmyung.springbootdeveloper.domain.User;
import me.choihyunmyung.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : " + username);
        Optional<User> userEntity = userRepository.findByEmail(username);
        if(userEntity.isPresent()){
            return new PrincipalDetail(userEntity.get()); //User 타입을 인자로 하는 생성자
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
