package com.mnan2c.diet.config;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import com.mnan2c.diet.utils.Jsr310DateConverters.DateToLocalDateConverter;
import com.mnan2c.diet.utils.Jsr310DateConverters.DateToLocalDateTimeConverter;
import com.mnan2c.diet.utils.Jsr310DateConverters.DateToZonedDateTimeConverter;
import com.mnan2c.diet.utils.Jsr310DateConverters.LocalDateTimeToDateConverter;
import com.mnan2c.diet.utils.Jsr310DateConverters.LocalDateToDateConverter;
import com.mnan2c.diet.utils.Jsr310DateConverters.ZonedDateTimeToDateConverter;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class DatabaseConfiguration extends AbstractMongoConfiguration {
	@Inject
	private ObjectProvider<MongoClientOptions> optionsProvider;

	@Inject
	private Environment environment;

	private String host;
	private String uri;
	private Integer port = null;
	private String database;
	private String username;
	private char[] password;
	private String authenticationDatabase;
	public static final int DEFAULT_PORT = 27017;
	public static final String DEFAULT_URI = "mongodb://localhost/diet";
	public static final String DB_NAME = "diet";
	
	@Override
	@Bean
	public CustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(DateToZonedDateTimeConverter.INSTANCE);
		converters.add(ZonedDateTimeToDateConverter.INSTANCE);
		converters.add(DateToLocalDateConverter.INSTANCE);
		converters.add(LocalDateToDateConverter.INSTANCE);
		converters.add(DateToLocalDateTimeConverter.INSTANCE);
		converters.add(LocalDateTimeToDateConverter.INSTANCE);
		return new CustomConversions(converters);
	}

	@Override
	protected String getDatabaseName() {
		return DB_NAME;
	}

	@Override
	public Mongo mongo() throws Exception {
		return createMongoClient(optionsProvider.getIfAvailable(), environment);
	}

	public MongoClient createMongoClient(MongoClientOptions options, Environment environment)
			throws UnknownHostException {
		try {
			Integer embeddedPort = getEmbeddedPort(environment);
			if (embeddedPort != null) {
				return createEmbeddedMongoClient(options, embeddedPort);
			}
			return createNetworkMongoClient(options);
		} finally {
			clearPassword();
		}
	}

	private Integer getEmbeddedPort(Environment environment) {
		if (environment != null) {
			String localPort = environment.getProperty("local.mongo.port");
			if (localPort != null) {
				return Integer.valueOf(localPort);
			}
		}
		return null;
	}

	private MongoClient createEmbeddedMongoClient(MongoClientOptions options, int port) {
		if (options == null) {
			options = MongoClientOptions.builder().build();
		}
		String host = this.host == null ? "localhost" : this.host;
		return new MongoClient(Collections.singletonList(new ServerAddress(host, port)),
				Collections.<MongoCredential>emptyList(), options);
	}

	private MongoClient createNetworkMongoClient(MongoClientOptions options) {
		if (hasCustomAddress() || hasCustomCredentials()) {
			if (this.uri != null) {
				throw new IllegalStateException(
						"Invalid mongo configuration, " + "either uri or host/port/credentials must be specified");
			}
			if (options == null) {
				options = MongoClientOptions.builder().build();
			}
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			if (hasCustomCredentials()) {
				String database = this.authenticationDatabase == null ? getMongoClientDatabase()
						: this.authenticationDatabase;
				credentials.add(MongoCredential.createCredential(this.username, database, this.password));
			}
			String host = this.host == null ? "localhost" : this.host;
			int port = this.port != null ? this.port : DEFAULT_PORT;
			return new MongoClient(Collections.singletonList(new ServerAddress(host, port)), credentials, options);
		}
		// The options and credentials are in the URI
		return new MongoClient(new MongoClientURI(determineUri(), builder(options)));
	}

	public void clearPassword() {
		if (this.password == null) {
			return;
		}
		for (int i = 0; i < this.password.length; i++) {
			this.password[i] = 0;
		}
	}
	
	private boolean hasCustomAddress() {
		return this.host != null || this.port != null;
	}
	
	private boolean hasCustomCredentials() {
		return this.username != null && this.password != null;
	}
	
	public String getMongoClientDatabase() {
		if (this.database != null) {
			return this.database;
		}
		return new MongoClientURI(determineUri()).getDatabase();
	}
	
	public String determineUri() {
		return (this.uri != null ? this.uri : DEFAULT_URI);
	}
	
	private Builder builder(MongoClientOptions options) {
		if (options != null) {
			return MongoClientOptions.builder(options);
		}
		return MongoClientOptions.builder();
	}
}
