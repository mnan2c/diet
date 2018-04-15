package com.mnan2c.diet.utils;

import java.io.IOException;
import java.time.temporal.TemporalAccessor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mnan2c.diet.domain.constants.DietConstants;

public final class Jsr310DateTimeSerializer extends JsonSerializer<TemporalAccessor> {

	public static final Jsr310DateTimeSerializer INSTANCE = new Jsr310DateTimeSerializer();

	private Jsr310DateTimeSerializer() {
	}

	@Override
	public void serialize(TemporalAccessor value, JsonGenerator generator, SerializerProvider serializerProvider)
			throws IOException {
		generator.writeString(DietConstants.ISOFormatter.format(value));
	}
}