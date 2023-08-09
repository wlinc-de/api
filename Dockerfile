FROM maven as build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:20
WORKDIR /app
COPY --from=build /build/target/*.jar ./api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./api.jar"]