package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Tech_Control")
@NamedQuery(name = "AllTechControls", query = "select tc from TechControl tc")
public class TechControl extends TechObject implements Serializable {

	private static final long serialVersionUID = 1L;

	public TechControl() {
	}

	public TechControl(User creator,TechObject  assocObj,
	TechObject  parentObj, Date createTime, Date editTime,
	String instructSign) {
		
		super(creator, assocObj, parentObj, createTime, editTime);
		this.paramControls = new ArrayList<ParamControl>();
		this.intsructSign = instructSign;
	}
	
	@Column(name = "INSTRUCTION_SIGN")
	private String intsructSign;

	@OneToMany(mappedBy = "techControl", cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderColumn(name = "PARAM_CTRL_INDEX")
	private List<ParamControl> paramControls;

	public String getIntsructSign() {
		return intsructSign;
	}

	public void setIntsructSign(String param) {
		this.intsructSign = param;
	}

	public List<ParamControl> getParamControls() {
		return paramControls;
	}

	public void setParamControls(List<ParamControl> paramControls) {
		this.paramControls = paramControls;
	}
	
	public void refresh(TechControl tc) {
		this.intsructSign = tc.getIntsructSign();
		this.setEditTime(tc.getEditTime());
	}
}
