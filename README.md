# LAD example

This project demonstrates a Learning Analytics Dashboard (LAD) using Vaadin. This project was made as part of the "Plattformen und Systeme fürs eLearning" course at the Goethe University Frankfurt.

For creating charts the ApexCharts add-on is used. The add-on is available at https://vaadin.com/directory/component/apexchartsjs.
For creating word clouds kumo is used. The library is available at https://github.com/kennycason/kumo.
For creating the map the LeafletMap add-on is used. The add-on is available at https://vaadin.com/directory/component/leafletmap-for-vaadin.

## Deploying and running to Production

The project can be built using the mvn clean package -Pproduction command. Then start the application using Spring Boot and open http://localhost:8080 in your browser.

When first running the application the JSON file should be imported. This can be done by visiting http://localhost:8080/utils.

## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/docs/components/app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.