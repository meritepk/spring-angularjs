package pk.merite.webapp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import pk.merite.webapp.info.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, String> {

    UserInfo findByUserName(String userName);
}
