package ce.mnu.todolist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ce.mnu.todolist.repository.FriendUser;
import ce.mnu.todolist.repository.FriendUserRepository;
import jakarta.transaction.Transactional;

@Service
public class FriendUserService {
    @Autowired
    private FriendUserRepository friendUserRepository;

    public void addFriend(String a, String b) {
        friendUserRepository.save(new FriendUser(null, a, b));
        friendUserRepository.save(new FriendUser(null, b, a)); // 양방향 저장
    }

    public List<FriendUser> getFriends(String myname) {
        return friendUserRepository.findByMyname(myname);
    }
    public FriendUser existFriend(String friendname, String myname) {
    	return friendUserRepository.findByFriendnameAndMyname(friendname, myname);
    }
    
    @Transactional
    public void deleteFriend(String myname, String friendname) {
        // 양방향 모두 삭제
        friendUserRepository.deleteByMynameAndFriendname(myname, friendname);
        friendUserRepository.deleteByMynameAndFriendname(friendname, myname);
    }
}

