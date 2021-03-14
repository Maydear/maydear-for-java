#!/usr/bin/env bash
# 上传SNAPSHOT版本到maven仓库
echo "Execute: mvn clean package deploy -DskipTests"
mvn clean package deploy -DskipTests
echo "Done."