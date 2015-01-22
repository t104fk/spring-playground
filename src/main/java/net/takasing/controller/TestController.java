package net.takasing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author toyofuku_takashi
 */
@Controller
@RequestMapping(value = {"/test"})
public class TestController {
    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "test";
    }
}
