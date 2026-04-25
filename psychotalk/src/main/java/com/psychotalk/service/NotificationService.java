package com.psychotalk.service;

import com.psychotalk.exception.customExceptions.AccessDeniedException;
import com.psychotalk.model.Notification;
import com.psychotalk.model.account.Account;
import com.psychotalk.model.enums.NotificationStatus;
import com.psychotalk.repository.AccountRepo;
import com.psychotalk.repository.NotificationRepo;
import com.psychotalk.security.SecurityUtils;
import com.psychotalk.service.Utils.CurrentRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;
    @Autowired
    private CurrentRoleService currentRoleService;
    @Autowired
    private AccountRepo accountRepo;

    public List<Notification> getAllNotification(){
       Account  account = currentRoleService.getCurrentAccount();
       return notificationRepo.findByAccount(account);
    }

    public void addNotification(Notification notification){
        notificationRepo.save(notification);
    }

    public Notification markAsRead(Long id) {
        Notification notification = notificationRepo.findById(id).orElseThrow(RuntimeException::new);
        if(notification.getStatus() == NotificationStatus.READ) return notification;
        if(!currentRoleService.getCurrentAccount().equals(notification.getAccount())) throw new AccessDeniedException();

        notification.setStatus(NotificationStatus.READ);
        return notificationRepo.save(notification);
    }
}
