@request-timeout
Feature: Request Timeout Response

  @simple-head
  Scenario: Making a request without CRLF to delineate end of request or metadata
    Given I make a GET request to "/simple_get" without a carriage-return-line-feed
    Then my response should have status code 408
