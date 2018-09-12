package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "Product")
@NamedQuery(name = "AllProducts", query = "select p from Product p order by p.createTime desc")
public class Product extends TechObject implements Serializable {

	private static final long serialVersionUID = 1L;

	public Product() {
	}

	public Product(User creator,TechObject  assocObj, TechObject  parentObj, 
	Date createTime, Date editTime, String prodName, float prodWeight,
	int batchValue, String prodSign) {
		super(creator, assocObj, parentObj, createTime, editTime);
		this.prodName = prodName;
		this.prodWeight = prodWeight;
		this.batchValue = batchValue;
		this.prodSign = prodSign;
	}

	@Column(name = "DOC_NAME")
	private String prodName;

	@Column(name = "PROD_WEIGHT")
	private float prodWeight;

	@Column(name = "BATCH_VALUE")
	private int batchValue;
	
	@Column(name = "PROD_SIGN")
	private String prodSign;
	
	@ManyToOne
	@JoinColumn(name = "MEAS_UNIT_NAME")
	private MeasureUnit measureUnit;
	
	@ManyToOne
	@JoinColumn(name = "PROD_GROUP")
	private ProductGroup productGroup;

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String docName) {
		this.prodName = docName;
	}

	public float getProdWeight() {
		return prodWeight;
	}

	public void setProdWeight(float param) {
		this.prodWeight = param;
	}

	public int getBatchValue() {
		return batchValue;
	}

	public void setBatchValue(int param) {
		this.batchValue = param;
	}

	public String getProdSign() {
		return prodSign;
	}

	public void setProdSign(String param) {
		this.prodSign = param;
	}

	public MeasureUnit getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(MeasureUnit measureUnit) {
		this.measureUnit = measureUnit;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}
}
