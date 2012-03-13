package no.niths.services;

import java.util.List;

import no.niths.domain.APIEvent;
import no.niths.domain.Subject;
import no.niths.infrastructure.interfaces.APIEventRepository;
import no.niths.infrastructure.interfaces.SubjectRepository;
import no.niths.services.interfaces.APIEventService;
import no.niths.services.interfaces.SubjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class APIEventServiceImpl implements APIEventService{

    @Autowired
    private APIEventRepository repo;

    public Long create(APIEvent event) {
        return repo.create(event);
    }

    public APIEvent getById(long id) {
    	return repo.getById(id);
   }

    public List<APIEvent> getAll(APIEvent event) {
    	return repo.getAll(event);
    }

    public void update(APIEvent event) {
        repo.update(event);
    }

    public boolean delete(long id) {
        return repo.delete(id);
    }

	@Override
	public void hibernateDelete(long id) {
		repo.hibernateDelete(id);
	}
}