@not-implemented
Feature: Returning a Not Implemented Response

  Scenario: Making a method request unrecognized by the server returns a 501 Response
    Given I make a WONKY method request to "/simple_get"
    Then my response should have status code 501

  Scenario: Making a method request unrecognized by the server returns a 501 Response
    Given I make a BUSTED method request to "/simple_get"
    Then my response should have status code 501
