package ua.kiev.prog.oauth2.loginviagoogle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.oauth2.loginviagoogle.dto.AccountDTO;
import ua.kiev.prog.oauth2.loginviagoogle.dto.TaskDTO;
import ua.kiev.prog.oauth2.loginviagoogle.model.Account;
import ua.kiev.prog.oauth2.loginviagoogle.model.Task;
import ua.kiev.prog.oauth2.loginviagoogle.repos.AccountRepository;
import ua.kiev.prog.oauth2.loginviagoogle.repos.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public void addAccount(AccountDTO accountDTO, TaskDTO... tasks) {
        if (accountRepository.existsByEmail(accountDTO.getEmail()))
            return; // do nothing

        Account account = Account.fromDTO(accountDTO);

        for (TaskDTO t : tasks) {
            Task task = Task.fromDTO(t);
            account.addTask(task);
        }

        accountRepository.save(account);
    }

    @Transactional
    public void addTask(String email, TaskDTO taskDTO) {
        Account account = accountRepository.findByEmail(email);
        Task task = Task.fromDTO(taskDTO);

        account.addTask(task);
        accountRepository.save(account);
    }

    @Transactional
    public List<TaskDTO> getTasks(String email, Pageable pageable) {
        List<TaskDTO> result = new ArrayList<>();
        List<Task> tasks = taskRepository.findByAccountEmail(email, pageable);

        tasks.forEach((x) -> result.add(x.toDTO()));

        return result;
    }

    @Transactional(readOnly = true)
    public Long count(String email) {
        return taskRepository.countByAccountEmail(email);
    }
}
