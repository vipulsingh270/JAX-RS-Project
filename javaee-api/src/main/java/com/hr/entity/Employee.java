package com.hr.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQuery(name="ListEmployees", query="select e from Employee e")
//@NamedQuery(name="getEmployeebyID", query="select e from Employee e where e.employeeId=:id")
@Table(name="EMPLOYEE")
public class Employee implements Serializable{
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private long employeeId;


private String name;

@ManyToOne
@JoinColumn(name="TEAM_ID")
private Team team;

public long getEmployeeId() {
	return employeeId;
}

public void setEmployeeId(long employeeId) {
	this.employeeId = employeeId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Team getTeam() {
	return team;
}

public void setTeam(Team team) {
	this.team = team;
}

@Override
public boolean equals(Object o) {
	if(this == o)
		return true;
	if(o != null || getClass() != o.getClass())
		return false;
	Employee emp = (Employee) o;
	return Objects.equals(getName().toUpperCase(), emp.getName().toUpperCase());
}

@Override
public int hashCode() {
	return Objects.hash(getName().toUpperCase());
}


}
