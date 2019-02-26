echo ""
echo "$1"
echo ""
sudo echo '<VirtualHost *:80>' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# The ServerName directive sets the request scheme, hostname and port that' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# the server uses to identify itself. This is used when creating' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# redirection URLs. In the context of virtual hosts, the ServerName' >> /etc/apache2/sites-available/000-default.conf
sudo echo "	# specifies what hostname must appear in the request's Host: header to" >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# match this virtual host. For the default virtual host (this file) this' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# value is not decisive as it is used as a last resort host regardless.' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# However, you must set it for any further virtual host explicitly.' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	#ServerName www.example.com' >> /etc/apache2/sites-available/000-default.conf
sudo echo '' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	ServerAdmin webmaster@localhost' >> /etc/apache2/sites-available/000-default.conf

sudo echo "	DocumentRoot ${1}" >> /etc/apache2/sites-available/000-default.conf
sudo echo '' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# Available loglevels: trace8, ..., trace1, debug, info, notice, warn,' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# error, crit, alert, emerg.' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# It is also possible to configure the loglevel for particular' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# modules, e.g.' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	#LogLevel info ssl:warn' >> /etc/apache2/sites-available/000-default.conf
sudo echo '' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	ErrorLog ${APACHE_LOG_DIR}/error.log' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	CustomLog ${APACHE_LOG_DIR}/access.log combined' >> /etc/apache2/sites-available/000-default.conf
sudo echo '' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# For most configuration files from conf-available/, which are' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# enabled or disabled at a global level, it is possible to' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# include a line for only one particular virtual host. For example the' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# following line enables the CGI configuration for this host only' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	# after it has been globally disabled with "a2disconf".' >> /etc/apache2/sites-available/000-default.conf
sudo echo '	#Include conf-available/serve-cgi-bin.conf' >> /etc/apache2/sites-available/000-default.conf
sudo echo '</VirtualHost>' >> /etc/apache2/sites-available/000-default.conf
sudo echo '' >> /etc/apache2/sites-available/000-default.conf
sudo echo '# vim: syntax=apache ts=4 sw=4 sts=4 sr noet' >> /etc/apache2/sites-available/000-default.conf

cp index.php $1
cp audioUpload.php $1
cp mediaUpload.php $1
cp upload.php $1


