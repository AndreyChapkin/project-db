package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Time_Norm")
@NamedQuery(name = "AllTimeNorms", query = "select tn from TimeNorm tn")
public class TimeNorm implements Serializable {

	private static final long serialVersionUID = 1L;

	public TimeNorm() {
	}

	public TimeNorm(String normSign, String normDescrip) {
		this.normSign = normSign;
		this.normDescrip = normDescrip;
	}
	
	@Column(name = "NORM_SIGN")
	@Id
	private String normSign;
	
	@Column(name = "NORM_DESCRIPTION")
	private String normDescrip;

	public String getNormDescrip() {
		return normDescrip;
	}

	public void setNormDescrip(String param) {
		this.normDescrip = param;
	}

	public String getNormSign() {
		return normSign;
	}

	public void setNormSign(String param) {
		this.normSign = param;
	}
	
	public void refresh(TimeNorm tn) {
		this.normDescrip = tn.getNormDescrip();
	}
}
