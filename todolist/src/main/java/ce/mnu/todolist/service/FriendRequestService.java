package ce.mnu.todolist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ce.mnu.todolist.repository.FriendRequest;
import ce.mnu.todolist.repository.FriendRequestRepository;

@Service
public class FriendRequestService {
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    public void sendRequest(String from, String to) {
        if (!friendRequestRepository.existsByFromUserAndToUserAndStatus(from, to, "PENDING")) {
            friendRequestRepository.save(new FriendRequest(null, from, to, "PENDING"));
        }
    }

    public List<FriendRequest> getRequestsForUser(String toUser) {
        return friendRequestRepository.findByToUserAndStatus(toUser, "PENDING");
    }

    public FriendRequest findById(Long id) {
        return friendRequestRepository.findById(id).orElse(null);
    }

    public void acceptRequest(Long id) {
        FriendRequest req = friendRequestRepository.findById(id).orElse(null);
        if (req != null) {
            req.setStatus("ACCEPTED");
            friendRequestRepository.save(req);
        }
    }
}
