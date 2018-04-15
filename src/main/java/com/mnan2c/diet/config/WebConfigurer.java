package com.mnan2c.diet.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mnan2c.diet.utils.Jsr310DateTimeSerializer;
import com.mnan2c.diet.utils.Jsr310LocalDateDeserializer;

@Component
public class WebConfigurer extends WebMvcConfigurerAdapter
		implements ServletContextInitializer, EmbeddedServletContainerCustomizer {

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {

	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

	}

	@Bean
	public ObjectMapper objectMapper() {
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(OffsetDateTime.class, Jsr310DateTimeSerializer.INSTANCE);
		javaTimeModule.addSerializer(ZonedDateTime.class, Jsr310DateTimeSerializer.INSTANCE);
		javaTimeModule.addSerializer(LocalDateTime.class, Jsr310DateTimeSerializer.INSTANCE);
		javaTimeModule.addSerializer(Instant.class, Jsr310DateTimeSerializer.INSTANCE);
		javaTimeModule.addDeserializer(LocalDate.class, Jsr310LocalDateDeserializer.INSTANCE);
		ObjectMapper mapper = new ObjectMapper()//
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)//
				.registerModule(javaTimeModule)//
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}
}
