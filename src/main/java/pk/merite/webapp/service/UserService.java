package pk.merite.webapp.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pk.merite.webapp.data.UserRepository;
import pk.merite.webapp.info.UserInfo;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = retrieveByUserName(username);
        if (userInfo != null) {
            return new User(username, userInfo.getPassword(),
                    AuthorityUtils.commaSeparatedStringToAuthorityList(userInfo.getRoles()));
        }
        throw new UsernameNotFoundException("User " + username + " not verified");
    }

    public UserInfo retrieveByUserName(String userName) {
        return repository.findByUserName(userName);
    }
}
