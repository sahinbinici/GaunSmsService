# Multi-stage build için Maven ve JDK 17 kullanıyoruz
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Önce pom.xml'i kopyalayıp dependency'leri indiriyoruz (cache için)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Kaynak kodları kopyalayıp build ediyoruz
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage - daha küçük image için
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Build stage'den war dosyasını kopyalıyoruz
COPY --from=build /app/target/*.war app.war

# Port expose ediyoruz
EXPOSE 8080

# Uygulamayı başlatıyoruz
ENTRYPOINT ["java", "-jar", "app.war"]
