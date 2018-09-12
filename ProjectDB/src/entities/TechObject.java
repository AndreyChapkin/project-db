package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;
import entities.User;
import static javax.persistence.TemporalType.TIMESTAMP;
import entities.TechObject;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Object")
@NamedQuery(name = "AllObjects", query = "select o from TechObject o")
public class TechObject implements Serializable {

	private static final long serialVersionUID = 1L;

	public TechObject() {
	}

	public TechObject(User creator,TechObject  assocObj,
						TechObject  parentObj, Date createTime, Date editTime) {
		this.status = "В разработке";
		this.creator = creator;
		this.editor = null;
		this.assocObj = assocObj;
		this.parentObj = parentObj;
		this.createTime = createTime;
		this.editTime = editTime;
		this.linkedObjs = new ArrayList<TechObject>();
		this.childObjs = new ArrayList<TechObject>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OBJ_ID")
	private int objId;

	@Column(name = "STATUS")
	private String status;
	
	@Temporal(TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Temporal(TIMESTAMP)
	@Column(name = "EDIT_TIME")
	private Date editTime;

	@ManyToOne
	@JoinColumn(name = "CREATOR_ID")
	private User creator;
	
	@ManyToOne
	@JoinColumn(name = "EDITOR_ID")
	private User editor;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "ASSOC_OBJ_ID")
	private TechObject assocObj;
	
	@OneToMany(mappedBy = "assocObj", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	@OrderColumn(name = "LINKED_OBJ_INDEX")
	private List<TechObject> linkedObjs;

	@ManyToOne
	@JoinColumn(name = "PARENT_OBJ_ID")
	private TechObject parentObj;

	@OneToMany(mappedBy = "parentObj", orphanRemoval=true)
	private Collection<TechObject> childObjs;
	

	public int getObjId() {
		return objId;
	}

	public void setObjId(int param) {
		this.objId = param;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String param) {
		this.status = param;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User param) {
		this.creator = param;
	}

	public User getEditor() {
		return editor;
	}

	public void setEditor(User editor) {
		this.editor = editor;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date param) {
		this.createTime = param;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date param) {
		this.editTime = param;
	}

	public TechObject getAssocObj() {
		return assocObj;
	}

	public void setAssocObj(TechObject assocObj) {
		this.assocObj = assocObj;
	}

	public List<TechObject> getLinkedObjs() {
		return linkedObjs;
	}

	public void setLinkedObjs(List<TechObject> assocObjs) {
		this.linkedObjs = assocObjs;
	}

	public TechObject getParentObj() {
	    return parentObj;
	}

	public void setParentObj(TechObject param) {
	    this.parentObj = param;
	}

	public Collection<TechObject> getChildObjs() {
	    return childObjs;
	}

	public void setChildObjs(Collection<TechObject> param) {
	    this.childObjs = param;
	}

}