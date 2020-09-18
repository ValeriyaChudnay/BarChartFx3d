package com.i2brain.chart3d;

import java.util.Collection;
import java.util.List;

import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import application.ChartDemoData;

/** What the user calls in order to create a BarChart3D.
 * 
 * @author JohnDev
 *
 */
public class BarChart3D {

	// TODO make these into properties. Use API of ChartFX.
	private static final int xBarSeparation = 50;
	private static final int zBarSeparation = 50;
	private static final int barDepth = 10;
	private static final int barWidth = 10;
	
	private final double sceneWidth = 600;
	private final double sceneHeight = 600;
	private static double mouseXold = 0;
	private static double mouseYold = 0;
	private static final double rotateModifier = 10;
	
	private final Color[] barColors = { Color.ORANGERED, Color.YELLOW, Color.BLUE, Color.GREEN };	// TODO use CSS:

	/** Sets up a Scene, the Camera and Children for a BarChar3D.
	 * 
	 * @param data	TODO Allow List<String, Number> to match ChartXY's API
	 * @return
	 */
	public Scene createBarChart3DScene(List<Series<Number,Number>> data) {
		Group group = new Group();
		Scene scene = new Scene(group, sceneWidth, sceneHeight, true, SceneAntialiasing.BALANCED);
		scene.setFill(Color.WHITE);
		Camera camera = createCamera();
		scene.setCamera(camera);

		group.getChildren().add(createChildren(data));

		scene.addEventHandler(MouseEvent.ANY, event -> {
			mouseHandler(group, event);
		});
		addMouseWheelControl(scene, group);
		return scene;
	}

	private Group createChildren(List<Series<Number,Number>> data) {
		final Group bars = createValueBars(data);
		// TODO pass the names of the Axes in as a parameter
		Group primitiveGroup = new Group(new Axes().createAxes("Month", "Income", "Year"), bars);
		return primitiveGroup;
	}

	private Group createValueBars(Collection<Series<Number, Number>> collection) {
		Group bars = new Group();
		int z = 0;
		for (XYChart.Series<Number, Number> series : collection) {
			// TODO use the name in legend
			String zName = series.getName();
			for (Data<Number, Number> data : series.getData()) {
				double x = data.XValueProperty().get().doubleValue();
				double val = data.YValueProperty().get().doubleValue();

				addValueBar(bars, x, z, val);
			}
			z++;
		}
		return bars;
	}

	private void addValueBar(Group bars, double x, int z, double val) {
		Color color = barColors[z];
		bars.getChildren().add(createBar(new Point3D(x * xBarSeparation, 0, -z * zBarSeparation), val, color));
	}

	private static Box createBar(Point3D pos, double height, Color color) {
		Box box = new Box(barWidth, height, barDepth);
		final PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(color);
		material.setSpecularColor(color.brighter());
		box.setMaterial(material);

//		bar.getStyleClass().addAll("chart-bar", "series" + seriesIndex, "data" + itemIndex,series.defaultColorStyleClass); From class BarChart

		box.setTranslateX(pos.getX());
		box.setTranslateY(pos.getY() - height / 2);
		box.setTranslateZ(pos.getZ());
		Tooltip.install(box, new Tooltip("x = " + pos.getX() + ", height = " + height + ", z = " + -pos.getZ()));
		return box;
	}

	private static void mouseHandler(Group sceneRoot, MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED || event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			mousePressedOrMoved(sceneRoot, event);
		}
	}

	private static void mousePressedOrMoved(Group sceneRoot, MouseEvent event) {
		Rotate xRotate = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate yRotate = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		sceneRoot.getTransforms().addAll(xRotate, yRotate);
		double mouseXnew = event.getSceneX();
		double mouseYnew = event.getSceneY();
		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			double pitchRotate = xRotate.getAngle() + (mouseYnew - mouseYold) / rotateModifier;
			xRotate.setAngle(pitchRotate);
			double yawRotate = yRotate.getAngle() - (mouseXnew - mouseXold) / rotateModifier;
			yRotate.setAngle(yawRotate);
		}
		mouseXold = mouseXnew;
		mouseYold = mouseYnew;
	}

	public static void addMouseWheelControl(Scene scene, Group group) {
		scene.setOnScroll(event -> {
			if (event.isShiftDown()) {
				group.setTranslateX(group.getTranslateX() + event.getDeltaX());
			} else {
				if (event.isControlDown()) {
					group.setTranslateZ(group.getTranslateZ() - event.getDeltaY());
				} else {
					group.setTranslateY(group.getTranslateY() + event.getDeltaY());
				}
			}
		});

	}

	private static Camera createCamera() {
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateX(400);
		camera.setTranslateY(-200);
		camera.setRotationAxis(Rotate.X_AXIS);
		camera.setRotate(20);
		camera.setRotationAxis(Rotate.Y_AXIS);
		camera.setRotate(-20);
		camera.setTranslateZ(-1200);

		return camera;
	}

}
