package org.accdatabase.central.model;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor@NoArgsConstructor@Getter
@Setter@ToString
public class SynchronizedStatusHistory {
    private String dishOrderId;
    private OrderProcess status;
    private LocalDateTime changedAt;
}

