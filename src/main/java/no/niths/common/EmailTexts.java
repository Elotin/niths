package no.niths.common;

import no.niths.domain.Application;
import no.niths.domain.Developer;
/**
 * Class that holds email templates
 * used in MailService
 *
 */
public final class EmailTexts {
	
	public static String getDeveloperConfirmationBody(Developer dev){
		String linkUrl = AppConstants.NITHS_BASE_DOMAIN + "register/enable/" + dev.getDeveloperKey();
		String body = "<h2>Congratulations, you are almost there!</h2>"+
						"<br /><br />" +
						//"Your developer token is: " + dev.getDeveloperToken() +
						//"<br /><br />" +
						"Your developer key is: " + dev.getDeveloperKey() +
						"<br /><br />" +
						"<a href='"+ linkUrl +"'>Click to enable!</a>" +
						"<br /><br />" +
						"Link not working? Paste this into your favourite browser:" + 
						"<br /><br />" +
						linkUrl;
		return body;
	}
	public static String getDeveloperEnabledBody(Developer dev){
		String body = "<h2>Congratulations, you are now enabled!</h2>"+
				"<br /><br />" +
				"Your new developer token is: " + dev.getDeveloperToken() +
				"<br /><br />" +
				"Your new developer key is: " + dev.getDeveloperKey() +
				"<br /><br />" +
				"Use this in all future requests." +
				"<br /><br />";
		return body;
	}
	public static String getApplicationEnabledBody(Developer dev, Application app){
		String body = "<h2>Congratulations, your application is now enabled!</h2>"+
				"<br /><br />" +
				"Your developer token is: " + dev.getDeveloperToken() +
				"<br /><br />" +
				"Your developer key is: " + dev.getDeveloperKey() +
				"<br /><br />" +
				"Your application token is: " + app.getApplicationToken() +
				"<br /><br />" +
				"Your application key is: " + app.getApplicationKey() +
				"<br /><br />" +
				"Use this in all future requests." +
				"<br /><br />";
		return body;
	}
	
	public static String getAddedAppToDevelioperBody(Application app){
		String body = "<h2>Your application is ready to use!</h2>" +
					"<br /><br />" +
					"Your application token is: " + app.getApplicationToken() + 
					"<br /><br />" +
					"Read the API for further instructions...";
		return body;
	}
}
