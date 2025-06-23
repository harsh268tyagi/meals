package com.assignment.meal.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "edamam")
@Data
public class EdamamProperties {

    private  String url;

    private String appId;

    private String appKey;

    private String accountUser;

}
