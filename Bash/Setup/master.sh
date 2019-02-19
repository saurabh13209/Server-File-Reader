sudo echo '# This is the main Apache server configuration file.  It contains the' >> /etc/apache2/apache2.conf
sudo echo '# configuration directives that give the server its instructions.' >> /etc/apache2/apache2.conf
sudo echo '# See http://httpd.apache.org/docs/2.4/ for detailed information about' >> /etc/apache2/apache2.conf
sudo echo '# the directives and /usr/share/doc/apache2/README.Debian about Debian specific' >> /etc/apache2/apache2.conf
sudo echo '# hints.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# Summary of how the Apache 2 configuration works in Debian:' >> /etc/apache2/apache2.conf
sudo echo '# The Apache 2 web server configuration in Debian is quite different to' >> /etc/apache2/apache2.conf
sudo echo '# upstream's suggested way to configure the web server. This is because Debian's' >> /etc/apache2/apache2.conf
sudo echo '# default Apache2 installation attempts to make adding and removing modules,' >> /etc/apache2/apache2.conf
sudo echo '# virtual hosts, and extra configuration directives as flexible as possible, in' >> /etc/apache2/apache2.conf
sudo echo '# order to make automating the changes and administering the server as easy as' >> /etc/apache2/apache2.conf
sudo echo '# possible.' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# It is split into several files forming the configuration hierarchy outlined' >> /etc/apache2/apache2.conf
sudo echo '# below, all located in the /etc/apache2/ directory:' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '#	/etc/apache2/' >> /etc/apache2/apache2.conf
sudo echo '#	|-- apache2.conf' >> /etc/apache2/apache2.conf
sudo echo '#	|	`--  ports.conf' >> /etc/apache2/apache2.conf
sudo echo '#	|-- mods-enabled' >> /etc/apache2/apache2.conf
sudo echo '#	|	|-- *.load' >> /etc/apache2/apache2.conf
sudo echo '#	|	`-- *.conf' >> /etc/apache2/apache2.conf
sudo echo '#	|-- conf-enabled' >> /etc/apache2/apache2.conf
sudo echo '#	|	`-- *.conf' >> /etc/apache2/apache2.conf
sudo echo '# 	`-- sites-enabled' >> /etc/apache2/apache2.conf
sudo echo '#	 	`-- *.conf' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# * apache2.conf is the main configuration file (this file). It puts the pieces' >> /etc/apache2/apache2.conf
sudo echo '#   together by including all remaining configuration files when starting up the' >> /etc/apache2/apache2.conf
sudo echo '#   web server.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# * ports.conf is always included from the main configuration file. It is' >> /etc/apache2/apache2.conf
sudo echo '#   supposed to determine listening ports for incoming connections which can be' >> /etc/apache2/apache2.conf
sudo echo '#   customized anytime.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# * Configuration files in the mods-enabled/, conf-enabled/ and sites-enabled/' >> /etc/apache2/apache2.conf
sudo echo '#   directories contain particular configuration snippets which manage modules,' >> /etc/apache2/apache2.conf
sudo echo '#   global configuration fragments, or virtual host configurations,' >> /etc/apache2/apache2.conf
sudo echo '#   respectively.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '#   They are activated by symlinking available configuration files from their' >> /etc/apache2/apache2.conf
sudo echo '#   respective *-available/ counterparts. These should be managed by using our' >> /etc/apache2/apache2.conf
sudo echo '#   helpers a2enmod/a2dismod, a2ensite/a2dissite and a2enconf/a2disconf. See' >> /etc/apache2/apache2.conf
sudo echo '#   their respective man pages for detailed information.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# * The binary is called apache2. Due to the use of environment variables, in' >> /etc/apache2/apache2.conf
sudo echo '#   the default configuration, apache2 needs to be started/stopped with' >> /etc/apache2/apache2.conf
sudo echo '#   /etc/init.d/apache2 or apache2ctl. Calling /usr/bin/apache2 directly will not' >> /etc/apache2/apache2.conf
sudo echo '#   work with the default configuration.' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# Global configuration' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo "# ServerRoot: The top of the directory tree under which the server's" >> /etc/apache2/apache2.conf
sudo echo '# configuration, error, and log files are kept.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# NOTE!  If you intend to place this on an NFS (or otherwise network)' >> /etc/apache2/apache2.conf
sudo echo '# mounted filesystem then please read the Mutex documentation (available' >> /etc/apache2/apache2.conf
sudo echo '# at <URL:http://httpd.apache.org/docs/2.4/mod/core.html#mutex>);' >> /etc/apache2/apache2.conf
sudo echo '# you will save yourself a lot of trouble.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# Do NOT add a slash at the end of the directory path.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '#ServerRoot "/etc/apache2"' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# The accept serialization lock file MUST BE STORED ON A LOCAL DISK.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '#Mutex file:${APACHE_LOCK_DIR} default' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# The directory where shm and other runtime files will be stored.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo 'DefaultRuntimeDir ${APACHE_RUN_DIR}' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# PidFile: The file in which the server should record its process' >> /etc/apache2/apache2.conf
sudo echo '# identification number when it starts.' >> /etc/apache2/apache2.conf
sudo echo '# This needs to be set in /etc/apache2/envvars' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo 'PidFile ${APACHE_PID_FILE}' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# Timeout: The number of seconds before receives and sends time out.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo 'Timeout 300' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# KeepAlive: Whether or not to allow persistent connections (more than' >> /etc/apache2/apache2.conf
sudo echo '# one request per connection). Set to "Off" to deactivate.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo 'KeepAlive On' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# MaxKeepAliveRequests: The maximum number of requests to allow' >> /etc/apache2/apache2.conf
sudo echo '# during a persistent connection. Set to 0 to allow an unlimited amount.' >> /etc/apache2/apache2.conf
sudo echo '# We recommend you leave this number high, for maximum performance.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo 'MaxKeepAliveRequests 100' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# KeepAliveTimeout: Number of seconds to wait for the next request from the' >> /etc/apache2/apache2.conf
sudo echo '# same client on the same connection.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo 'KeepAliveTimeout 5' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# These need to be set in /etc/apache2/envvars' >> /etc/apache2/apache2.conf
sudo echo 'User ${APACHE_RUN_USER}' >> /etc/apache2/apache2.conf
sudo echo 'Group ${APACHE_RUN_GROUP}' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# HostnameLookups: Log the names of clients or just their IP addresses' >> /etc/apache2/apache2.conf
sudo echo '# e.g., www.apache.org (on) or 204.62.129.132 (off).' >> /etc/apache2/apache2.conf
sudo echo "# The default is off because it'd be overall better for the net if people" >> /etc/apache2/apache2.conf
sudo echo '# had to knowingly turn this feature on, since enabling it means that' >> /etc/apache2/apache2.conf
sudo echo '# each client request will result in AT LEAST one lookup request to the' >> /etc/apache2/apache2.conf
sudo echo '# nameserver.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo 'HostnameLookups Off' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# ErrorLog: The location of the error log file.' >> /etc/apache2/apache2.conf
sudo echo '# If you do not specify an ErrorLog directive within a <VirtualHost>' >> /etc/apache2/apache2.conf
sudo echo '# container, error messages relating to that virtual host will be' >> /etc/apache2/apache2.conf
sudo echo '# logged here.  If you *do* define an error logfile for a <VirtualHost>' >> /etc/apache2/apache2.conf
sudo echo "# container, that host's errors will be logged there and not here." >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo 'ErrorLog ${APACHE_LOG_DIR}/error.log' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# LogLevel: Control the severity of messages logged to the error_log.' >> /etc/apache2/apache2.conf
sudo echo '# Available values: trace8, ..., trace1, debug, info, notice, warn,' >> /etc/apache2/apache2.conf
sudo echo '# error, crit, alert, emerg.' >> /etc/apache2/apache2.conf
sudo echo '# It is also possible to configure the log level for particular modules, e.g.' >> /etc/apache2/apache2.conf
sudo echo '# "LogLevel info ssl:warn"' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo 'LogLevel warn' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# Include module configuration:' >> /etc/apache2/apache2.conf
sudo echo 'IncludeOptional mods-enabled/*.load' >> /etc/apache2/apache2.conf
sudo echo 'IncludeOptional mods-enabled/*.conf' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# Include list of ports to listen on' >> /etc/apache2/apache2.conf
sudo echo 'Include ports.conf' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# Sets the default security model of the Apache2 HTTPD server. It does' >> /etc/apache2/apache2.conf
sudo echo '# not allow access to the root filesystem outside of /usr/share and /var/www.' >> /etc/apache2/apache2.conf
sudo echo '# The former is used by web applications packaged in Debian,' >> /etc/apache2/apache2.conf
sudo echo '# the latter may be used for local directories served by the web server. If' >> /etc/apache2/apache2.conf
sudo echo '# your system is serving content from a sub-directory in /srv you must allow' >> /etc/apache2/apache2.conf
sudo echo '# access here, or in any related virtual host.' >> /etc/apache2/apache2.conf
sudo echo '<Directory />' >> /etc/apache2/apache2.conf
sudo echo '    Options Indexes FollowSymLinks Includes ExecCGI' >> /etc/apache2/apache2.conf
sudo echo '    AllowOverride All' >> /etc/apache2/apache2.conf
sudo echo '    Require all granted' >> /etc/apache2/apache2.conf
sudo echo '</Directory>' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '<Directory /usr/share>' >> /etc/apache2/apache2.conf
sudo echo '	AllowOverride None' >> /etc/apache2/apache2.conf
sudo echo '	Require all granted' >> /etc/apache2/apache2.conf
sudo echo '</Directory>' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '<Directory /var/www/>' >> /etc/apache2/apache2.conf
sudo echo '	Options Indexes FollowSymLinks' >> /etc/apache2/apache2.conf
sudo echo '	AllowOverride None' >> /etc/apache2/apache2.conf
sudo echo '	Require all granted' >> /etc/apache2/apache2.conf
sudo echo '</Directory>' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#<Directory /srv/>' >> /etc/apache2/apache2.conf
sudo echo '#	Options Indexes FollowSymLinks' >> /etc/apache2/apache2.conf
sudo echo '#	AllowOverride None' >> /etc/apache2/apache2.conf
sudo echo '#	Require all granted' >> /etc/apache2/apache2.conf
sudo echo '#</Directory>' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# AccessFileName: The name of the file to look for in each directory' >> /etc/apache2/apache2.conf
sudo echo '# for additional configuration directives.  See also the AllowOverride' >> /etc/apache2/apache2.conf
sudo echo '# directive.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo 'AccessFileName .htaccess' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# The following lines prevent .htaccess and .htpasswd files from being' >> /etc/apache2/apache2.conf
sudo echo '# viewed by Web clients.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '<FilesMatch "^\.ht">' >> /etc/apache2/apache2.conf
sudo echo '	Require all denied' >> /etc/apache2/apache2.conf
sudo echo '</FilesMatch>' >> /etc/apache2/apache2.conf
sudo echo ' ' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# The following directives define some format nicknames for use with' >> /etc/apache2/apache2.conf
sudo echo '# a CustomLog directive.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# These deviate from the Common Log Format definitions in that they use %O' >> /etc/apache2/apache2.conf
sudo echo '# (the actual bytes sent including headers) instead of %b (the size of the' >> /etc/apache2/apache2.conf
sudo echo '# requested file), because the latter makes it impossible to detect partial' >> /etc/apache2/apache2.conf
sudo echo '# requests.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo '# Note that the use of %{X-Forwarded-For}i instead of %h is not recommended.' >> /etc/apache2/apache2.conf
sudo echo '# Use mod_remoteip instead.' >> /etc/apache2/apache2.conf
sudo echo '#' >> /etc/apache2/apache2.conf
sudo echo 'LogFormat "%v:%p %h %l %u %t \"%r\" %>s %O \"%{Referer}i\" \"%{User-Agent}i\"" vhost_combined' >> /etc/apache2/apache2.conf
sudo echo 'LogFormat "%h %l %u %t \"%r\" %>s %O \"%{Referer}i\" \"%{User-Agent}i\"" combined' >> /etc/apache2/apache2.conf
sudo echo 'LogFormat "%h %l %u %t \"%r\" %>s %O" common' >> /etc/apache2/apache2.conf
sudo echo 'LogFormat "%{Referer}i -> %U" referer' >> /etc/apache2/apache2.conf
sudo echo 'LogFormat "%{User-agent}i" agent' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# Include of directories ignores editors' and dpkg's backup files,' >> /etc/apache2/apache2.conf
sudo echo '# see README.Debian for details.' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# Include generic snippets of statements' >> /etc/apache2/apache2.conf
sudo echo 'IncludeOptional conf-enabled/*.conf' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# Include the virtual host configurations:' >> /etc/apache2/apache2.conf
sudo echo 'IncludeOptional sites-enabled/*.conf' >> /etc/apache2/apache2.conf
sudo echo '' >> /etc/apache2/apache2.conf
sudo echo '# vim: syntax=apache ts=4 sw=4 sts=4 sr noet' >> /etc/apache2/apache2.conf

