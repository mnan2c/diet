package com.mnan2c.diet.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Custom Jackson deserializer for transforming a JSON object (using the ISO
 * 8601 date formatwith optional time) to a JSR310 LocalDate object.
 */
public class Jsr310LocalDateDeserializer extends JsonDeserializer<LocalDate> {

	public static final Jsr310LocalDateDeserializer INSTANCE = new Jsr310LocalDateDeserializer();

	private Jsr310LocalDateDeserializer() {
	}

	private static final DateTimeFormatter ISO_DATE_OPTIONAL_TIME;

	static {
		ISO_DATE_OPTIONAL_TIME = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE).optionalStart()
				.appendLiteral('T').append(DateTimeFormatter.ISO_OFFSET_TIME).toFormatter();
	}

	@Override
	public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		switch (parser.getCurrentToken()) {
		case START_ARRAY:
			if (parser.nextToken() == JsonToken.END_ARRAY) {
				return null;
			}

			parser.nextToken();
			int month = parser.getIntValue();

			parser.nextToken();
			int day = parser.getIntValue();

			int year = parser.getIntValue();

			if (parser.nextToken() != JsonToken.END_ARRAY) {
				throw context.wrongTokenException(parser, JsonToken.END_ARRAY, "Expected array to end.");
			}
			return LocalDate.of(year, month, day);

		case VALUE_STRING:
			String string = parser.getText().trim();
			if (string.length() == 0) {
				return null;
			}
			return LocalDate.parse(string, ISO_DATE_OPTIONAL_TIME);
		default:
			throw context.wrongTokenException(parser, JsonToken.START_ARRAY, "Expected array or string.");
		}
	}
}
