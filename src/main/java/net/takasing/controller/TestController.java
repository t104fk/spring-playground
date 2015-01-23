package net.takasing.controller;

import net.takasing.command.TestCommand;
import net.takasing.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author toyofuku_takashi
 */
@Controller
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
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
    @RequestMapping(value = {"/api/input"}, method = RequestMethod.GET)
    public String getFromApi() {
        return "form";
    }
    @RequestMapping(value = {"/api/input"}, method = RequestMethod.POST)
    public String postAndGetFromApi(@RequestBody String postPayload,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        LOGGER.info("@RequestBody's String payload=" + postPayload);
        request.setAttribute("payload", postPayload);
        return "result";
    }
}
