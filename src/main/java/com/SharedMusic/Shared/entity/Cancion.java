package com.SharedMusic.Shared.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity
public class Cancion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(unique = true)

	private String genero;
	private String nombre;
	private String link;
	private String artista;
	private String cantidad_likes;
	private String numero_reproducciones;

	public Cancion() {

	}

	public Cancion(String genero, String nombre, String link, String artista, String cantidad_likes,
			String numero_reproducciones) {
		this.genero = genero;
		this.nombre = nombre;
		this.link = link;
		this.artista = artista;
		this.cantidad_likes = cantidad_likes;
		this.numero_reproducciones = numero_reproducciones;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getCantidad_likes() {
		return cantidad_likes;
	}

	public void setCantidad_likes(String cantidad_likes) {
		this.cantidad_likes = cantidad_likes;
	}

	public String getNumero_reproducciones() {
		return numero_reproducciones;
	}

	public void setNumero_reproducciones(String numero_reproducciones) {
		this.numero_reproducciones = numero_reproducciones;
	}

}
