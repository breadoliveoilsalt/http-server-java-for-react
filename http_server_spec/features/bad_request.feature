@bad-request
Feature: Returning a bad request response

  Scenario: Sending a jibberish request
    Given I make a nonsensical request to the server
    Then my response should have a status code of 400

  Scenario: Sending a request with a misordered status line
    Given I make a request with a misordered status line
    Then my response should have a status code of 400
