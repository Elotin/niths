package no.niths.application.rest.interfaces;

import no.niths.domain.battlestation.Game;

/**
 * Controller for games
 */
public interface GameController extends GenericRESTController<Game> {

    /**
     * Adds a console too a game
     *
     * @param gameId id of the game
     * @param consoleId id of the console
     */
    public void addConsole(Long gameId, Long consoleId);

    /**
     * Removes a console from a game
     *
     * @param gameId id of the game
     */
    public void removeConsole(Long gameId);

    /**
     * Adds a loan too a game
     *
     * @param gameId id of the game
     * @param loanId id of the loan
     */
    public void addLoan(Long gameId, Long loanId);

    /**
     * Removes a loan from a game
     *
     * @param gameId id of the game
     */
    public void removeLoan(Long gameId);
}
