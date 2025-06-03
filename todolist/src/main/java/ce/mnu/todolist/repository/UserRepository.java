package ce.mnu.todolist.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    User findByEmailAndPasswd(String email, String passwd);
    User findByEmail(String email);
    User findByName(String name);
}

