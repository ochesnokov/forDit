package beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dbo.tClientOrder")
public class ClientOrderBean {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ClientOrderId")
	private long id;

	@Column(name = "ProjectID")
	private int projectId;

	@Column(name = "Priority")
	private int priority;

	@Column(name = "Subject")
	private String suject;

	@Column(name = "ModuleId")
	private int moduleId;

	@Column(name = "TaskID")
	private int taskId;

	@Column(name = "InDateTime")
	private Date inDateTime;

	@Column(name = "f_RegUserID")
	private int regUserId;

	@Column(name = "InstrumentID")
	private int instrumentId;

	@Column(name = "Flags")
	private int flags;

	@Column(name = "f_InNameOf")
	private int onName;
	
	@Column(name = "ParentCOID")
	private long parentCo;
	
	@Column(name = "StateID")
	private long stateId;
	
	@Column(name = "Status")
	private int status;
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getStateId() {
		return stateId;
	}

	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

	public long getParentCo() {
		return parentCo;
	}

	public void setParentCo(long parentCo) {
		this.parentCo = parentCo;
	}

	public int getOnName() {
		return onName;
	}

	public void setOnName(int onName) {
		this.onName = onName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getSuject() {
		return suject;
	}

	public void setSuject(String suject) {
		this.suject = suject;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Date getInDateTime() {
		return inDateTime;
	}

	public void setInDateTime(Date inDateTime) {
		this.inDateTime = inDateTime;
	}

	public int getRegUserId() {
		return regUserId;
	}

	public void setRegUserId(int regUserId) {
		this.regUserId = regUserId;
	}

	public int getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

}
