package net.takasing.controller;

import net.takasing.command.TestCommand;
import net.takasing.model.User;
import net.takasing.service.HttpService;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * @author toyofuku_takashi
 */
@Controller
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private HttpService httpService;
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
    @RequestMapping(value = {"/templateWithParam"}, method = RequestMethod.GET)
    public String getTemplateWithParam(@ModelAttribute("command") TestCommand command,
                                       @RequestParam("username") String username,
                                       @RequestParam("mailAddress") String mailAddress) {
        command.setName("test controller.");
        User user = new User();
        user.setUsername(username);
        user.setMailAddress(mailAddress);
        command.setUser(user);
        return "templateWithModel";
    }
    @RequestMapping(value = {"/api/input"}, method = RequestMethod.GET)
    public String getFromApi() {
        return "form";
    }
    @RequestMapping(value = {"/simple/input"}, method = RequestMethod.POST)
    public String post(@RequestBody String postPayload,
                                  HttpServletRequest request) {
        LOGGER.info("@RequestBody's String payload=" + postPayload);
        request.setAttribute("payload", postPayload);
        return "result";
    }
    @RequestMapping(value = {"/api/input"}, method = RequestMethod.POST)
    public void postAndGetFromApi(@RequestParam("username") String username,
                                    @RequestParam("mailAddress") String mailAddress,
                                  HttpServletResponse response) throws Exception {
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(8080)
                .setPath("/templateWithParam")
                .addParameter("username", username)
                .addParameter("mailAddress", mailAddress)
                .build();
        String entity = httpService.request(new HttpGet(uri));
        httpService.response(response, entity);
    }
}
