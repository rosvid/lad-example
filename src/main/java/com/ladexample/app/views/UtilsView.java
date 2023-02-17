package com.ladexample.app.views;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.PixelBoundaryBackground;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import com.ladexample.app.Application;
import com.ladexample.app.data.entity.Course;
import com.ladexample.app.data.service.CourseService;
import com.ladexample.app.utils.LadUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@PageTitle("Utils")
@Route(value = "utils", layout = MainLayout.class)
public class UtilsView extends VerticalLayout{
   private final CourseService courseService;
   private final Logger logger = LoggerFactory.getLogger(Application.class);
   private Button importButton;
   private Button wordCloudButton;

   public UtilsView(CourseService courseService) throws IOException{
      this.courseService = courseService;

      createLayout();
   }

   private void createLayout(){
      // create two buttons: "Import from JSON" and "Generate Word Cloud"
      importButton = new Button("Import from JSON", this::importFromJSON);
      wordCloudButton = new Button("Generate Word Cloud", this::generateWordCloud);
      add(importButton, wordCloudButton);
   }

   private void importFromJSON(ClickEvent<Button> buttonClickEvent){
      File file = new File("src/main/resources/Top20k.JSON");
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

      try{
         List<String> jsonListFromFile = LadUtil.getJsonListFromFile(file);
         logger.info("Courses read: " + jsonListFromFile.size());

         List<Course> courses = new ArrayList<>();
         for(String json : jsonListFromFile){
            Course course = mapper.readValue(json, Course.class);
            courses.add(course);
         }

         List<Course> savedCourses = courseService.saveAll(courses);
         logger.info("Courses saved: " + savedCourses.size());
      }
      catch(Exception e){
         e.printStackTrace();
      }
      importButton.setText("JSON import finished");
      importButton.setEnabled(false);
   }

   private void generateWordCloud(ClickEvent<Button> buttonClickEvent){
      final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
      frequencyAnalyzer.setWordFrequenciesToReturn(1000);
      frequencyAnalyzer.setMinWordLength(3);
      frequencyAnalyzer.setStopWords(loadStopWords());

      List<String> allKursbeschreibungen = courseService.findAll().stream().map(Course::getKursbeschreibung).filter(StringUtils::isNotBlank).toList();
      List<String> allKurstitel = courseService.findAll().stream().map(Course::getKurstitel).filter(StringUtils::isNotBlank).toList();
      List<String> combinedList = new ArrayList<>(Stream.of(allKursbeschreibungen, allKurstitel).flatMap(List::stream).toList());
      combinedList.replaceAll(s -> s.replaceAll("-", " "));
      logger.info("Word Cloud words: " + combinedList.size());

      final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(combinedList);
      final Dimension dimension = new Dimension(1111, 841);
      final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
      wordCloud.setPadding(2);
      wordCloud.setKumoFont(new KumoFont("Marker Felt", FontWeight.PLAIN));
      wordCloud.setAngleGenerator(new AngleGenerator(0, -90, 2));
      wordCloud.setBackgroundColor(Color.WHITE);
      try{
         wordCloud.setBackground(new PixelBoundaryBackground("src/main/resources/grad-hat.png"));
      }
      catch(IOException e){
         e.printStackTrace();
      }
      wordCloud.setColorPalette(new ColorPalette(new Color(0x000000), new Color(0x0019A6), new Color(0x9D8335)));
      wordCloud.setFontScalar(new SqrtFontScalar(11, 89));
      wordCloud.build(wordFrequencies);
      wordCloud.writeToFile("/wordcloud_" + System.currentTimeMillis() + ".png");

      wordCloudButton.setText("Word Cloud generated");
      wordCloudButton.setEnabled(false);
   }

   private static Set<String> loadStopWords(){
      try{
         return new HashSet<>(IOUtils.readLines(getInputStream("german_stopwords_full.txt")));
      }
      catch(IOException e){
         throw new RuntimeException(e);
      }
   }

   private static InputStream getInputStream(String path){
      return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
   }
}