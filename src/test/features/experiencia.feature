Feature: El oferente especifica la experiencia requerida para las ofertas.

Scenario: El oferente especifica un numero de experiencia requerida para la oferta
	Given el oferente llena el campos tittle and llena el campo description
	When ingresa 4 como experiencia requerida and save nueva oferta de trabajo
	Then se crea una oferta con 4 como experiencia requerida

Scenario: El oferente no especifica un numero de experiencia requerida para la oferta
	Given el oferente llena el campos tittle and llena el campo description
	When save nueva oferta de trabajo
	Then se crea una oferta con 0 como experiencia requerida
