package com.viettq.querydsldynamicquery;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class CallingGraphqlController {
    @GetMapping("/test")
    public Student test() throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://localhost:8080/graphql");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("query", "{\\n  getStudentByFilter({field: \"id\", operator: \"EQ\", values: {value: \"1\", type: \"LONG\"}}) {\\n    age\\n    email\\n    dob\\n  }\\n}")
                .build();
        request.setURI(uri);
        HttpResponse response = client.execute(request);
        response.getEntity();
        return null;
    }
}
