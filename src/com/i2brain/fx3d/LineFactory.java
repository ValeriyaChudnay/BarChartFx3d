package com.i2brain.fx3d;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class LineFactory {

	/** The material for the lines parallel to the axes. */
	private PhongMaterial lineMaterial = new PhongMaterial();

	/** Sets a default grey material for the line.
	 */
	public LineFactory() {
		lineMaterial.setDiffuseColor(Color.GREY);
		lineMaterial.setSpecularColor(Color.GREY.brighter());
	}
	
	public void setMaterial(PhongMaterial lineMaterial) {
		this.lineMaterial = lineMaterial;
	}
	
	/** Based on http://netzwerg.ch/blog/2015/03/22/javafx-3d-line/ 
	 */
	public Node createLine(Point3D origin, Point3D target) {
	    Point3D yAxis = new Point3D(0, 1, 0);
	    Point3D diff = target.subtract(origin);
	    double height = diff.magnitude();

	    Point3D mid = target.midpoint(origin);
	    Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

	    Point3D axisOfRotation = diff.crossProduct(yAxis);
	    double angle = Math.acos(diff.normalize().dotProduct(yAxis));
	    Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

	    Box line = new Box(1, height, 1);
	    line.setMaterial(lineMaterial);

	    line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

	    return line;
	}


}
