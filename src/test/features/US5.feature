Feature: Validez de las ofertas: fecha de publicación y fecha de vencimiento

Scenario: Publicación de oferta con fecha de publicacion y vencimiento futura
    Given Titulo de la oferta "Programador Java"
    When ingreso fecha de publicacion "30/11/2017" and fecha vencimiento "14/12/2017" and guardo la oferta
    Then se creo la oferta "Programador Java" con fecha de publicacion "30/11/2017" and fecha vencimiento "14/12/2017" 

Scenario: Publicación de oferta con fecha de publicacion y vencimiento pasada
    Given Titulo de la oferta "Programador Java"
    When ingreso fecha de publicacion "30/10/2017" and fecha  vencimiento "31/10/2017" and guardo la oferta
    Then no se creo la oferta "Programador Java"

Scenario: Publicación de oferta con fecha de publicacion o vencimiento pasada
    Given Titulo de la oferta "Programador Java"
    When ingreso fecha de publicacion "30/11/2017" and fecha vencimiento "31/10/2017" and guardo la oferta
    Then no se creo la oferta "Programador Java"
