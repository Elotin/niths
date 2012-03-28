package no.niths.domain.adapter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import no.niths.common.AppConstants;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonDateDeserializerAdapter extends JsonDeserializer<Date>{
	private Logger logger = LoggerFactory
			.getLogger(JsonDateDeserializerAdapter.class);
	private DateFormat df = new SimpleDateFormat(AppConstants.DATE_FORMAT);
	
	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		try {
			return df.parse(jp.getText());
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
		return null;
	}

}