Feature: Como usuario quiero poder hacer busquedas en la pantalla de ofertas

	Scenario: El usuario realiza una busqueda con la palabra en mayuscula.
		Given ​existe una oferta de "programador java" And existe una oferta "programador php"​
	    When ​busco "JAVA"
	    Then ​encuentro la busqueda "programador java" And​ ​no encu​entro la busqueda "programador php"

	Scenario: El usuario realiza una busqueda con la palabra en minuscula.
		Given ​existe una oferta de "programador jaVa" And existe una oferta "programador C"​
	    When ​busco "java"
	    Then ​encuentro la busqueda "programador jaVa" And​ ​no encu​entro la busqueda "programador C"
		
	Scenario: El usuario realiza una busqueda con la palabra con letras mayusculas y minusculas.
		Given ​existe una oferta de "programador java" And existe una oferta "programador C"​
	    When ​busco "jaVa"
	    Then ​encuentro la busqueda "programador java" And​ ​no encu​entro la busqueda "programador C"

	Scenario: El usuario realiza una busqueda sin ninguna palabra.
		Given ​existe una oferta de "programador java" And existe una oferta "programador C"​
	    When ​busco ""
	    Then ​encuentro la busqueda "programador java" And​ ​encu​entro la busqueda "programador C"

	Scenario: El usuario realiza una busqueda con dos palabras.
		Given ​existe una oferta de "programador java" And existe una oferta "programador junior java"​
	    When ​busco "programador java"
	    Then ​encuentro la busqueda "programador java" And​ ​ no encu​entro la busqueda "programador junior java"
