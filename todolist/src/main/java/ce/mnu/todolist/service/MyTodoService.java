package ce.mnu.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ce.mnu.todolist.repository.MyTodoRepository;
import ce.mnu.todolist.repository.MyTodo;

import java.sql.Date;
import java.util.List;

@Service
public class MyTodoService {

    @Autowired
    private MyTodoRepository myTodoRepository;

    // email과 날짜로 해당 사용자의 일정만 조회
    public List<MyTodo> findMyTodosByEmailAndDate(String email, Date callendardate) {
        return myTodoRepository.findByEmailAndCallendardate(email, callendardate);
    }

}
