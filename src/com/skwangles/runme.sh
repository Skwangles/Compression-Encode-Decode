#!/bin/bash

cat test.txt | java LZWencode.java | java LZWpack.java | java LZWunpack.java | java LZWdecode.java
