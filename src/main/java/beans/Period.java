package beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dbo.tObjectivePeriod")
public class Period {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ObjectivePeriodID")
	private int id;
	
	@Column(name = "Brief")
	private String brief;

	@Column(name = "IsQuarter")
	private int isQuarter;
	
	@Column(name = "PeriodStartDate")
	private Date periodStartDate;
	
	@Column(name = "PeriodEndDate")
	private Date periodEndDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public int getIsQuarter() {
		return isQuarter;
	}

	public void setIsQuarter(int isQuarter) {
		this.isQuarter = isQuarter;
	}

	public Date getPeriodStartDate() {
		return periodStartDate;
	}

	public void setPeriodStartDate(Date periodStartDate) {
		this.periodStartDate = periodStartDate;
	}

	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}
	
	
}
