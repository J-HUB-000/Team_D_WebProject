package ce.mnu.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ce.mnu.todolist.repository.MyTodoRepository;
import ce.mnu.todolist.repository.MyTodo;

import java.util.List;

@Service
public class MyTodoService {

    @Autowired
    private MyTodoRepository myTodoRepository;

    // email과 날짜로 해당 사용자의 일정만 조회
    public List<MyTodo> findMyTodosByEmailAndDate(String email, String callendardate) {
        return myTodoRepository.findByEmailAndCallendardate(email, callendardate);
    }
    public void addTodo(String email, String callendardate, String todo) {
    	MyTodo myTodo = new MyTodo();
        myTodo.setEmail(email);
        myTodo.setCallendardate(callendardate);
        myTodo.setTodo(todo);
        myTodoRepository.save(myTodo); // 엔티티 객체로 저장!
    }
    public void updateTodo(Long id, String todo) {
        MyTodo myTodo = myTodoRepository.findById(id).orElse(null);
        if (myTodo != null) {
            myTodo.setTodo(todo);
            myTodoRepository.save(myTodo);
        }
    }
    public void deleteTodo(Long id) {
        myTodoRepository.deleteById(id);
    }
    
}
