package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Collection;
import java.util.ArrayList;

@Entity
@Table(name = "Product_Group")
@NamedQuery(name = "AllProdGroups", query = "select pg from ProductGroup pg")
public class ProductGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	public ProductGroup() {
	}

	public ProductGroup(String prodGroupName) {
		this.prodGroupName = prodGroupName;
		this.setProducts(new ArrayList<Product>());
	}

	@Id
	@Column(name = "PROD_GROUP_NAME")
	private String prodGroupName;
	
	@OneToMany(mappedBy = "productGroup")
	private Collection<Product> products;
	
	public String getProdGroupName() {
		return prodGroupName;
	}

	public void setProdGroupName(String param) {
		this.prodGroupName = param;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
}
