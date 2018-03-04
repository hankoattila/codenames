package com.codenames.attilahanko.controller;

import com.codenames.attilahanko.model.player.UserAccount;
import com.codenames.attilahanko.service.UserAccountService;
import com.codenames.attilahanko.utils.Path;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserAccountController {
    private UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping(Path.Web.REGISTER)
    public String serveRegistrationPage(Model model) {
        model.addAttribute("userAccount", new UserAccount());
        return Path.Template.REGISTER;
    }

    @PostMapping(Path.Web.REGISTER)
    public String handleRegistrationPage(@ModelAttribute("userAccount") UserAccount userAccount) {
        userAccountService.save(userAccount);
        return "redirect:" + Path.Template.LOGIN;
    }

}
