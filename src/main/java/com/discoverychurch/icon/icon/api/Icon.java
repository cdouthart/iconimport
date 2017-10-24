package com.discoverychurch.icon.icon.api;

import com.discoverychurch.icon.domain.IconAuth;
import com.discoverychurch.icon.domain.IconEnvelope;
import com.discoverychurch.icon.domain.IconRequest;
import com.discoverychurch.icon.domain.IconResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class Icon {
    private static IconAuth iconAuth;

    public static void setIconAuth(IconAuth iconAuth) {
        Icon.iconAuth = iconAuth;
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static String iconUrl = "https://secure1.iconcmo.com/api/";

    private static MediaType mediaType;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        mediaType = MediaType.parse("application/json");
        iconAuth = new IconAuth();

    }

    public static IconResponse request(IconRequest iconRequest) throws IOException{

        IconEnvelope envelope = IconEnvelope.builder()
                .auth(iconAuth)
                .request(iconRequest)
                .build();


          log.info("Request: "  + objectMapper.writeValueAsString(envelope));
//        IconResponse r = new IconResponse();
//        r.setStatus(200);
//        return r;
        OkHttpClient client = new OkHttpClient();

        String payload = objectMapper.writeValueAsString(envelope);

        Request r = new Request.Builder()
                .url(iconUrl)
                .post(RequestBody.create(mediaType, payload))
                .build();

        Response response =client.newCall(r).execute();



        String body = response.body().string();

        IconResponse iconResponse = objectMapper.readValue(body,IconResponse.class);
        log.info("Response: "  + body);

        iconResponse.setStatus(response.code());
        return iconResponse;
    }
}
