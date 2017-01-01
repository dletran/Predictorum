Feature:
  As an user
  I want to sign in
  So I can start using the app


  Scenario Outline: Sign in with valid credentials
    Given a registered user
    And a browser <browser>
    When user navigates to home page
    And user clicks on welcome button
    And user sets login.user field with value user_username
    And user sets login.password field with value user_password
    And user clicks on signIn button
    Then user is logged correctly
    And menu is displayed
    Examples:
    |browser|
    |chrome |
    |firefox|
    |explorer|


