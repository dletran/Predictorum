package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Season extends DomainEntity {

	private Date startDate;
	private Date finishDate;
	

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
		
	//Relaciones
	private League league;
	private Collection<Round> rounds;
	private Collection<Team> teams;

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy="season")
	public Collection<Round> getRounds() {
		return rounds;
	}

	public void setRounds(Collection<Round> rounds) {
		this.rounds = rounds;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="season")
	public Collection<Team> getTeams() {
		return teams;
	}
	
	public void setTeams(Collection<Team> teams) {
		this.teams = teams;
	}
	
	
		
	
	
	

}
