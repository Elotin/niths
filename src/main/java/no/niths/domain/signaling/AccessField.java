package no.niths.domain.signaling;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import no.niths.common.AppConstants;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private AccessPoint accessPoint;

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
}