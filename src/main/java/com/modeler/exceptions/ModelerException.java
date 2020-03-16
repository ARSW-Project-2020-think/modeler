package com.modeler.exceptions;

public class ModelerException extends Exception{
	public static String existeUsuario ="Ya existe un usuario con este email";
	public static String noEsUnCorreoValido="Revise el correo enviado";
	public static String nombreDeUsuarioInvalido="Ya esta en uso este nombre de usuario";
	public static String nombreProyecto = "No puede crear otro proyecto con el mismo nombre";

	public ModelerException(String ms) {
		super(ms);
	}
}
