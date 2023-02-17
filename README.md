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

## Dashboard screenshots
![dashboard-01](https://user-images.githubusercontent.com/15930548/219654180-2b49460a-9368-4a81-91a7-ab258fda5c8b.png)
![dashboard-02](https://user-images.githubusercontent.com/15930548/219654183-a9f1afb4-fe34-4d08-b555-16fcd3696797.png)
![dashboard-03](https://user-images.githubusercontent.com/15930548/219654170-eefd7885-4eb6-4c08-b2f4-57ed60461e76.png)
