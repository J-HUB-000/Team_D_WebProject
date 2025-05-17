package ce.mnu.todolist.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface FriendUserRepository extends CrudRepository<FriendUser, Long> {
    List<FriendUser> findByMyname(String myname);
    public void deleteByMynameAndFriendname(String myname, String friendname);
}
