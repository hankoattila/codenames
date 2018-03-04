package com.codenames.attilahanko.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ErrorControllerImpl implements ErrorController {

    private String errorPath = "/error";

    @RequestMapping(value = "/error")
    public String error() {
        return "error";
    }


    @Override
    public String getErrorPath() {
        return errorPath;
    }
}
