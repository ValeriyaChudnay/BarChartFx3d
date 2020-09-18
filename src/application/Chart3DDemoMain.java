package application;

import java.util.List;

import com.i2brain.chart3d.BarChart3D;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

/**
 */
public class Chart3DDemoMain extends Application {

	@Override
	public void start(Stage primaryStage) {

		List<Series<Number, Number>> data = ChartDemoData.getData();
		
		Scene scene = new BarChart3D().createBarChart3DScene(data);

		primaryStage.setTitle("Chart3D Demo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}