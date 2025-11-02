#!/bin/bash
JDK_PATH="/Users/freddycarias/Documents/Aprendisaje/ProyectoFinal/jdk-25.0.1+8/Contents/Home/bin"

# Compilar el código
$JDK_PATH/javac src/App.java

# Ejecutar el programa
$JDK_PATH/java -cp src App
