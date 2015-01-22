package net.takasing.controller;

import net.takasing.command.TestCommand;
import net.takasing.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author toyofuku_takashi
 */
@Controller
public class TestController {
    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    public String get() {
        return "test";
    }
    @RequestMapping(value = {"/template"}, method = RequestMethod.GET)
    public String getTemplate(@ModelAttribute("command") TestCommand command) {
        command.setName("test controller.");
        User user = new User();
        user.setUsername("toyo");
        user.setMailAddress("toyo@mail.com");
        command.setUser(user);
        return "templateWithModel";
    }
}
