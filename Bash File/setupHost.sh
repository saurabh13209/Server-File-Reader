echo "Welcome to SFR setup."
echo "Continue installation?(Y/N)"

read  contInst
if(let "contInst = 89");  then
    sudo apt-get update
fi
echo ""
echo "******************** Files updated ********************"
echo ""

echo "Install Apache? (Y/N)"
read installApache
if(let "installApache = 89");  then
    sudo apt-get install apache2
fi
echo ""
echo "******************** Apache updated ********************"
echo ""

i=1
for d in /home/* ; do
    echo "${i} ${d}"
    let "i++"
done
echo "choose your parent directory"
read parentDir
if (let "parentDir = 1"); then
    sudo gedit /etc/apache2/sites-available/000-default.conf
    sudo service apache2 restart

    sudo rm -rf /etc/apache2/apache2.conf

    sudo sh ./master.sh
    sudo service apache2 restart

    sudo ufw app info "Apache Full"
    sudo apt-get install mysql-server
    sudo apt-get install php libapache2-mod-php php-mcrypt php-mysql
    sudo systemctl restart apache2
    
    

else
    echo "NN"
fi
 

