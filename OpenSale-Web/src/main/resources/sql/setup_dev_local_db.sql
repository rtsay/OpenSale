create database if not exists opensale_dev;
use opensale_dev;
grant all on opensale_dev.* to 'opensale'@'localhost' identified by 'opensale';
flush privileges;