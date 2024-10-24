
package co.edu.usco.TM.web.controller;

import co.edu.usco.TM.persistence.entity.commerce.Maker;
import co.edu.usco.TM.web.client.MakerClient;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private MakerClient makerClient;
    
    @GetMapping("maker/list")
    public String showMakersPanel(Model model) {
        model.addAttribute("makers", makerClient.getMakers());
        return "listMaker";
    }
    
    @GetMapping("/maker/create")
    public String showCreateMakerForm(Model model) {
        model.addAttribute("maker", new Maker());
        return "createMaker";
    }
    
    @PostMapping("/maker/save")
    public String insertMaker(@ModelAttribute Maker maker, Model model) {
        String requestId = UUID.randomUUID().toString();
        makerClient.createMaker(requestId, maker);
        return "redirect:/admin/maker/list";
    }
}
