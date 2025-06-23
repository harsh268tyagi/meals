package com.assignment.meal.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "documenu")
@Data
public class DocumenuProperties {

    private String apiKey;


    private String apiUrl;
}
