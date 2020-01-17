@not-implemented
Feature: Returning a Not Implemented Response

  Scenario: Making a DELETE method request to /simple_get returns a 501 Response
    Given I make a DELETE request to "/simple_get"
    Then my response should have status code 501
