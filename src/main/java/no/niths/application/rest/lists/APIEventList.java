package no.niths.application.rest.lists;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import no.niths.common.constants.DomainConstantNames;
import no.niths.domain.APIEvent;
/**
 * Class to contain a list of apiEvents
 */
@XmlRootElement(name = DomainConstantNames.APIEVENTS)
public class APIEventList extends ListAdapter<APIEvent> {

    private static final long serialVersionUID = 2794068992188408350L;

    @SuppressWarnings("unused")
    @XmlElement(name = "apievent")
    private List<APIEvent> data;

    @Override
    public void setData(List<APIEvent> data) {
        this.data = data;
    }
}