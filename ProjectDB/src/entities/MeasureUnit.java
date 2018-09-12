package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Measure_Unit")
@NamedQuery(name = "AllMeasUnits", query = "select mu from MeasureUnit mu")
public class MeasureUnit implements Serializable {

	private static final long serialVersionUID = 1L;

	public MeasureUnit() {
	}

	public MeasureUnit(String measUnitName) {
		this.measUnitName = measUnitName;
	}

	@Id
	@Column(name = "MEAS_UNIT_NAME")
	private String measUnitName;

	public String getMeasUnitName() {
		return measUnitName;
	}

	public void setMeasUnitName(String param) {
		this.measUnitName = param;
	}
}
