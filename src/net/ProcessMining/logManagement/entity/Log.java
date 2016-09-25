package net.ProcessMining.logManagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import net.ProcessMining.user.entity.User;

@SuppressWarnings("serial")
@Entity
@Table(name = "log")
public class Log implements Serializable{
	
	@Id
//	@GenericGenerator(name = "generator", strategy = "uuid.hex")//主键生成器名称及方式
//	@GeneratedValue(generator = "generator")//主键赋值方式由生成器来进行赋值
	@Column(name = "id", unique = true)
	private String id;
	
	@Column(name = "name", nullable = false, length = 64)
	private String name;
	
	@Column(name = "type", nullable = false, length =4)
	private Integer type;
	
	@Column(name = "format", nullable = false, length = 16)
	private String format;
	
	@Column(name = "link", nullable = true, length = 64)
	private String link;
	
	@Column(name = "date", nullable = true, length = 8)
	private Integer date;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user")
	private User user;
	
	@Column(name = "is_share", nullable = false, length =2)
	private Integer isShare;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getIsShare() {
		return isShare;
	}

	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}
	
	
}
