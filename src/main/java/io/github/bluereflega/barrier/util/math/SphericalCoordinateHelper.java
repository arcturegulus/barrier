package io.github.bluereflega.barrier.util.math;

public class SphericalCoordinateHelper {
	public static double toCartX(double radius, double zenith, double azimuth) {
		return radius * Math.sin(Math.toRadians(zenith)) * Math.cos(Math.toRadians(azimuth));
	}

	public static double toCartY(double radius, double zenith) {
		return radius * Math.cos(Math.toRadians(zenith));
	}

	public static double toCartZ(double radius, double zenith, double azimuth) {
		return radius * Math.sin(Math.toRadians(zenith)) * Math.sin(Math.toRadians(azimuth));
	}
}
