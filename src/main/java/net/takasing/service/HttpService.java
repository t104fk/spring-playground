package net.takasing.service;

import org.apache.commons.lang3.CharEncoding;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;

/**
 * @author takasing
 */
@Service
public class HttpService {
    @Autowired
    private HttpClient client;

    public String request(HttpUriRequest request) throws Exception {
        HttpResponse response = null;
        try {
            response = client.execute(request);
            return EntityUtils.toString(response.getEntity(), CharEncoding.UTF_8);
        } catch (Exception e) {
            if (request != null) request.abort();
            throw e;
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
    }

    public void response(HttpServletResponse response, String content) throws Exception {
        response.setContentLength(content.getBytes(CharEncoding.UTF_8).length);
        response.setContentType(ContentType.TEXT_HTML.getMimeType());
        response.setCharacterEncoding(CharEncoding.UTF_8);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(response.getOutputStream(), CharEncoding.UTF_8);
        outputStreamWriter.write(content);
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}
