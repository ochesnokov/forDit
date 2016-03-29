package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

	@Column(name = "email")
	private String email;

	@Column(name = "f_ChildClientID")
	private int onName;

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

}
