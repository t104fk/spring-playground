package net.takasing.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * @author takasing
 */
@Controller
public class PartialController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartialController.class);
    private static final String REPLACE_TEXT = "<!--module_placeholder-->";
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping(value = {"/partial"}, method = RequestMethod.GET)
    public String renderPartial(HttpServletResponse response) throws Exception {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("content", new ArrayList<>(Arrays.asList("firstContent", "secondContent")));
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template partialTemplate;
        try {
            partialTemplate = configuration.getTemplate("partial.ftl");
        } catch (Exception e) {
            LOGGER.error("", e);
            throw e;
        }
        StringWriter partialWriter = new StringWriter();
        try {
            partialTemplate.process(dataModel, partialWriter);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw e;
        }
        String partialString = partialWriter.toString();

        Template moduleTemplate;
        try {
            moduleTemplate = configuration.getTemplate("module.ftl");
        } catch (IOException e) {
            LOGGER.error("", e);
            throw e;
        }
        LOGGER.info("module.ftl=" + moduleTemplate.toString());
        StringWriter moduleWriter = new StringWriter();
        dataModel.put("hasModule", true);
        try {
            moduleTemplate.process(dataModel, moduleWriter);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw e;
        }
        LOGGER.info("content of the module.ftl=" + moduleWriter.toString());
        String entity = StringUtils.replace(partialString, REPLACE_TEXT, moduleWriter.toString());
        response(response, entity);
        return null;
    }

    private void response(HttpServletResponse response, String content) throws Exception {
        response.setContentLength(content.getBytes(CharEncoding.UTF_8).length);
        response.setContentType(ContentType.TEXT_HTML.getMimeType());
        response.setCharacterEncoding(CharEncoding.UTF_8);
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(response.getOutputStream(), CharEncoding.UTF_8);
            outputStreamWriter.write(content);
            outputStreamWriter.flush();
        } finally {
            if (outputStreamWriter != null) outputStreamWriter.close();
        }
    }
}
