package hu.ppke.itk.haladoJava2.lesson01_start;


public class Measurement {
	
	
	//MID	NODE	SEQUENCE	VOLT	TEMP	LIGHT	MAGX	MAGY	ACCELX	ACCELY	NOISE?	TIMESTAMP
	private final byte		mid;		//nem tudjuk mi.
	private final byte		node;		
	private final int		sequence;	//csomagsorrend visszaállításához, kicsi az esély, hogy a sorrend elromoljon
	private final double	volt;
	private final double	temp;
	private final double	light;
	private final double	magX;		//mágneses érték
	private final double	magY;
	private final double	accelX;
	private final double	accelY;
	private final double	noise;
	private final long		timestamp; //elvileg nincs ket azonos timestamp

}
