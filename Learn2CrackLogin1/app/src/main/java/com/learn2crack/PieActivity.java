package com.learn2crack;

/**
 * Created by neha24 on 5/4/15.
 */
import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class PieActivity extends Activity {
    private View mChart1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment3);

        // Getting reference to the button btn_chart
        Button btnChart1 = (Button) findViewById(R.id.btn_chart1);

        // Defining click event listener for the button btn_chart1
        OnClickListener clickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Draw the pie Chart
                openChart1();
            }
        };

        // Setting event click listener for the button btn_chart1 of the
        // MainActivity layout
        btnChart1.setOnClickListener(clickListener);
    }

    private void openChart1() {

        // Pie Chart Section Names
        String[] code = new String[] { "Froyo", "Gingerbread",
                "IceCream Sandwich", "Jelly Bean", "KitKat" };

        // Pie Chart Section Value
        double[] distribution = { 0.5, 9.1, 7.8, 45.5, 33.9 };

        // Color of each Pie Chart Sections
        int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN,
                Color.RED };

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(
                " Android version distribution as on October 1, 2012");
        for (int i = 0; i < distribution.length; i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int i = 0; i < distribution.length; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
//Adding colors to the chart
            defaultRenderer.setBackgroundColor(Color.BLACK);
            defaultRenderer.setApplyBackgroundColor(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer
                .setChartTitle("Android version distribution as on December 1, 2014. ");
        defaultRenderer.setChartTitleTextSize(20);
        defaultRenderer.setZoomButtonsVisible(false);

        // this part is used to display graph on the xml
        // Creating an intent to plot bar chart using dataset and
        // multipleRenderer
        // Intent intent = ChartFactory.getPieChartIntent(getBaseContext(),
        // distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");

        // Start Activity
        // startActivity(intent);

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart1);
        // remove any views before u paint the chart
        chartContainer.removeAllViews();
        // drawing pie chart1
        mChart1 = ChartFactory.getPieChartView(getBaseContext(),
                distributionSeries, defaultRenderer);
        // adding the view to the linearlayout
        chartContainer.addView(mChart1);

    }

}