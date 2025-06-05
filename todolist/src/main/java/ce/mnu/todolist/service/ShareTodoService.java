package ce.mnu.todolist.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ce.mnu.todolist.repository.ShareTodo;
import ce.mnu.todolist.repository.ShareTodoRepository;

@Service
public class ShareTodoService {
	@Autowired
    private ShareTodoRepository shareTodoRepository;

    public void addShareTodo(Long roomid, String email, String name, String selectedDate, String sharetodos) {
    	ShareTodo sharetodo = new ShareTodo();
    	sharetodo.setEmail(email);
    	sharetodo.setName(name);
    	sharetodo.setCallendardate(selectedDate);
    	sharetodo.setSharetodo(sharetodos);
    	sharetodo.setRoomid(roomid);
        shareTodoRepository.save(sharetodo); // 엔티티 객체로 저장!
    }
    public void updateShareTodo(Long id, String sharetodo) {
        ShareTodo shareTodo = shareTodoRepository.findById(id).orElse(null);
        if (shareTodo != null) {
            shareTodo.setSharetodo(sharetodo);
            shareTodoRepository.save(shareTodo);
        }
    }
    public void deleteShareTodo(Long id) {
        shareTodoRepository.deleteById(id);
    }
    public List<ShareTodo> findByRoomidAndCallendardate(Long roomid, String callendardate) {
        return shareTodoRepository.findByRoomidAndCallendardate(roomid, callendardate);
    }

}
