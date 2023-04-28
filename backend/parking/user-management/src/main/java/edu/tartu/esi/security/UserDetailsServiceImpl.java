package edu.tartu.esi.security;

import edu.tartu.esi.model.User;
import edu.tartu.esi.repository.UserRepository;
import edu.tartu.esi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(username);
        user.orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
        return user.map(ParkingUserDetails::new).get();
    }

//    private ParkingUserDetails getUserDetails(User user) {
//        return ParkingUserDetails.builder()
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .isAccountNonExpired(true)
//                .isAccountNonLocked(true)
//                .isEnabled(true)
//                .isCredentialsNonExpired(true)
//                .grantedAuthorities(user.getUserRole().getGrantedAuthorities())
//                .build();
//    }
}