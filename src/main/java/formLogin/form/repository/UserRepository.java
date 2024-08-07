package formLogin.form.repository;

import formLogin.form.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByUsername(String username);

}
