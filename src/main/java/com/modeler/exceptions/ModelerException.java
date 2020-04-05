package com.modeler.exceptions;

public class ModelerException extends Exception{
	public static String existeUsuario ="Ya existe un usuario con este email";
	public static String noEsUnCorreoValido="Revise el correo enviado";
	public static String nombreDeUsuarioInvalido="Ya esta en uso este nombre de usuario";
	public static String nombreProyecto = "No puede crear otro proyecto con el mismo nombre";
	public static String modeloInvalido = "Ya existe un un modelo con este nombre";
	public static String claseInvalida = "Ya existe alguna clase con este nombre";
	public static String claseInexistente ="No existe una clase con este nombre";
	public static String proyectoYaCompartido = "Este proyecto ya fue compartido con este usuario";
	public static String existeYaEstaLinea = "Ya existe una linea con estas caracteristicas";

	public ModelerException(String ms) {
		super(ms);
	}
}
