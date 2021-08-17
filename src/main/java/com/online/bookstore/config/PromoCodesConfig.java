package com.online.bookstore.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix="promocodes")
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties
public class PromoCodesConfig
{
    List<Promos> promos = new ArrayList<Promos>();

}