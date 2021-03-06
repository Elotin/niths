package no.niths.domain.school;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import no.niths.common.constants.DomainConstantNames;
import no.niths.common.constants.ValidationConstants;
import no.niths.domain.Domain;
import no.niths.domain.adapter.JsonCalendarDeserializerAdapter;
import no.niths.domain.adapter.JsonCalendarSerializerAdapter;
import no.niths.domain.adapter.XmlCalendarAdapter;
import no.niths.domain.location.Location;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Domain class for Event
 * 
 * <p>
 * Event has these variables: name = example Eksamen i PG2100, description =
 * example Skriftlig, startTime = example 9:00, endTime = example 12:00, tags =
 * example none
 * </p>
 * <p>
 * And relations too: Committee, Location
 * </p>
 * <p>
 * Holds information about an event. Supports tags, for example all events
 * belonging to fadderuka 2012, gets the tag fadderuka12.
 * </p>
 * 
 */
@XmlRootElement
@Entity
@Table(name = DomainConstantNames.EVENTS)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include = Inclusion.NON_NULL)
public class Event implements Domain {

    @Transient
    private static final long serialVersionUID = 1878727682733503699L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Pattern(
            regexp  = ValidationConstants.REGULAR,
            message = "Invalid name (should be 2 - 80 alphanumeric letters)")
    private String name;

    @Column
    @Pattern(
            regexp  = ValidationConstants.LARGE,
            message = "Invalid desc (should be 2 - 500 alphanumeric letters)")
    private String description;

    @Column(name = "startTime")
    @Temporal(TemporalType.TIMESTAMP)
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(XmlCalendarAdapter.class)
    @XmlElement(name = "starttime")
    private Calendar startTime;

    @Column(name = "endTime")
    @Temporal(TemporalType.TIMESTAMP)
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(XmlCalendarAdapter.class)
    @XmlElement(name = "endtime")
    private Calendar endTime;

    @Column(name = "tags")
    private String tags;

    @JsonIgnore
    @XmlTransient
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Committee.class)
    @Cascade(CascadeType.ALL)
    @JoinTable(name = "committees_events", joinColumns = @JoinColumn(name = "events_id"), inverseJoinColumns = @JoinColumn(name = "committees_id"))
    private List<Committee> committees = new ArrayList<Committee>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "events_location", joinColumns = @JoinColumn(name = "events_id"), inverseJoinColumns = @JoinColumn(name = "location_id"))
    @Cascade(value = CascadeType.ALL)
    private Location location;

    public Event() {
        this(null, null, null, null);
        setCommittees(null);
        setLocation(null);
    }

    public Event(String name) {
        this.name = name;
    }

    public Event(Long id, String name, String description,
            GregorianCalendar startTime, GregorianCalendar endTime) {
        setId(id);
        setName(name);
        setDescription(description);
        setEndTime(endTime);
        setStartTime(startTime);
    }

    public Event(String name, String description, GregorianCalendar startTime,
            GregorianCalendar endTime) {
        setName(name);
        setDescription(description);
        setEndTime(endTime);
        setStartTime(startTime);
    }

    public Event(String name, String description, GregorianCalendar startTime,
            GregorianCalendar endTime, String tags) {
        setName(name);
        setDescription(description);
        setEndTime(endTime);
        setStartTime(startTime);
        setTags(tags);
    }

    public Event(Long eventId) {
        setId(eventId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Event)) {
            return false;
        }
        Event s = (Event) obj;
        return s == this ? true : s.getId() == id ? true : false;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return description == null && name == null && id == null
                && endTime == null && startTime == null;
    }

    @Override
    public String toString() {

        return String.format("[%s][%s][%s][%s][%s]", id, name, description,
                tags, startTime, endTime);
    }

    @JsonSerialize(using = JsonCalendarSerializerAdapter.class)
    public Calendar getEndTime() {
        return endTime;
    }

    @JsonDeserialize(using = JsonCalendarDeserializerAdapter.class)
    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    @JsonSerialize(using = JsonCalendarSerializerAdapter.class)
    public Calendar getStartTime() {
        return startTime;
    }

    @JsonDeserialize(using = JsonCalendarDeserializerAdapter.class)
    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<Committee> getCommittees() {
        return committees;
    }

    public void setCommittees(List<Committee> committees) {
        this.committees = committees;
    }

    @JsonSerialize(as = Location.class)
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
