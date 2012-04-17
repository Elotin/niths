package no.niths.application.rest.battlestation;

import no.niths.application.rest.AbstractRESTControllerImpl;
import no.niths.application.rest.RESTConstants;
import no.niths.application.rest.battlestation.interfaces.LoanController;
import no.niths.application.rest.helper.TimeDTO;
import no.niths.application.rest.lists.ListAdapter;
import no.niths.application.rest.lists.LoanList;
import no.niths.common.AppConstants;
import no.niths.common.ValidationHelper;
import no.niths.domain.battlestation.Loan;
import no.niths.services.battlestation.interfaces.ConsoleService;
import no.niths.services.battlestation.interfaces.LoanService;
import no.niths.services.interfaces.*;
import no.niths.services.school.interfaces.StudentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for loan
 */
@Controller
@RequestMapping(AppConstants.LOANS)
public class LoanControllerImpl extends AbstractRESTControllerImpl<Loan> implements LoanController{

    private static final Logger logger = LoggerFactory
            .getLogger(LoanControllerImpl.class);

    @Autowired
    private LoanService loanService;

    @Autowired
    private ConsoleService consoleService;

    @Autowired
    private StudentService studentService;

    private LoanList loanList = new LoanList();

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Loan> getAll(Loan domain) {
        loanList = (LoanList) super.getAll(domain);
        clearRelations();
        return loanList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Loan> getAll(Loan domain, @PathVariable int firstResult, @PathVariable int maxResults) {
        loanList = (LoanList) super.getAll(domain, firstResult, maxResults);
        clearRelations();
        return loanList;
    }

    private void clearRelations(){
        for(Loan loan : loanList){
            loan.setConsoles(null);
            loan.setStudent(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "{loanId}/add/console/{consoleId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK, reason = "Console Added")
    public void addConsole(@PathVariable Long loanId, @PathVariable Long consoleId) {
        loanService.addConsole(loanId, consoleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "{loanId}/remove/console/{consoleId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK, reason = "Console Removed")
    public void removeConsole(@PathVariable Long loanId, @PathVariable Long consoleId) {
        loanService.removeConsole(loanId, consoleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "{loanId}/add/student/{studentId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK, reason = "Student Added")
    public void addStudent(@PathVariable Long loanId, @PathVariable Long studentId) {
        loanService.addStudent(loanId, studentId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "{loanId}/remove/student", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK, reason = "Student Removed")
    public void removeStudent(@PathVariable Long loanId) {
        loanService.removeStudent(loanId);
    }

    @Override
    @RequestMapping(value = "dates", method = RequestMethod.GET, headers = RESTConstants.ACCEPT_HEADER)
    @ResponseBody
    public List<Loan> getLoansBetweenDates(TimeDTO timeDTO) {
        logger.debug(timeDTO +"");
        ValidationHelper.isObjectNull(timeDTO.getStartTime());

        if(timeDTO.getEndTime() != null){
            renewList(loanService.getLoansBetweenDates(timeDTO.getStartTimeCal(), timeDTO.getEndTimeCal()));
        }else{
            renewList(loanService.getLoansBetweenDates(timeDTO.getStartTimeCal(), null));
        }
        return loanList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GenericService<Loan> getService() {
        return loanService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListAdapter<Loan> getList() {
        return loanList;
    }
}
