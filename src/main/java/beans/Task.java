package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dbo.tAppModuleTask")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AppModuleTaskID")
	private long id;
	
	
	@Column(name = "Name")
	private String taskName;

	@Column(name = "ParentTaskId")
	private long parentTask;

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getTaskName() {
		return taskName;
	}


	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public long getParentTask() {
		return parentTask;
	}


	public void setParentTask(long parentTask) {
		this.parentTask = parentTask;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (parentTask ^ (parentTask >>> 32));
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (id != other.id)
			return false;
		if (parentTask != other.parentTask)
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		return true;
	}

	
	
	
}
