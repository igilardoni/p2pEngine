package model.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Item {
	@XmlElement(name="id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@XmlElement(name="title")
	@NotNull
	@Size(min = 3, max = 255)
	private String title;
	
	@XmlElement(name="description")
	@NotNull
	@Size(min = 3, max = 2000)
	private String description;
	
	@XmlElement(name="createdAt")
	@NotNull
	@Temporal(TemporalType.TIME)
	private Date createdAt;

	@XmlElement(name="pbkey")
	@NotNull
	private BigInteger pbkey;
	
	@XmlElement(name="sign")
	@NotNull
	private ElGamalSignEntity sign;
	
	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCreatedAt(Date date) {
		createdAt = date;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public ElGamalSignEntity getSign() {
		return sign;
	}

	public void setSign(ElGamalSignEntity sign) {
		this.sign = sign;
	}

	public BigInteger getPbkey() {
		return pbkey;
	}

	public void setPbkey(BigInteger pbkey) {
		this.pbkey = pbkey;
	}
}
