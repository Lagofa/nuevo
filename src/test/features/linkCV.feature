Feature: El postulante agrega un link a su CV cuando se postula a una jobOffer

Scenario: El postulante ingresa un link valido a su CV al postularse a una jobOffer
Given el postulante ha ingresado su nombre y un mail
When el postulante ingresa un link valido "https://www.linkedin.com/profile/view?id=AAMAABhp-akBJJDtLnfvsXb-3Ur2820svaUvhaU&trk=hp-identity-name" a su CV and se postula para la jobOffer
Then se envia mail al oferente de la jobOffer

Scenario: El postulante ingresa un link invalido a su CV al postularse a una jobOffer
Given el postulante ha ingresado su nombre y un mail
When el postulante ingresa un link invalido "hps://www.linkedin.com/profile/view?id=AAMAABhp-akBJJDtLnfvsXb-3Ur2820svaUvhaU&trk=hp-identity-name" a su CV and se postula para la jobOffer
Then no se envia mail al oferente de la jobOffer

Scenario: El postulante no ingresa un link a su CV al postularse a una jobOffer
Given el postulante ha ingresado su nombre y un mail
When el postulante no ingresa un link a su CV and se postula para la jobOffer
Then no se envia mail al oferente de la jobOffer
