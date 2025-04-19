package org.accdatabase.central.endpoint;

import org.accdatabase.central.service.SynchronizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SynchronizationRestController {

    @Autowired
    private SynchronizationService service;

    @PostMapping("synchronization")
    public ResponseEntity<Object> synchronize() {
        try {
            return ResponseEntity.ok(service.syncProcessingTime());
        }catch(Exception e) {
            throw new RuntimeException("Synchronization failed", e);
        }
    }


}
