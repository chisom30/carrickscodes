package com.chisom.redditApplication.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Collections;

@Service
@AllArgsConstructor
@Configuration
public class MailContentBuilder implements WebMvcConfigurer {

     private final TemplateEngine templateEngine;

    String build(String message){
        Context context = new Context();
        context.setVariable(message, "message");
        return templateEngine.process("mailTemplate", context);
    }
    private static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

//    @Bean
//    @Primary
//    public TemplateEngine emailTemplateEngine() {
//        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        // Resolver for HTML emails (except the editable one)
//        templateEngine.addTemplateResolver(emailTemplateResolver());
//
//        return templateEngine;
//    }

    private ITemplateResolver emailTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setResolvablePatterns(Collections.singleton("*"));
        templateResolver.setPrefix("/mailTemplate/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        templateResolver.setCacheable(false);

        return templateResolver;
    }
}

