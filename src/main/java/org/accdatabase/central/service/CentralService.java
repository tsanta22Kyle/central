package org.accdatabase.central.service;


import lombok.RequiredArgsConstructor;
import org.accdatabase.central.model.BestSales;
import org.accdatabase.central.model.DishSold;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CentralService {
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<Object> getBestSales(int top) {
        List<DishSold> dishSolds = (List<DishSold>) restTemplate.getForObject("http://localhost:8080/salesPoint/sales", Object.class);
        BestSales bestSales = new BestSales();
        bestSales.setUpdatedAt(LocalDateTime.now());

        if (top < 0) {
            return new ResponseEntity<>("Top must be a positive number", HttpStatus.BAD_REQUEST);
        }
        if (dishSolds == null) {
            return new ResponseEntity<>("Best sales not available, try later!", HttpStatus.BAD_REQUEST);
        }
        if (top > 0 && dishSolds.size() > top) {
            dishSolds = dishSolds.subList(0, top);
        }

        bestSales.setDishSolds(dishSolds);

        return ResponseEntity.ok().body(bestSales);
    }
}
