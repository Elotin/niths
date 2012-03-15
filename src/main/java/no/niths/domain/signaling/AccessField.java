package no.niths.domain.signaling;

import java.io.Serializable;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import no.niths.common.AppConstants;
import no.niths.domain.Room;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@Table(name = AppConstants.ACCESS_FIELDS)
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AccessField implements Serializable {

	private static final long serialVersionUID = 2425345455743938142L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "min_range")
	private Integer minRange;

	@Column(name = "max_range")
	private Integer maxRange;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rooms_access_fields",
	    joinColumns =        @JoinColumn(name = "access_field_id"),
	    inverseJoinColumns = @JoinColumn(name = "room_id"))
	private List<Room> rooms = new ArrayList<Room>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "access_fields_access_points",
	    joinColumns =        @JoinColumn(name = "access_field_id"),
	    inverseJoinColumns = @JoinColumn(name = "access_point_id"))
	private AccessPoint accessPoint;

	public AccessField(){
		this(null,null);
	}
	
	public AccessField(Integer min, Integer max){
		setMaxRange(max);
		setMinRange(min);
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setMinRange(Integer minRange) {
		this.minRange = minRange;
	}

	public Integer getMinRange() {
		return minRange;
	}

	public void setMaxRange(Integer maxRange) {
		this.maxRange = maxRange;
	}

	public Integer getMaxRange() {
		return maxRange;
	}

	public AccessPoint getAccessPoint() {
		return accessPoint;
	}

	public void setAccessPoint(AccessPoint accessPoint) {
		this.accessPoint = accessPoint;
	}

	@JsonIgnore
	@XmlTransient
	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
}