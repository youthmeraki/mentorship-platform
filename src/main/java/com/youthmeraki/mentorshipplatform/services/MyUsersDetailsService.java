package com.youthmeraki.mentorshipplatform.services;

import com.youthmeraki.mentorshipplatform.models.User;
import com.youthmeraki.mentorshipplatform.repositories.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MyUsersDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public MyUsersDetailsService(UserRepo repo) {
        this.userRepo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Validate user status before authentication
//        if (!user.isActive()) {
//            throw new DisabledException("User account is disabled");
//        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().toString());
        Set<GrantedAuthority> authorities = Set.of(authority);


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }
}

