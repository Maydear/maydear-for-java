#!/bin/bash
# 打包正式版本
echo -e "\033[0;36m Run mvn release:prepare ...\033[0m";
mvn clean release:prepare release:perform release:clean;
echo -e "\033[1;36m done! \033[0m"
