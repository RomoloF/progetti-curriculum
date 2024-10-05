package it.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Restituisce la pagina di login
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin"; // Restituisce la pagina admin
    }

    @GetMapping("/user")
    public String user() {
        return "user"; // Restituisce la pagina utente
    }

    @GetMapping("/anonimo")
    public String anonimo() {
        return "anonimo"; // Restituisce la pagina anonima
    }
}
