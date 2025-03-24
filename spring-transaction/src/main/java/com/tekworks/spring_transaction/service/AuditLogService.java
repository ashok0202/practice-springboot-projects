package com.tekworks.spring_transaction.service;

import com.tekworks.spring_transaction.entity.AuditLog;
import com.tekworks.spring_transaction.entity.Order;
import com.tekworks.spring_transaction.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAuditDetails(Order order, String action) {
        AuditLog auditLog=new AuditLog();
        auditLog.setOrderId(order.getId());
        auditLog.setAction(action);
        auditLog.setTimestamp(java.time.LocalDateTime.now());

        auditLogRepository.save(auditLog);

    }
}
