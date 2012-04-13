package no.niths.domain.battlestation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import no.niths.common.AppConstants;
import no.niths.domain.Domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cascade;

@XmlRootElement
@Entity
@Table(name = AppConstants.CONSOLES)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Console implements Domain {

    @Transient
    private static final long serialVersionUID = 3797521949800376516L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(min = 3, max = 80, message ="The length of the name must be between 3 to 80 letters")
    private String name;

    @Column(name="console_type")
    private String consoleType;

    @Column
    private Integer locker;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Game.class)
    @JoinTable(name = "games_consoles",
            joinColumns = @JoinColumn(name = "consoles_id"),
            inverseJoinColumns = @JoinColumn(name = "games_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Game> games = new ArrayList<Game>();

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Loan.class)
    @JoinTable(name = "loans_consoles",
            joinColumns = @JoinColumn(name = "console_id"),
            inverseJoinColumns = @JoinColumn(name = "loan_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Loan loan;

    public Console(){
        this(null);
        setGames(null);
        setLoan(null);
    }

    public Console(String name){
        setName(name);
    }

    public Console(String name, String consoleType, Integer locker){
        setName(name);
        setConsoleType(consoleType);
        setLocker(locker);
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setConsoleType(String consoleType) {
        this.consoleType = consoleType;
    }

    public String getConsoleType() {
        return consoleType;
    }

    public void setLocker(Integer locker) {
        this.locker = locker;
    }

    public Integer getLocker() {
        return locker;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }

    @JsonSerialize(as=Loan.class)
    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Loan getLoan() {
        return loan;
    }

    @Override
    public boolean equals(Object that) {
        if(!(that instanceof Console)) return false;
        Console console = (Console) that;

        return console == this || console.getId().equals(id);
    }

    @JsonIgnore
    public boolean isEmpty(){
        return (id == null && name == null);
    }

    @Override
    public String toString() {
        return String.format("[%s][%s]", id, name);
    }
}
