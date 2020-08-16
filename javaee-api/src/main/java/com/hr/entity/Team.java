package com.hr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@NamedQuery(name="findByTeamName", query="select t from Team t where t.name=:name")
@NamedQuery(name="getAllTeams", query="select t from Team t")
public class Team implements Serializable{

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private long Id;

@NotEmpty(message="Team Name must be Set")
private String name;

//@OneToMany(mappedBy="team")
//private Collection<Employee> employee = new ArrayList<>();


public long getId() {
	return Id;
}

public void setId(long id) {
	Id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name.toUpperCase();
}

//public Collection<Employee> getEmployee() {
//	return employee;
//}
//
//public void setEmployee(Collection<Employee> employee) {
//	this.employee = employee;
//}

@Override
public boolean equals(Object o) {
	if(this == o)
		return true;
	if(o == null || getClass() != o.getClass()) return false;
	Team that = (Team) o;
	return Objects.equals(that.getName().toUpperCase(), getName().toUpperCase());
}

@Override
public int hashCode() {
	return Objects.hash(getName().toUpperCase());
}


}
