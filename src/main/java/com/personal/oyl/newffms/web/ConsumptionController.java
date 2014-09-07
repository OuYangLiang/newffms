package com.personal.oyl.newffms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/consumption")
public class ConsumptionController {
    
    
    @RequestMapping("/initAdd")
    public String initAdd() {
        return "consumption-add";
    }
    
}
