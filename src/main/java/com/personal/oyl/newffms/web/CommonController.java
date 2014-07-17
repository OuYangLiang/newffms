package com.personal.oyl.newffms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
    
    @RequestMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}
