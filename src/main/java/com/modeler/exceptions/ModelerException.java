package com.modeler.exceptions;

public class ModelerException extends Exception{
	public static String existeUsuario ="Ya existe un usuario con este email";

	public ModelerException(String ms) {
		super(ms);
	}
}
