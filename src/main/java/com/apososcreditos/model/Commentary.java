package com.apososcreditos.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Commentary implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "comentario_filme")
	private Filme filme;

	@ManyToOne
	@JoinColumn(name = "comentario_user")
	private UserInfo userInfo;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "data", columnDefinition = "DATE")
	private Date data;

	@Column
	private String commentaryText;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getCommentaryText() {
		return commentaryText;
	}

	public void setCommentaryText(String commentary) {
		this.commentaryText = commentary;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
