Feature: Validez de las ofertas: fecha de publicación y fecha de vencimiento

Scenario: Publicación de oferta con fecha de publicacion y vencimiento futura
    Given Titulo de la oferta "Programador Java"
    When ingreso fecha de publicacion 2  dias despues de hoy and fecha vencimiento 4 dias despues de hoy and guardo la oferta
    Then se creo la oferta "Programador Java" con fecha de publicacion 2 dias despues de hoy and fecha vencimiento 4 dias despues de hoy 

Scenario: Publicación de oferta con fecha de publicacion pasada
    Given Titulo de la oferta "Programador Java"
    When ingreso fecha de publicacion 4 dias antes de hoy 
    Then no se creo la oferta

Scenario: Publicación de oferta con fecha de vencimiento pasada
    Given Titulo de la oferta "Programador Java"
    When ingreso fecha de publicacion hoy and fecha vencimiento 4 dias antes de hoy
    Then no se creo la oferta
