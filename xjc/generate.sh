#!/bin/bash

CURDIR=`pwd`
cd ..
PARENTDIR=`pwd`

XSDDIR=$PARENTDIR/src/main/resources/s-ramp
JAVADIR=$PARENTDIR/src/main/java

xjc $XSDDIR/*.xsd -d $JAVADIR
