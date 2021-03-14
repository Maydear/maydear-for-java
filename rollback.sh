#!/bin/bash
echo -e "\033[0;31m Run mvn release:prepare failed \033[0m";
echo -e "\033[0;36m Run mvn release:rollback ... \033[0m";
mvn clean release:rollback;
echo -e "\033[1;36m done! \033[0m"