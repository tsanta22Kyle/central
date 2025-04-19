package org.accdatabase.central.endpoint;


import org.accdatabase.central.model.CalculationMode;
import org.accdatabase.central.model.DurationUnit;
import org.accdatabase.central.service.DishProcessingTimeService;
import org.accdatabase.central.service.SynchronizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProcessingTimeController {

    @Autowired
   private DishProcessingTimeService dishProcessingTimeService;
    @Autowired
   private SynchronizationService synchronizationService;

    //@GetMapping("/dishes/{id}/bestProcessingTime")
   // public ResponseEntity<Object> getProcessingTime(@PathVariable int id, @RequestParam int top, @RequestParam(defaultValue = "SECONDS") DurationUnit durationUnit,@RequestParam(defaultValue = "AVERAGE") CalculationMode calculationMode) {

 //   }
    @PostMapping("dishes/{id}/processingTime")
    public ResponseEntity<Void> syncProcessingTime(
            @PathVariable String id,
            @RequestParam String from,
            @RequestParam String to) {
        try {

        synchronizationService.syncProcessingTime();
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
