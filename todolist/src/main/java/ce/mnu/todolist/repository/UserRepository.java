package ce.mnu.todolist.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    boolean existsByEmail(String email);
    User findByEmailAndPasswd(String email, String passwd);
    User findByEmail(String email);
}

