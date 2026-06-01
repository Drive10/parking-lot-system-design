package com.parking.repository;

import com.parking.constants.TicketStatus;
import com.parking.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    Optional<TicketEntity> findByTicketUid(String ticketUid);
    List<TicketEntity> findByStatus(TicketStatus status);
    long countByStatus(TicketStatus status);
}
