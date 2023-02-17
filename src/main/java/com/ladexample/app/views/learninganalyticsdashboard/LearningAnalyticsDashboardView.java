package com.ladexample.app.views.learninganalyticsdashboard;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import com.ladexample.app.Application;
import com.ladexample.app.data.entity.Course;
import com.ladexample.app.data.service.CourseService;
import com.ladexample.app.utils.LadUtil;
import com.ladexample.app.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.AnchorTarget;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
import software.xdev.vaadin.maps.leaflet.flow.data.LMarker;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;

import java.util.*;
import java.util.stream.Stream;

@PageTitle("Learning Analytics Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class LearningAnalyticsDashboardView extends VerticalLayout{
   private final CourseService courseService;
   private final List<Course> courses;
   private final String[] chartColors = {"#1e88e5", "#00acc1", "#5e35b1", "#fb8c00", "#f4511e", "#6d4c41", "#546e7a", "#d81b60", "#00897b", "#c0ca33", "#43a047", "#8e24aa", "#039be5", "#7cb342", "#fb8c00", "#f4511e", "#6d4c41", "#546e7a", "#d81b60", "#00897b", "#c0ca33", "#43a047", "#8e24aa", "#039be5", "#7cb342", "#fb8c00", "#f4511e", "#6d4c41", "#546e7a", "#d81b60", "#00897b", "#c0ca33", "#43a047", "#8e24aa", "#039be5", "#7cb342", "#fb8c00", "#f4511e", "#6d4c41", "#546e7a", "#d81b60", "#00897b", "#c0ca33", "#43a047", "#8e24aa", "#039be5", "#7cb342", "#fb8c00", "#f4511e", "#6d4c41", "#546e7a", "#d81b60", "#00897b", "#c0ca33", "#43a047", "#8e24aa", "#039be5", "#7cb342", "#fb8c00", "#f4511e", "#6d4c41", "#546e7a", "#d81b60", "#00897b", "#c0ca33", "#43a047", "#8e24aa", "#039be5", "#7cb342", "#fb8c00", "#f4511e", "#6d4c41", "#546e7a", "#d81b60", "#00897b", "#c0ca33", "#43a047", "#8e24aa", "#039be5", "#7cb342", "#fb8c00", "#f4511e", "#6d4c41", "#546e7a", "#d81b60", "#00897b", "#c0ca33", "#43a047", "#8e24aa", "#039be5"};

   public LearningAnalyticsDashboardView(CourseService courseService){
      this.courseService = courseService;
      this.courses = courseService.findAll();

      this.setSizeFull();

      HorizontalLayout firstRow = createFirstRow();
      HorizontalLayout secondRow = createSecondRow();

      add(firstRow, secondRow);
   }

   private HorizontalLayout createFirstRow(){
      HorizontalLayout firstRow = new HorizontalLayout();
      firstRow.setSizeFull();

      VerticalLayout dataInfoLayout = createDataInfoLayout();
      dataInfoLayout.setMargin(false);
      dataInfoLayout.setPadding(false);
      dataInfoLayout.setWidth("1100px");

      ApexCharts cityDonutChart = createCityDonutChart();
      cityDonutChart.setId("chart-section");
      cityDonutChart.setWidthFull();

      ApexCharts vendorPieChart = createVendorPieChart();
      vendorPieChart.setId("chart-section");
      vendorPieChart.setWidthFull();

      ApexCharts vendorsPerMonthVerticalBarChart = createVendorsPerMonthVerticalBarChart();
      vendorsPerMonthVerticalBarChart.setId("chart-section");
      vendorsPerMonthVerticalBarChart.setWidthFull();

      firstRow.add(dataInfoLayout, cityDonutChart, vendorPieChart, vendorsPerMonthVerticalBarChart);

      return firstRow;
   }

   private HorizontalLayout createSecondRow(){
      HorizontalLayout secondRow = new HorizontalLayout();
      secondRow.setSizeFull();

      ApexCharts bundeslandHorizontalBarChart = createBundeslandHorizontalBarChart();
      bundeslandHorizontalBarChart.setId("chart-section");
      bundeslandHorizontalBarChart.setWidthFull();

      VerticalLayout wordCloudLayout = createWordCloudLayout();
      wordCloudLayout.setId("chart-section");

      ApexCharts topicsRadarChart = createTopicsRadarChart();
      topicsRadarChart.setId("chart-section");
      topicsRadarChart.setWidthFull();

      secondRow.add(bundeslandHorizontalBarChart, wordCloudLayout, topicsRadarChart);

      return secondRow;
   }

   private VerticalLayout createDataInfoLayout(){
      VerticalLayout dataInfoLayout = new VerticalLayout();

      VerticalLayout upperLayout = new VerticalLayout();
      upperLayout.setSizeFull();
      upperLayout.setId("chart-section");

      VerticalLayout bottomLayout = new VerticalLayout();
      bottomLayout.setSizeFull();
      bottomLayout.setId("chart-section");
      dataInfoLayout.add(upperLayout, bottomLayout);

      Label dataInfoTitleLabel = new Label("Informationen über die Daten");
      dataInfoTitleLabel.getElement().getStyle().set("font-family", "Helvetica, Arial, sans-serif");
      dataInfoTitleLabel.getElement().getStyle().set("font-size", "14px");
      dataInfoTitleLabel.getElement().getStyle().set("font-weight", "900");

      VerticalLayout dataInfoContentLayout = new VerticalLayout();
      dataInfoContentLayout.setSpacing(false);
      dataInfoContentLayout.setPadding(false);

      Label coursesCountLabel = new Label("# Kurse: " + courses.size());
      Label differentTopicsCountLabel = new Label("# Kurtstitel: " + getDifferentTopicsCount());
      Label differentVendorsCountLabel = new Label("# Anbieter: " + getDifferentVendorsCount());
      Label differentCitiesCountLabel = new Label("# Städte: " + getDifferentCitiesCount());
      Label differentBundeslandCountLabel = new Label("# Bundesländer: " + getDifferentBundeslandCount());
      Label averageWordCountInCourseTitlesLabel = new Label("Ø Wörter in Titeln: " + getAverageWordCountInCourseTitles());
      Label averageWordCountInCourseDescriptionLabel = new Label("Ø Wörter in Beschreibungen: " + getAverageWordCountInCourseDescription());

      Label openMapTitleLabel = new Label("Standorte der Kurse");
      openMapTitleLabel.getElement().getStyle().set("font-family", "Helvetica, Arial, sans-serif");
      openMapTitleLabel.getElement().getStyle().set("font-size", "14px");
      openMapTitleLabel.getElement().getStyle().set("font-weight", "900");

      Button openMapButton = new Button("Karte öffnen");
      openMapButton.getElement().getStyle().set("margin", "auto");

      // when button is clicked a new dialog window is opened
      openMapButton.addClickListener(this::openDialog);

      dataInfoContentLayout.add(coursesCountLabel, differentTopicsCountLabel, differentVendorsCountLabel,
            differentCitiesCountLabel, differentBundeslandCountLabel, averageWordCountInCourseTitlesLabel,
            averageWordCountInCourseDescriptionLabel);
      upperLayout.add(dataInfoTitleLabel, dataInfoContentLayout);
      bottomLayout.add(openMapTitleLabel, openMapButton);

      return dataInfoLayout;
   }

   private void openDialog(ClickEvent<Button> e){
      Dialog dialog = new Dialog();
      dialog.setHeaderTitle("Standorte der Kurse");
      dialog.setWidth("1000px");
      dialog.setHeight("800px");
      dialog.open();

      VerticalLayout dialogLayout = new VerticalLayout();
      dialogLayout.setSizeFull();
      dialog.add(dialogLayout);

      LMap map = new LMap(51.25, 10.26, 6);
      map.setTileLayer(LTileLayer.DEFAULT_OPENSTREETMAP_TILE);
      map.setSizeFull();
      dialogLayout.add(map);

      List<LMarker> markers = getMarkersFromCourses();
      for(LMarker marker : markers)
         map.addLComponents(marker);

      HorizontalLayout buttonLayout = new HorizontalLayout();
      buttonLayout.setWidthFull();
      buttonLayout.setJustifyContentMode(JustifyContentMode.END);
      dialogLayout.add(buttonLayout);

      Button closeButton = new Button("Karte schließen");
      closeButton.addClickListener(c -> dialog.close());
      buttonLayout.add(closeButton);
   }

   private List<LMarker> getMarkersFromCourses(){
      List<LMarker> markers = new ArrayList<>();

      // filter out courses with same latitude and longitude
      List<Course> filteredCourses = new ArrayList<>();
      for(Course course : courses){
         if(course.getKursstadt() != null && course.getLatitude() != null && course.getLongitude() != null &&
               course.getLatitude() > 45 && course.getLatitude() < 55 && course.getLongitude() > 5 && course.getLongitude() < 15){
            boolean exists = false;
            for(Course filteredCourse : filteredCourses){
               if(filteredCourse.getLatitude().equals(course.getLatitude()) && filteredCourse.getLongitude().equals(course.getLongitude())){
                  exists = true;
                  break;
               }
            }
            if(!exists)
               filteredCourses.add(course);
         }
      }

      for(Course course : filteredCourses){
         LMarker marker = new LMarker(course.getLatitude(), course.getLongitude());
         String cleanAnbieterlink = course.getAnbieterlink();
         if(course.getAnbieterlink() != null && !course.getAnbieterlink().startsWith("http://") && !course.getAnbieterlink().startsWith("https://"))
            cleanAnbieterlink = "https://" + course.getAnbieterlink();

         marker.setPopup("<a href='" + cleanAnbieterlink + "' target='" + AnchorTarget.BLANK.getValue() + "'>" + course.getAnbietername() + "</a>");
         markers.add(marker);
      }
      return markers;
   }

   private String getDifferentTopicsCount(){
      Set<String> topics = new HashSet<>();
      for(Course course : courses){
         String topic = course.getKurstitel();
         if(StringUtils.isBlank(topic))
            topic = "Kein Kurstitel definiert";
         topics.add(topic);
      }
      return String.valueOf(topics.size());
   }

   private String getDifferentVendorsCount(){
      Set<String> vendors = new HashSet<>();
      for(Course course : courses){
         String vendor = course.getAnbietername();
         if(StringUtils.isBlank(vendor))
            vendor = "Kein Anbieter definiert";
         vendors.add(vendor);
      }
      return String.valueOf(vendors.size());
   }

   private String getDifferentCitiesCount(){
      Set<String> cities = new HashSet<>();
      for(Course course : courses){
         String city = course.getKursstadt();
         if(StringUtils.isBlank(city))
            city = "Kein Ort definiert";
         cities.add(city);
      }
      return String.valueOf(cities.size());
   }

   private String getDifferentBundeslandCount(){
      Set<String> bundeslaender = new HashSet<>();
      for(Course course : courses){
         String bundesland = course.getBundesland();
         if(StringUtils.isBlank(bundesland))
            bundesland = "Kein Bundesland definiert";
         bundeslaender.add(bundesland);
      }
      return String.valueOf(bundeslaender.size());
   }

   private String getAverageWordCountInCourseDescription(){
      int wordCount = 0;
      for(Course course : courses){
         String description = course.getKursbeschreibung();
         if(StringUtils.isNotBlank(description))
            wordCount += description.split(" ").length;
      }
      return String.valueOf(wordCount / courses.size());
   }

   private String getAverageWordCountInCourseTitles(){
      int wordCount = 0;
      for(Course course : courses){
         String title = course.getKurstitel();
         if(StringUtils.isNotBlank(title))
            wordCount += title.split(" ").length;
      }
      return String.valueOf(wordCount / courses.size());
   }

   private ApexCharts createCityDonutChart(){
      LinkedHashMap<String, Double> cityCountMap = new LinkedHashMap<>();
      for(Course course : courses){
         String city = course.getKursstadt();
         if(StringUtils.isBlank(city))
            city = "Kein Ort definiert";
         if(cityCountMap.containsKey(city))
            cityCountMap.put(city, cityCountMap.get(city) + 1);
         else
            cityCountMap.put(city, 1D);
      }

      // sort map by value descending
      cityCountMap = sortMapByValueDescending(cityCountMap);

      // count all courses from 10th city in the cityCountMap
      double otherCoursesCount = 0;
      double ninthCityValue = 0;
      int i = 0;
      for(Map.Entry<String, Double> entry : cityCountMap.entrySet()){
         if(i == 8)
            ninthCityValue = entry.getValue();
         else if(i >= 9)
            otherCoursesCount += entry.getValue();
         i++;
      }

      double finalNinthCityValue = ninthCityValue;
      cityCountMap.entrySet().removeIf(entry -> entry.getValue() < finalNinthCityValue);

      // put other courses count to beginning of map
      cityCountMap.put("Andere Städte", otherCoursesCount);

      cityCountMap = sortMapByValueDescending(cityCountMap);

      String[] cityNames = cityCountMap.keySet().toArray(new String[0]);
      Double[] coursesCount = cityCountMap.values().toArray(new Double[0]);

      ApexCharts cityDonutChart = new ApexChartsBuilder()
            .withChart(ChartBuilder.get().withType(Type.DONUT).build())
            .withTitle(TitleSubtitleBuilder.get().withText("Kursstädte").build())
            .withLabels(cityNames)
            .withSeries(coursesCount)
            .withColors(chartColors)
            .build();
      return cityDonutChart;
   }

   private LinkedHashMap<String, Double> sortMapByValueDescending(LinkedHashMap<String, Double> cityCountMap){
      return cityCountMap.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
   }

   private ApexCharts createBundeslandHorizontalBarChart(){
      Map<String, Double> bundeslandCountMap = new HashMap<>();
      for(Course course : courses){
         String bundesland = course.getBundesland();
         if(StringUtils.isBlank(bundesland))
            bundesland = "Keine Angabe";
         if(bundeslandCountMap.containsKey(bundesland))
            bundeslandCountMap.put(bundesland, bundeslandCountMap.get(bundesland) + 1);
         else
            bundeslandCountMap.put(bundesland, 1D);
      }

      ApexCharts bundeslandHorizontalBarChart = new ApexChartsBuilder()
            .withChart(ChartBuilder.get().withType(Type.BAR).build())
            .withTitle(TitleSubtitleBuilder.get().withText("Anzahl Kurse nach Bundesland").build())
            .withLabels(bundeslandCountMap.keySet().toArray(new String[0]))
            .withSeries(new Series<>(bundeslandCountMap.values().toArray(new Double[0])))
            .withPlotOptions(PlotOptionsBuilder.get()
                  .withBar(BarBuilder.get()
                        .withHorizontal(true)
                        .build())
                  .build())
            .withDataLabels(DataLabelsBuilder.get()
                  .withEnabled(false)
                  .build())
            .withXaxis(XAxisBuilder.get()
                  .withCategories()
                  .withMax(1500D)
                  .build())
            .build();
      return bundeslandHorizontalBarChart;
   }

   private ApexCharts createTopicsRadarChart(){
      List<String> allKursbeschreibungen = courseService.findAll().stream().map(Course::getKursbeschreibung).filter(StringUtils::isNotBlank).toList();
      List<String> allKurstitel = courseService.findAll().stream().map(Course::getKurstitel).filter(StringUtils::isNotBlank).toList();
      List<String> combinedList = new ArrayList<>(Stream.of(allKursbeschreibungen, allKurstitel).flatMap(List::stream).toList());
      combinedList.replaceAll(s -> s.replaceAll("-", " "));

      long countOnline = combinedList.stream().filter(s -> StringUtils.containsIgnoreCase(s, "online")).count();
      long countGrundlagen = combinedList.stream().filter(s -> StringUtils.containsIgnoreCase(s, "grundlagen")).count();
      long countVertiefung = combinedList.stream().filter(s -> StringUtils.containsIgnoreCase(s, "vertiefung")).count();
      long countPresentation = combinedList.stream().filter(s -> StringUtils.containsIgnoreCase(s, "präsentation")).count();
      long countMicrosoft = combinedList.stream().filter(s -> StringUtils.containsIgnoreCase(s, "microsoft")).count();
      long countEinfuehrung = combinedList.stream().filter(s -> StringUtils.containsIgnoreCase(s, "einführung")).count();
      long countManagement = combinedList.stream().filter(s -> StringUtils.containsIgnoreCase(s, "management")).count();
      long countAusbildung = combinedList.stream().filter(s -> StringUtils.containsIgnoreCase(s, "ausbildung")).count();
      long countKommunikation = combinedList.stream().filter(s -> StringUtils.containsIgnoreCase(s, "kommunikation")).count();
      long countProjekt = combinedList.stream().filter(s -> StringUtils.containsIgnoreCase(s, "projekt")).count();

      ApexCharts topicsRadarChart = ApexChartsBuilder.get()
            .withChart(ChartBuilder.get()
                  .withType(Type.RADAR)
                  .build())
            .withTitle(TitleSubtitleBuilder.get()
                  .withText("Häufig verwendete Stichwörter")
                  .build())
            .withSeries(new Series<>("Themen", countOnline, countGrundlagen, countVertiefung, countPresentation, countMicrosoft, countEinfuehrung, countManagement, countAusbildung, countKommunikation, countProjekt))
            .withXaxis(XAxisBuilder.get()
                  .withCategories("Online", "Grundlagen", "Vertiefung", "Präsentation", "Microsoft", "Einführung", "Management", "Ausbildung", "Kommunikation", "Projekt")
                  .build())
            .build();

      return topicsRadarChart;
   }

   private ApexCharts createVendorsPerMonthVerticalBarChart(){
      Set<Course> businessVendors = new HashSet<>();
      Set<Course> educationalAndSocietyVendors = new HashSet<>();
      Set<Course> otherVendors = new HashSet<>();

      for(Course course : courses){
         String vendor = course.getAnbietername();
         if(StringUtils.isBlank(vendor))
            otherVendors.add(course);
         else if(vendor.contains("mbH") || vendor.contains("AG"))
            businessVendors.add(course);
         else if(vendor.contains("Schule") || vendor.contains("schule") || vendor.contains("Akademie") || vendor.contains("Universität") || vendor.contains("Institut") || vendor.contains("e.V.") || vendor.contains("e. V."))
            educationalAndSocietyVendors.add(course);
         else
            otherVendors.add(course);
      }

      // remove if getKursbeginn is null
      businessVendors.removeIf(course -> course.getKursbeginn() == null);
      educationalAndSocietyVendors.removeIf(course -> course.getKursbeginn() == null);
      otherVendors.removeIf(course -> course.getKursbeginn() == null);

      String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"};

      // create List with business vendors per month
      List<String> businessVendorsPerMonth = new ArrayList<>();
      for(int i = 1; i <= 12; i++){
         int finalI = i;
         businessVendorsPerMonth.add(String.valueOf(businessVendors.stream().filter(course -> LadUtil.convertDateToLocalDate(course.getKursbeginn()).getMonthValue() == finalI).count()));
      }

      // create List with educational and society vendors per month
      List<String> educationalAndSocietyVendorsPerMonth = new ArrayList<>();
      for(int i = 1; i <= 12; i++){
         int finalI = i;
         educationalAndSocietyVendorsPerMonth.add(String.valueOf(educationalAndSocietyVendors.stream().filter(course -> LadUtil.convertDateToLocalDate(course.getKursbeginn()).getMonthValue() == finalI).count()));
      }

      // create List with other vendors per month
      List<String> otherVendorsPerMonth = new ArrayList<>();
      for(int i = 1; i <= 12; i++){
         int finalI = i;
         otherVendorsPerMonth.add(String.valueOf(otherVendors.stream().filter(course -> LadUtil.convertDateToLocalDate(course.getKursbeginn()).getMonthValue() == finalI).count()));
      }

      ApexCharts vendorsPerMonthVerticalBarChart = new ApexChartsBuilder()
            .withChart(ChartBuilder.get()
                  .withType(Type.BAR)
                  .build())
            .withTitle(TitleSubtitleBuilder.get().withText("Anbieter pro Monat").build())
            .withPlotOptions(PlotOptionsBuilder.get()
                  .withBar(BarBuilder.get()
                        .withHorizontal(false)
                        .withColumnWidth("55%")
                        .build())
                  .build())
            .withDataLabels(DataLabelsBuilder.get()
                  .withEnabled(false).build())
            .withStroke(StrokeBuilder.get()
                  .withShow(true)
                  .withWidth(2.0)
                  .withColors("transparent")
                  .build())
            .withSeries(new Series<>("Kommerzielle Anbieter", businessVendorsPerMonth.toArray()),
                  new Series<>("Bildungseinrichtungen und Vereine", educationalAndSocietyVendorsPerMonth.toArray()),
                  new Series<>("Sonstige Anbieter", otherVendorsPerMonth.toArray()))
            .withXaxis(XAxisBuilder.get().withCategories(monthNames).build())
            .withYaxis(YAxisBuilder.get().withMax(3500).build())
            .withFill(FillBuilder.get()
                  .withOpacity(1.0).build())
            .build();
      return vendorsPerMonthVerticalBarChart;
   }

   private ApexCharts createVendorPieChart(){
      Set<Course> noVendors = new HashSet<>();
      Set<Course> gmbhVendors = new HashSet<>();
      Set<Course> agVendors = new HashSet<>();
      Set<Course> societyVendors = new HashSet<>();
      Set<Course> vhsVendors = new HashSet<>();
      Set<Course> otherSchoolsVendors = new HashSet<>();
      Set<Course> academyVendors = new HashSet<>();
      Set<Course> universityVendors = new HashSet<>();
      Set<Course> instituteVendors = new HashSet<>();
      Set<Course> otherVendors = new HashSet<>();

      for(Course course : courses){
         String vendor = course.getAnbietername();
         if(StringUtils.isBlank(vendor))
            noVendors.add(course);
         else if(vendor.contains("mbH"))
            gmbhVendors.add(course);
         else if(vendor.contains("AG"))
            agVendors.add(course);
         else if(vendor.contains("e.V.") || vendor.contains("e. V."))
            societyVendors.add(course);
         else if(vendor.contains("Volkshochschule"))
            vhsVendors.add(course);
         else if(vendor.contains("Schule") || vendor.contains("schule"))
            otherSchoolsVendors.add(course);
         else if(vendor.contains("Akademie") || vendor.contains("akademie"))
            academyVendors.add(course);
         else if(vendor.contains("Universität") || vendor.contains("universität"))
            universityVendors.add(course);
         else if(vendor.contains("Institut"))
            instituteVendors.add(course);
         else
            otherVendors.add(course);
      }

      // create LinkedHashMap with key = vendor name and value = count of courses
      LinkedHashMap<String, Double> vendorCountMap = new LinkedHashMap<>();
      vendorCountMap.put("Kein Anbieter definiert", (double) noVendors.size());
      vendorCountMap.put("GmbH", (double) gmbhVendors.size());
      vendorCountMap.put("AG", (double) agVendors.size());
      vendorCountMap.put("e.V.", (double) societyVendors.size());
      vendorCountMap.put("Volkshochschule", (double) vhsVendors.size());
      vendorCountMap.put("Schule", (double) otherSchoolsVendors.size());
      vendorCountMap.put("Akademie", (double) academyVendors.size());
      vendorCountMap.put("Universität", (double) universityVendors.size());
      vendorCountMap.put("Institut", (double) instituteVendors.size());
      vendorCountMap.put("Sonstige", (double) otherVendors.size());

      // sort LinkedHashMap by value descending
      vendorCountMap = sortMapByValueDescending(vendorCountMap);

      String[] vendorNames = vendorCountMap.keySet().toArray(new String[0]);
      Double[] vendorCount = vendorCountMap.values().toArray(new Double[0]);

      ApexCharts vendorPieChart = new ApexChartsBuilder()
            .withChart(ChartBuilder.get().withType(Type.PIE).build())
            .withTitle(TitleSubtitleBuilder.get().withText("Anbietertypen").build())
            .withLabels(vendorNames)
            .withSeries(vendorCount)
            .withLegend(LegendBuilder.get()
                  .withPosition(Position.RIGHT)
                  .build())
            .withColors(chartColors)
            .build();
      return vendorPieChart;
   }

   private VerticalLayout createWordCloudLayout(){
      VerticalLayout wordCloudLayout = new VerticalLayout();
      Label wordCloudLabel = new Label("Word Cloud aus Kurstiteln und -beschreibungen");
      wordCloudLabel.getElement().getStyle().set("font-family", "Helvetica, Arial, sans-serif");
      wordCloudLabel.getElement().getStyle().set("font-size", "14px");
      wordCloudLabel.getElement().getStyle().set("font-weight", "900");
      wordCloudLayout.add(wordCloudLabel);

      Image wordCloudImage = new Image("images/wordcloud.png", "Word Cloud");
      wordCloudImage.getElement().getStyle().set("object-fit", "contain");
      wordCloudImage.getElement().getStyle().set("margin", "auto");
      wordCloudImage.setMaxWidth("85%");
      wordCloudLayout.add(wordCloudImage);

      return wordCloudLayout;
   }
}