package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.XYChart;

/** A simple class whose only method fills a List with data.
 *  So the method could read a DB or whatever.
 *  It's separated from main to fulfil SRP.
 *  
 * @author JohnDev
 *
 */
public class ChartDemoData {

	static List<XYChart.Series<Number, Number>> getData() {
		
		List<XYChart.Series<Number, Number>> dataList = new ArrayList<>();

		XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
		series1.setName("2003");	// Z-Axis
		series1.getData().add(new XYChart.Data<>(1, 200));
		series1.getData().add(new XYChart.Data<>(2, 100));
		series1.getData().add(new XYChart.Data<>(3, 150));
		series1.getData().add(new XYChart.Data<>(4, 70));
		dataList.add(series1);

		XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
		series2.setName("2004");	// Z-Axis
		series2.getData().add(new XYChart.Data<>(1, 20));
		series2.getData().add(new XYChart.Data<>(2, 130));
		series2.getData().add(new XYChart.Data<>(3, 50));
		series2.getData().add(new XYChart.Data<>(4, 150));
		dataList.add(series2);

		XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
		series3.setName("2005");	// Z-Axis
		series3.getData().add(new XYChart.Data<>(1, 70));
		series3.getData().add(new XYChart.Data<>(2, 30));
		series3.getData().add(new XYChart.Data<>(3, 50));
		series3.getData().add(new XYChart.Data<>(4, 150));
		dataList.add(series3);
		
		return dataList;
	}

}
