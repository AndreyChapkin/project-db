package entities;

import java.io.Serializable;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import entities.TechObject;
import entities.Message;

@Entity
@Table(name = "Tech_User")
@NamedQueries({
				@NamedQuery(name = "AllUsers", query = "select u from User u"),
				@NamedQuery(name = "UserWithSurname", query = "select u from User u where u.surname = :surname")
})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public User() {
	}

	public User(String name, String surname, String post, String password) {
		this.name = name;
		this.surname = surname;
		this.post = post;
		this.password = password;
		this.createdObjects = new ArrayList<TechObject>();
		this.setEditedObjects(new ArrayList<TechObject>());
		this.setOutMessages(new ArrayList<Message>());
		this.inMessages = new ArrayList<Message>();
		this.editRight = true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private int userId;

	@Column(name = "NAME")
	private String name;

	@OneToMany(mappedBy = "creator")
	private Collection<TechObject> createdObjects;

	@OneToMany(mappedBy = "editor")
	private Collection<TechObject> editedObjects;

	@Column(name = "SURNAME")
	private String surname;

	@Column(name = "PATRONYMIC")
	private String patronymic;

	@Column(name = "POST")
	private String post;

	@Column(name = "EDIT_RIGHT")
	private Boolean editRight;

	@Column(name = "COMPANY_NAME")
	private String companyName;

	@OneToMany(mappedBy = "sender")
	@OrderColumn(name="OUT_MES_INDEX")
	private List<Message> outMessages;

	@OneToMany(mappedBy = "recipient")
	@OrderColumn(name="IN_MES_INDEX")
	private List<Message> inMessages;

	private String password;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int param) {
		this.userId = param;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

	public Collection<TechObject> getCreatedObjects() {
		return createdObjects;
	}

	public void setCreatedObjects(Collection<TechObject> param) {
		this.createdObjects = param;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String param) {
		this.surname = param;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String param) {
		this.patronymic = param;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String param) {
		this.post = param;
	}

	public Boolean getEditRight() {
		return editRight;
	}

	public void setEditRight(Boolean param) {
		this.editRight = param;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String param) {
		this.companyName = param;
	}

	public List<Message> getInMessages() {
		return inMessages;
	}

	public void setInMessages(List<Message> param) {
		this.inMessages = param;
	}

	public Collection<TechObject> getEditedObjects() {
		return editedObjects;
	}

	public void setEditedObjects(Collection<TechObject> editedObjects) {
		this.editedObjects = editedObjects;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String param) {
		this.password = param;
	}

	public List<Message> getOutMessages() {
		return outMessages;
	}

	public void setOutMessages(List<Message> outMessages) {
		this.outMessages = outMessages;
	}

}
