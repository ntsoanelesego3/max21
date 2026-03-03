package com.example.shopmyway.Services;
import com.example.shopmyway.dao.UserRepo;
import com.example.shopmyway.models.UserPrinciple;
import com.example.shopmyway.models.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepo repo;

    public UserDetailService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);

        if (user == null) {
            try {
                System.out.println("User not Found");
            } catch (Exception e) {
                throw new UsernameNotFoundException("found no user in Database");
            }
        }
        return new UserPrinciple(user);
    }
}
