
package co.edu.usco.TM.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {
    
    @GetMapping("/")
    public String showIndex() {
        return "index";
    }
}
