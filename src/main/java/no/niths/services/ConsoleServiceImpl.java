package no.niths.services;

import no.niths.domain.Console;
import no.niths.infrastructure.interfaces.ConsoleRepository;
import no.niths.infrastructure.interfaces.GenericRepository;
import no.niths.services.interfaces.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsoleServiceImpl extends AbstractGenericService<Console> implements ConsoleService {

    @Autowired
    private ConsoleRepository consoleRepository;

    public Console getById(long id) {
        Console console = consoleRepository.getById(id);

        if (console != null) {
            console.getGames().size();
        }
        return console;
    }

    @Override
    public GenericRepository<Console> getRepository() {
        return consoleRepository;
    }
}