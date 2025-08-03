package br.com.isiflix.salutar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "tbl_midia")
public class Midia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "num_seq")
	private Integer numeroSequencial;
	
	@Column(name = "descricao", length = 255)
	private String descricao;
	
	@Column(name = "link_midia", length = 255)
	private String linkMidia;
	
	@ManyToOne
	@JoinColumn(name = "id_ficha")
	@JsonIgnoreProperties("midias")
	private FichaPaciente ficha;

	public Integer getNumeroSequencial() {
		return numeroSequencial;
	}

	public void setNumeroSequencial(Integer numeroSequencial) {
		this.numeroSequencial = numeroSequencial;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLinkMidia() {
		return linkMidia;
	}

	public void setLinkMidia(String linkMidia) {
		this.linkMidia = linkMidia;
	}

	public FichaPaciente getFicha() {
		return ficha;
	}

	public void setFicha(FichaPaciente ficha) {
		this.ficha = ficha;
	}
}

