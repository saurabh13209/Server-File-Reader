echo "Welcome to Server File Reader setup."
echo "Continue installation?(Y/N)"

read  contInst
if(let "contInst = 89");  then
    sudo apt-get update
    echo ""
    echo "******************** Files updated ********************"
    echo ""
    sudo apt-get install apache2
    echo ""
    echo "******************** Apache updated ********************"
    echo ""
    clear
    echo "Task completed:"
    echo "1. Files updated"
    echo "2. Apache installed"
    echo ""
    echo "choose your parent directory"
    echo "*ch	parent_directory "
    i=1
    for d in /home/* ; do
        echo "${i} ${d}"
        let "i++"
    done
    echo "select any one"
    read parentDir
    i=1
    for d in /home/* ; do
        if(let "i = ${parentDir}"); then
	    sudo rm -rf /etc/apache2/sites-available/000-default.conf
            sudo sh ./setDir.sh $d
            break
        fi
    done
    sudo rm -rf /etc/apache2/apache2.conf
    sudo sh ./master.sh

    sudo ufw app info "Apache Full"
    sudo apt-get install mysql-server
    echo ""
    echo "******************** MySql installed successfully ********************"
    echo ""
    sudo apt-get install php libapache2-mod-php php-mcrypt php-mysql
    sudo apt-get install lamp-server^
    sudo apt-add-repository ppa:ondrej/php
    sudo apt-get update
    sudo apt-get install php7.0
    sudo apt install libapache2-mod-php7.0 libapache2-mod-php

    sudo service apache2 restart
    sudo systemctl restart apache2

    echo ""
    echo "******************** Setup installed successfully ********************"
    echo ""
    echo "Your server ip is :"
    sudo ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1'
    
fi




 

