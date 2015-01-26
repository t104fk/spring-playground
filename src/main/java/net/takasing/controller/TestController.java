package net.takasing.controller;

import net.takasing.command.TestCommand;
import net.takasing.enums.Blood;
import net.takasing.enums.Gender;
import net.takasing.model.Attribute;
import net.takasing.model.JsonApiEntitiy;
import net.takasing.model.User;
import net.takasing.service.HttpService;
import net.takasing.service.json.JsonService;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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
    @Autowired
    private JsonService jsonService;
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
    @RequestMapping(value = {"/templateByJson"}, method = RequestMethod.POST)
    public String getTemplateByJson(@ModelAttribute("command") TestCommand command,
                                    @RequestBody String posted) {
        command.setName("payload=" + posted);
        JsonApiEntitiy entity = jsonService.deserialize(posted, JsonApiEntitiy.class);
        LOGGER.info("entity=" + entity);
        command.setUser(entity.getUser());
        command.setAttribute(entity.getAttribute());
        return "templateByJson";
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
    @RequestMapping(value = {"/api/json"}, method = RequestMethod.POST)
    public void postAndJson(@RequestParam("username") String username,
                                    @RequestParam("mailAddress") String mailAddress,
                                    @RequestParam("gender") int gender,
                                    @RequestParam("blood") int blood,
                                  HttpServletResponse response) throws Exception {
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(8080)
                .setPath("/templateByJson")
                .build();
        HttpPost request = new HttpPost(uri);

        User user = new User();
        user.setUsername(username);
        user.setMailAddress(mailAddress);
        Attribute attribute = new Attribute();
        attribute.setGender(Gender.getGender(gender));
        attribute.setBlood(Blood.getBlood(blood));
        JsonApiEntitiy jsonApiEntitiy = new JsonApiEntitiy();
        jsonApiEntitiy.setUser(user);
        jsonApiEntitiy.setAttribute(attribute);

        StringEntity stringEntity = new StringEntity(jsonService.serialize(jsonApiEntitiy));
        request.setHeader("Content-type", ContentType.APPLICATION_JSON.getMimeType());
        request.setEntity(stringEntity);

        String entity = httpService.request(request);
        httpService.response(response, entity);
    }
}
