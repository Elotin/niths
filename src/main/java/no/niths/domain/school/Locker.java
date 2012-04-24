package no.niths.domain.school;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import no.niths.common.constants.DomainConstantNames;
import no.niths.domain.Domain;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@XmlRootElement(name = DomainConstantNames.LOCKERS)
@Table(name = DomainConstantNames.LOCKERS)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include = Inclusion.NON_NULL)
public class Locker implements Domain {

    @Transient
    private static final long serialVersionUID = -1430199434685615379L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "locker_number", unique = true)
    @Pattern(regexp = "\\d{1,3}", message = "Invalid locker number")
    @XmlElement(name = "lockernumber")
    private String lockerNumber;

    @JsonSerialize(as = Student.class)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Student.class)
    @JoinTable(
            name               = "students_lockers",
            joinColumns        = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "locker_id"))
    private Student owner;

   public Locker(Long id, String lockerNumber) {
        this.id           = id;
        this.lockerNumber = lockerNumber;
    }

    public Locker() {
        this(null,null);
        setOwner(null);
    }

    public Locker(Long id) {
        this.id = id;
    }

    public Locker(String lockerNumber) {
        this.lockerNumber = lockerNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLockerNumber() {
        return lockerNumber;
    }

    public void setLockerNumber(String lockerNumber) {
        this.lockerNumber = lockerNumber;
    }

    public Student getOwner() {
        return owner;
    }

    public void setOwner(Student owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;

        if (obj instanceof Locker) {
            Locker locker = (Locker) obj;
            equal = locker == this
                    ? true : locker.getId() == id
                    || locker.getLockerNumber() == lockerNumber
                            ? true : false;
        }

        return equal;
    }
}
