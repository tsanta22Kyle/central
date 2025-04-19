package org.accdatabase.central.endpoint.rest;

import org.accdatabase.central.model.CalculationMode;
import org.accdatabase.central.model.DurationUnit;

import java.time.LocalDateTime;

public class ProcessingTimeRest {
    private Long dishId;
    private String dishName;
    private Long processingTime;
    private DurationUnit unit;
    private CalculationMode calculationMode;
    private LocalDateTime syncDate;
}
