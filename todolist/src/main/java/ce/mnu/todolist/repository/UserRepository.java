package ce.mnu.todolist.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmailAndPasswd(String email, String passwd);
}
