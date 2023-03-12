package com.example.hangman.configs;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig
{
   @Value("${spring.datasource.url}")
   private String url;

   @Value("${spring.datasource.username}")
   private String username;

   @Value("${spring.datasource.password}")
   private String password;

   @Bean
   public DSLContext dslContext() {
      return DSL.using(url, username, password);
   }
}
