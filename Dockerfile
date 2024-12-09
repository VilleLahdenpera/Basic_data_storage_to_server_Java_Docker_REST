FROM openjdk:17-jdk-slim

WORKDIR /app

COPY send_user_input.java .

# Compile
RUN javac send_user_input.java

# Run
CMD ["java", "send_user_input"]
