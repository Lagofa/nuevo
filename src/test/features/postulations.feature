Feature: El oferente puede ver la cantidad total de postulaciones a su jobOffer

Scenario: Un oferente crea una jobOffer

Given el oferente ingreso todos los datos para crear la nueva jobOffer "Programador Java" 
When el oferente guarda la jobOffer
Then la cantidad de postulaciones a la jobOffer "Programador Java" es 0

Scenario: Un usuario se postula a la jobOffer

Given la cantidad de postulaciones de la jobOffer "Programador Java" es 0
When un usuario se postula a la oferta "Programador Java"
Then la cantidad de postulaciones a la jobOffer "Programador Java" es 0
