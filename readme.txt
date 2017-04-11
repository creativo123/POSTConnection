	==============================================================
	||===========================================================||
	||			Creativo			     ||
	||		Developed by - Keval Choudhary		     ||
	||			www.creativek.me		     ||
	||===========================================================||
	===============================================================

	###################
	## POSTConnection##
	################### 
This class takes most of the hurdles of data connectivity required to connect to an Android API.

Steps to use

1) copy the POSTConnection class to your project.
2) create an object of the class.
3) implement doOnsuccess() and doOnFail method
* doOnSuccess is called on HTTP REQUEST CODE 200.
* doOnFail is called when HTTP REQUEST CODE is not 200. example 404,400, etc.
4) create Hashmap of parameters.
5) Defualt method is POST, you can make any changes to HttpURLConnection object by calling getConnection() before calling Connect().
6) Copy sample Api from creativo-php-script