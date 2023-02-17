package com.ladexample.app.views.learninganalyticsdashboard;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.ResponsiveBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;

public class BundeslandDonutChartBuilder extends ApexChartsBuilder{
   public BundeslandDonutChartBuilder(){
      withChart(ChartBuilder.get().withType(Type.DONUT).build())
            .withTitle(TitleSubtitleBuilder.get()
                  .withText("Bundesland-Kurs-Verteilung").build())
            .withLabels("BW", "Hessen", "Team C", "Team D", "Team E")
            .withLegend(LegendBuilder.get()
                  .withPosition(Position.RIGHT)
                  .build())
            .withSeries(44.0, 55.0, 41.0, 17.0, 15.0)
            .withResponsive(ResponsiveBuilder.get()
                  .withBreakpoint(480.0)
                  .withOptions(OptionsBuilder.get()
                        .withLegend(LegendBuilder.get()
                              .withPosition(Position.BOTTOM)
                              .build())
                        .build())
                  .build());
   }
}