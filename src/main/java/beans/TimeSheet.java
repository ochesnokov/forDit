package beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="dbo.tTimeSheet")
public class TimeSheet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TimeSheetID")
	private long id;

	@Column(name = "DateIn")
	private Date dateIn;

	@Column(name = "EmployeeID")
	private long userId;
	

	@Column(name = "TimeInWork")
	private double workTime;

	@Column(name = "ClientOrderID")
	private int clientOrderId;
	
	@Column(name = "TaskTypeID")
	private int taskType;

	@Transient
	private String name;
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Date getDateIn() {
		return dateIn;
	}


	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public double getWorkTime() {
		return workTime;
	}


	public void setWorkTime(double workTime) {
		this.workTime = workTime;
	}


	public int getClientOrderId() {
		return clientOrderId;
	}


	public void setClientOrderId(int clientOrderId) {
		this.clientOrderId = clientOrderId;
	}


	public int getTaskType() {
		return taskType;
	}


	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
