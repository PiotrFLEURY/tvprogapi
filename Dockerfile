FROM openjdk:26-oracle

# Set the working directory
WORKDIR /app
# Copy the JAR file into the container
COPY target/*.jar app.jar
# Copy xmltv.dtd file into the container
COPY xmltv.dtd xmltv.dtd
# Expose the port the app runs on
EXPOSE 8080
# Run the application
CMD ["java", "-jar", "-Xms512m", "-Xmx1024m", "app.jar"]