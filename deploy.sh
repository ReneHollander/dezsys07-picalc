#!/bin/bash

NAME=picalc_hollander_kalauner

rm -Rf $NAME $NAME.zip
mkdir -p $NAME

JARS=(picalc-balancer picalc-calculator picalc-client picalc-integrationtest)

for i in ${JARS[@]}; do
  cp ${i}/final/${i}.jar $NAME/
done

mkdir -p $NAME/doc
find files/docs/* \( -name '*.svg' -o -name '*.html' -o -name '*.pdf' \) -exec cp {} $NAME/doc \;
#cp files/docs/* $NAME/doc

git log --stat > $NAME/repo.log

zip -r $NAME.zip $NAME/*
