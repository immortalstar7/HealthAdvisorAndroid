package com.learn2crack;

import org.achartengine.ChartFactory;
import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GraphActivity extends Activity {

    private View mChart, mChart1;
    private String[] mMonth = new String[] {
            "Jan", "Feb" , "Mar", "Apr", "May", "Jun",
            "Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment3);

        // Getting reference to the button btn_chart
        Button btnChart = (Button) findViewById(R.id.btn_chart);

        // Defining click event listener for the button btn_chart
        OnClickListener clickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Draw the Income vs Expense Chart
                openChart();
            }
        };

        // Setting event click listener for the button btn_chart of the MainActivity layout
        btnChart.setOnClickListener(clickListener);

    }

    private void openChart(){
        int[] x = { 0,1,2,3,4,5,6,7, 8, 9, 10, 11 };
        int[] income = { 20000,25000,27000,30000,2800,0,0,0,0,0,0,0};
        int[] expense = {50000,50000,50000,50000,50000,0,0,0, 0, 0, 0, 0 };

        // Creating an XYSeries for Income
        XYSeries incomeSeries = new XYSeries("Steps");
        // Creating an XYSeries for Expense
        XYSeries expenseSeries = new XYSeries("Goal");
        // Adding data to Income and Expense Series
        for(int i=0;i<x.length;i++){
            incomeSeries.add(i,income[i]);
            expenseSeries.add(i,expense[i]);
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(incomeSeries);
        // Adding Expense Series to dataset
        dataset.addSeries(expenseSeries);

        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.CYAN); //color of the graph set to cyan
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(2);
        incomeRenderer.setDisplayChartValues(true);
        incomeRenderer.setDisplayChartValuesDistance(10); //setting chart value distance

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.GREEN);
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2);
        expenseRenderer.setDisplayChartValues(true);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Steps taken");
        multiRenderer.setXTitle("Year 2015");
        multiRenderer.setYTitle("Steps taken");

        /***
         * Customizing graphs
         */
//setting text size of the title
        multiRenderer.setChartTitleTextSize(28);
        //setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
        //setting text size of the graph lable
        multiRenderer.setLabelsTextSize(24);
        //setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(false);
        //setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(false, false);
        //setting click false on graph
        multiRenderer.setClickEnabled(false);
        //setting zoom to false on both axis
        multiRenderer.setZoomEnabled(false, false);
        //setting lines to display on y axis
        multiRenderer.setShowGridY(false);
        //setting lines to display on x axis
        multiRenderer.setShowGridX(false);
        //setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
        //setting displaying line on grid
        multiRenderer.setShowGrid(false);
        //setting zoom to false
        multiRenderer.setZoomEnabled(false);
        //setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
        //setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
        //setting to in scroll to false
        multiRenderer.setInScroll(false);
        //setting to set legend height of the graph
        multiRenderer.setLegendHeight(30);
        //setting x axis label align
        multiRenderer.setXLabelsAlign(Align.CENTER);
        //setting y axis label to align
        multiRenderer.setYLabelsAlign(Align.LEFT);
        //setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        //setting no of values to display in y axis
        multiRenderer.setYLabels(10);
        // setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
        // if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(50000);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
//setting max values to be display in x axis
        multiRenderer.setXAxisMax(11);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(0.5);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        //Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);

        //setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{30, 30, 30, 30});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mMonth[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(incomeRenderer);
        multiRenderer.addSeriesRenderer(expenseRenderer);

        //this part is used to display graph on the xml
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
        //remove any views before u paint the chart
        chartContainer.removeAllViews();
        //drawing bar chart
        mChart = ChartFactory.getBarChartView(GraphActivity.this, dataset, multiRenderer,Type.DEFAULT);
        //adding the view to the linearlayout
        chartContainer.addView(mChart);
        // Pie Chart Section Names
        new URLReader().execute();


    }




    private class URLReader extends AsyncTask<String, String, String> {

        private View mChart, mChart1;
        Button btnChart = (Button) findViewById(R.id.btn_chart);

        @Override
        public String doInBackground(String... strings) {
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://fitbitsample-40998.onmodulus.net/getStepsForUser/2XXCMB");
            try {
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } else {
                    Log.e(URLReader.class.toString(), "Failed to download file");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return builder.toString();

        }

        @Override
        public void onPostExecute(String input) {
            String[] code = new String[]{"Today", "Goal"};
            try {
                JSONObject json = new JSONObject(input);
                // Pie Chart Section Value
                double[] distribution = {json.getInt("stepsToday"), json.getInt("stepsGoal")};
                // Color of each Pie Chart Sections
                int[] colors = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN,
                        Color.RED};

                // Instantiating CategorySeries to plot Pie Chart
                CategorySeries distributionSeries = new CategorySeries(
                        "Steps taken against Steps goal");
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
                        .setChartTitle("Steps taken against steps goal");
                defaultRenderer.setChartTitleTextSize(20);
                defaultRenderer.setZoomButtonsVisible(false);

                // this part is used to display graph on the xml
                // Creating an intent to plot bar chart using dataset and
                // multipleRenderer
                // Intent intent = ChartFactory.getPieChartIntent(getBaseContext(),
                // distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");

                // Start Activity
                // startActivity(intent);

                LinearLayout chartContainer1 = (LinearLayout) findViewById(R.id.chart1);
                // remove any views before u paint the chart
                //chartContainer1.removeAllViews();
                // drawing pie chart1
                mChart1 = ChartFactory.getPieChartView(getBaseContext(),
                        distributionSeries, defaultRenderer);
                // adding the view to the linearlayout
                chartContainer1.addView(mChart1);

            } catch (Exception exception) {
                Log.e(GraphActivity.class.toString(), "Json Error in reading from url ");
            }
        }
    }
}
