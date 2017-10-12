package com.discoverychurch.icon.domain;

import org.boon.json.JsonFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class IconEnvelopeTest {

    @Test
    public void test() throws Exception {
        IconAuth auth = IconAuth.builder().session("session").build();

        Map<String,String> params = new HashMap<>();
        IconRequest<Object> iconRequest = IconRequest.builder()
                .action("memberships")
                .section("household")
                .action("create")
                .build();

        params.put("first_name","First");
        params.put("last_name","Last");

        iconRequest.setData(params);



        IconEnvelope ie = IconEnvelope.builder()
                .auth(auth)
                .request(iconRequest)
                .build();

        System.out.println(JsonFactory.toJson(ie));

    }





}