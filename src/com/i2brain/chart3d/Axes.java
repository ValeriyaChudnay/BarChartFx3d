package com.i2brain.chart3d;

import javafx.css.CssMetaData;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import com.i2brain.fx3d.LineFactory;

import java.util.List;

public class Axes {
	final int barWidth = 2;

	/** The length of the axes (number of pixels). TODO make these into properties. They are
	 * temporarily public to allow users to change them. Cludge!*/
	public int maxX = 500, maxY = 500, maxZ = 500;

	final PhongMaterial axisMaterial = new PhongMaterial();

	/** Gap between the lines on the plane created by 2 axes. */
	private int lineSpacing = 100;
	private LineFactory lineFactory = new LineFactory();

	Node createAxes(String xLabel, String yLabel, String zLabel) {
		Group axes = new Group();
		axisMaterial.setDiffuseColor(Color.BLACK);
		axisMaterial.setSpecularColor(Color.BLACK.brighter());

		axes.getChildren().addAll(createXAxis(xLabel), createYAxis(yLabel), createZAxis(zLabel), createXYPlane(), createYZPlane(), createXZPlane());

		return axes;
	}

	private Node createXYPlane() {
		Group group = new Group();
		for (int x = 0; x <= maxX; x += lineSpacing) {
			group.getChildren().add(createLine(new Point3D(x, 0, 0), new Point3D(x, -maxY, 0)));
		}
		for (int y = 0; y <= maxY; y += lineSpacing) {
			group.getChildren().add(createLine(new Point3D(0, -y, 0), new Point3D(maxX, -y, 0)));
		}
		
//		group.getChildren().add(makeAxesPlane(maxX, maxY, 2, Color.LIGHTYELLOW, maxX / 2, -maxY / 2, barWidth*2));

		return group;
	}

	private static Box makeAxesPlane(int maxX, int maxY, int maxZ, Color col, double xTrans, double yTrans, double zTrans) {
		Box plane = new Box(maxX, maxY, maxZ);
		plane.setTranslateX(xTrans);
		plane.setTranslateY(yTrans);
		plane.setTranslateZ(zTrans);
		plane.setMaterial(new PhongMaterial(col));
		// TODO use transparency in 8u60: http://stackoverflow.com/questions/29308397/javafx-3d-transparency
		return plane;
	}

	private Node createYZPlane() {
		Group group = new Group();
		for (int z = 0; z <= maxZ; z += lineSpacing) {
			group.getChildren().add(createLine(new Point3D(0, 0, -z), new Point3D(0, -maxY, -z)));
		}
		for (int y = 0; y <= maxY; y += lineSpacing) {
			group.getChildren().add(createLine(new Point3D(0, -y, 0), new Point3D(0, -y, -maxZ)));
		}
//		group.getChildren().add(makeAxesPlane(2, maxY, maxZ, Color.ORANGE, -barWidth*2, -maxY / 2, -maxZ / 2));
		return group;
	}

	private Node createXZPlane() {
		Group group = new Group();
		for (int z = 0; z <= maxZ; z += lineSpacing) {
			group.getChildren().add(createLine(new Point3D(0, 0, -z), new Point3D(maxX, 0, -z)));
		}
		for (int x = 0; x <= maxX; x += lineSpacing) {
			group.getChildren().add(createLine(new Point3D(x, 0, 0), new Point3D(x, 0, -maxZ)));
		}
//		group.getChildren().add(makeAxesPlane(maxX, 2, maxZ, Color.LIGHTBLUE, maxX / 2, barWidth*2, -maxZ / 2));
		return group;
	}

	private Node createLine(Point3D origin, Point3D target) {
		return lineFactory.createLine(origin, target);
	}

	/** TODO Ideally, we could expect the Chart3D to be based on the CSS for 2D Charts...
	 * @param label
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void setStyle(Node label) {
//		label.getStyleClass().add("axis-label");	// TODO get it working: http://docs.oracle.com/javase/8/javafx/user-interface-tutorial/css-styles.htm#CIHGIAGE
//		List styleList = BarChart.getClassCssMetaData();
//		for (Object style : styleList) {
//			CssMetaData<BarChart, String> data = (CssMetaData<BarChart, String>)style;
//			System.out.println(data);
//		}
//		for (String style : new BarChart(new CategoryAxis(), new NumberAxis()).getStyleClass()) {	// chart, bar-chart
//		for (String style : new CategoryAxis().getStyleClass()) {	// axis
//			System.out.println(style);
//		}
//		}
//		String textFill = Label.getClassCssMetaData();
//		getStyle("-fx-text-fill: aqua;");
	}

	private Group createXAxis(String xLabel) {
		Group group = new Group();
		Box xAxis = new Box(maxX, barWidth, barWidth);
		xAxis.setMaterial(axisMaterial);
		xAxis.setTranslateX(maxX / 2);
		Label label = new Label(xLabel);
		label.setTranslateX(maxX / 2);
		label.setTranslateY(0);
		setStyle(label);
		label.setStyle("-fx-text-fill: aqua;");
		group.getChildren().addAll(xAxis, label, createTicksOnXAxis());
		return group;
	}

	/** TODO Very rough. Needs list of Texts, really. */
	private Node createTicksOnXAxis() {
		Group group = new Group();
		int x = 0;
		int cntTicks = 5;
		int deltaX = maxX / cntTicks ;
		// TODO breaks when deltaX < 1!
		for (int i = 0 ; i <= cntTicks ; i++) {
			Label label = new Label(String.valueOf(x));
			label.setTranslateX(x - 10); // guessed	// should be deltaX - length / 2 !
			label.setTranslateY(15); // guessed	// should be deltaX - length / 2 !
			group.getChildren().addAll(label, lineFactory.createLine(new Point3D(x, 0, 0), new Point3D(x, 10, 0)));
			System.out.println(label.getBoundsInParent());	// I need the width!
			x += deltaX;
		}
		return group;
	}

	private Group createYAxis(String yLabel) {
		Group group = new Group();
		Box yAxis = new Box(barWidth, maxY, barWidth);
		yAxis.setMaterial(axisMaterial);
		yAxis.setTranslateY(-maxY / 2);
		Label label = new Label(yLabel);
//		System.out.println("Width = " + label.getWidth());	// 0!
		label.setTranslateX(-50);	// Guessed! TODO how to do it properly?
		label.setTranslateY(-maxY / 2);
		
		label.setStyle("-fx-text-fill: aqua;");
		group.getChildren().addAll(yAxis, label, createTicksOnYAxis());
		return group;
	}

	private Node createTicksOnYAxis() {
		Group group = new Group();
		int y = 0;
		int cntTicks = 5;
		int deltaY = maxY / cntTicks ;
		// TODO breaks when deltaY < 1!
		for (int i = 0 ; i <= cntTicks ; i++) {
			Label label = new Label(String.valueOf(y));
			label.setTranslateX(-40); // guessed
			label.setTranslateY(-y - 10); // guessed
			group.getChildren().addAll(label, lineFactory.createLine(new Point3D(0, -y, 0), new Point3D(-10, -y, 0)));
			y += deltaY;
		}
		return group;
	}

	private Group createZAxis(String zLabel) {
		Group group = new Group();
		Box zAxis = new Box(barWidth, barWidth, maxZ);
		zAxis.setMaterial(axisMaterial);
		zAxis.setTranslateZ(-maxZ / 2);
		Label label = new Label(zLabel);
		label.setTranslateX(-50);	// Guessed! TODO how to do it properly?
		label.setTranslateZ(-maxZ / 2);
		label.setStyle("-fx-text-fill: aqua;");
		group.getChildren().addAll(zAxis, label, createTicksOnZAxis());
		return group;
	}

	private Node createTicksOnZAxis() {
		Group group = new Group();
		int z = 0;
		int cntTicks = 5;
		int deltaZ = maxZ / cntTicks ;
		// TODO breaks when deltaZ < 1!
		for (int i = 0 ; i <= cntTicks ; i++) {
			Label label = new Label(String.valueOf(z));
			label.setTranslateX(-40); // guessed
			label.setTranslateZ(-z - 10); // guessed
			group.getChildren().addAll(label, lineFactory.createLine(new Point3D(0, 0, -z), new Point3D(-10, 0, -z)));
			z += deltaZ;
		}
		return group;
	}
}
