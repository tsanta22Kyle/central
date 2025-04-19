package org.accdatabase.central.endpoint;

import lombok.RequiredArgsConstructor;
import org.accdatabase.central.service.CentralService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CentralRestController {

    private final CentralService centralService;

    @GetMapping("/bestSales")
    public Object getSales(@RequestParam(name = "top", defaultValue = "5") int top) {
        return centralService.getBestSales(top);
    }
}
