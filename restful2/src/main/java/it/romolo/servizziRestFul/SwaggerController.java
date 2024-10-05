package it.romolo.servizziRestFul;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

    @GetMapping("/")
    public String redirectToSwaggerUI() {
        return "redirect:/swagger-ui/index.html";
    }
}
