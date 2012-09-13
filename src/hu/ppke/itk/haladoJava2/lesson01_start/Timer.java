package hu.ppke.itk.haladoJava2.lesson01_start;

class Timer {
	private static long startTime	= 0;
	
	public static void start () {
		startTime = System.currentTimeMillis();
	}
	
	public static long stop () {
		return System.currentTimeMillis() - startTime;
	}
	
	public static void printElapsedTime () {
		System.out.println(stop ());
	}
}