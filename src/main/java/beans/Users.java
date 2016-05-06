package beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import ochesnokov.general.WorkDataBase;

@Entity
@Table(name = "dbo.tCltCltRelation")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CltCltRelationID")
	private long id;

	@Column(name = "f_DepartmentID")
	private int departament;

	@Column(name = "UserID")
	private int user;

	@Column(name = "Name")
	private String name;

	@Column(name = "NetName")
	private String netName;

	@Column(name = "jobTitle")
	private String jobTitle;

	@Transient
	private String email;

	@Column(name = "f_ChildClientID")
	private int onName;

	@Transient
	private List<Task> taskForUser = new ArrayList<>();

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

	public int getDepartament() {
		return departament;
	}

	public void setDepartament(int departament) {
		this.departament = departament;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Task> getTaskForUser() {
		return taskForUser;
	}

	public void setTaskForUser(List<Task> taskForUser) {
		this.taskForUser = taskForUser;
	}

	public void getAllTaskFromUser(List<Task> allTask, List<Task> getTaskFromUser) {
		Set<Task> set = new HashSet<>();
		set.addAll(getTaskFromUser);

		for (Task tu : set) {
			for (Task all : allTask) {
				if (all.getParentTask() != 0) {
					if (!set.contains(all)) {
						if (all.getParentTask() == tu.getId()) {
							getTaskFromUser.add(all);
							getAllTaskFromUser(allTask, getTaskFromUser);
						}
					}
				}
			}
		}

	}

	public void getTaskFromUser(int myUser) {
		WorkDataBase wdb = WorkDataBase.getInstance();
		taskForUser = wdb.em.createNativeQuery(
				"SELECT appmoduletaskid, [Name], [ParentTaskID] from [dbo].[tResponsiblePerson] rp inner JOIN [dbo].[tAppModuleTask] amt ON amt.[AppModuleTaskID] = [rp].[ObjectID] where [rp].[EmployeeID] = "
						+ myUser + " and [rp].[RoleID] = 3",
				Task.class).getResultList();

	}

}
