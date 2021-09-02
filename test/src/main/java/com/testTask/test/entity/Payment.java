package com.testTask.test.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;

    @Column(name = "account", nullable = false)
    private Long account;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "command", nullable = false)
    private String command;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}
