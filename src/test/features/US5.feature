Feature: Validez de las ofertas: fecha de publicación y fecha de vencimiento

Scenario: Publicación de oferta con fecha de publicacion y vencimiento futura
    Given Titulo de la oferta "Programador Java"
    When ingreso fecha de publicacion 2  dias despues de hoy and fecha vencimiento 4 dias despues de hoy and guardo la oferta
    Then se creo la oferta "Programador Java" con fecha de publicacion 2 dias despues de hoy and fecha vencimiento 4 dias despues de hoy 

Scenario: Publicación de oferta con fecha de vencimiento menor a la de publicacion
    Given Titulo de la oferta "Programador Java"
    When ingreso fecha de vencimiento 4 dias antes de hoy y fecha de publicacion hoy 
    Then no se creo la oferta con endDate menor a starDate

Scenario: Publicación de oferta con fecha de vencimiento pasada
    Given Titulo de la oferta "Programador Java"
    When ingreso fecha vencimiento 4 dias antes de hoy
    Then no se creo la oferta con endDate
