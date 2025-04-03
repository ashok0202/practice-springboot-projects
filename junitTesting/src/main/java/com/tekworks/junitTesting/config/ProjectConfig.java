package com.tekworks.junitTesting.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProjectConfig {

    @Bean
    public Cloudinary getCloudinary(){

        Map map=new HashMap();
        map.put("cloud_name","drojynnwr");
        map.put("api_key","152484248174981");
        map.put("api_secret","np95TwmTqCZpX3eIlfH98MohVao");
        map.put("secure",true);

        return new Cloudinary(map);

    }
}
