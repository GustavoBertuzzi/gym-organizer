# Etapa de build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
  
  # Copia o pom.xml e baixa as dependências primeiro (cache mais eficiente)
COPY pom.xml .
RUN mvn dependency:go-offline -B
  
  # Copia o código do projeto e faz o build
COPY src ./src
RUN mvn clean package -DskipTests -Pproduction
  
  # Etapa de runtime
FROM eclipse-temurin:17-jdk
WORKDIR /app
  
  # Copia o JAR gerado da etapa de build
COPY --from=build /app/target/*.jar app.jar
  
  # Expõe a porta padrão do Spring Boot
EXPOSE 8080
  
  # Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
