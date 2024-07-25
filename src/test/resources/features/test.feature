Feature: T-Mobile website test

  Scenario: Add smartwatch to basket
    Given the user is on the T-Mobile homepage
    When the user selects "Urządzenia" from the top menu
    And the user clicks "Bez abonamentu" from the "Smartwatche i opaski" column
    And the user clicks the first item in the list
    And the user adds the product to the basket
    Then the user should see the basket page with correct prices
    When the user navigates back to the homepage
    Then the basket icon with correct number of items should be displayed