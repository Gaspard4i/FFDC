#!/bin/bash
javac -d bin/ src/**/*.java -cp res/ ;
java -cp bin:res main.Main