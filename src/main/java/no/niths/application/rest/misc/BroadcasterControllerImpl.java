package no.niths.application.rest.misc;

import java.io.EOFException;

import javax.servlet.http.HttpServletResponse;

import no.niths.common.AppNames;
import no.niths.common.SecurityConstants;
import no.niths.domain.misc.Email;
import no.niths.services.interfaces.MailSenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sun.mail.smtp.SMTPAddressFailedException;

@Controller
@RequestMapping(AppNames.BROADCAST)
public class BroadcasterControllerImpl {

    @Autowired
    private MailSenderService service;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize(SecurityConstants.ADMIN_SR_FADDER_LEADER)
    public void broadcast(@RequestBody Email email) {
        String[] recipientAddresses = email.getRecipientAddresses();

        for (String recipientAddress : recipientAddresses) {
            service.composeAndSend(
                    recipientAddress,
                    "NITHs",
                    email.getSubject(),
                    "Hei.\n\nDette er " + email.getSenderName() + ".\n\n"
                        + email.getBody());            
        }
    }

    /**
     * 
     * @param e The exception thrown
     * @param res The response
     */
    @ExceptionHandler(EOFException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void endOfFile(EOFException e, HttpServletResponse res) {
        res.setHeader("Error", "Wrong input");
    }

    /**
     * 
     * @param e The exception thrown
     * @param res The response
     */
    @ExceptionHandler(SMTPAddressFailedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleNonexistentEmailAddresses(SMTPAddressFailedException e,
            HttpServletResponse res) {
        res.setHeader("Error", "Email addresse(s) don't exist");
    }
}