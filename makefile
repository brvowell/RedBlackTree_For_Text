# Makefile for Assignment 2

top:
	javac -d classfiles -sourcepath src src/main/Trees.java

test:
	java -classpath classfiles main.Trees -1 data rbcommands
	java -classpath classfiles main.Trees -2 data rbcommands

clean:
	rm classfiles/main/*
