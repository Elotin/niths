package no.niths.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import no.niths.common.AppConstants;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Class that holds information about a student orientation group 
 *
 *
 */
@XmlRootElement
@Entity
@Table(name =AppConstants.FADDER_GROUP)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)  
public class FadderGroup implements Serializable {

	@Transient
	private static final long serialVersionUID = -3434555328809873472L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(name="group_number")
	private Integer groupNumber;
	
	@ManyToMany(fetch = FetchType.LAZY,targetEntity = Student.class)
	@Cascade(CascadeType.ALL)
//	@JoinTable(
//			name="leaders_fadder_groups",
//					 joinColumns = @JoinColumn(name = "leaders_id")
//            ,inverseJoinColumns = @JoinColumn(name = "students_id"),
//		            uniqueConstraints={@UniqueConstraint(columnNames ={"leaders_id", "students_id"})} )
	private List<Student> leaders = new ArrayList<Student>();
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Student.class)
	@Cascade(CascadeType.ALL)
	private List<Student> fadderChildren = new ArrayList<Student>();
	
	
	public FadderGroup() {
		this(0);
	}
	
	public FadderGroup(int groupId) {
		this.groupNumber = groupId;
	}
	
	public List<Student> getLeaders() {
		return leaders;
	}

	public void setLeaders(List<Student> leaders) {
		this.leaders = leaders;
	}

	public List<Student> getFadderChildren() {
		return fadderChildren;
	}

	public void setFadderChildren(List<Student> fadderChildren) {
		this.fadderChildren = fadderChildren;
	}


	public Integer getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(Integer groupId) {
		this.groupNumber = groupId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FadderGroup)) {
			return false;
		}
		FadderGroup s = (FadderGroup) obj;
		return s == this ? true : s.getId() == id ? true : false;
	}

}