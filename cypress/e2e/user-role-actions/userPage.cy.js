describe("AdminPage E2E Tests", () => {
  before(() => {
    cy.visit("http://localhost:3000/signup");
    cy.get('[data-testid="signup-username"]').type("user1");
    cy.get('[data-testid="signup-password"]').type("user1");
    cy.get('[data-testid="signup-name"]').type("user1");
    cy.get('[data-testid="signup-email"]').type("user1@gmail.com");
    cy.get('[data-testid="signup-button"]').click();
  });
  beforeEach(() => {
    cy.visit("http://localhost:3000/login");
    cy.get('[data-testid="login-username"]').type("user1");
    cy.get('[data-testid="login-password"]').type("user1");
    cy.get('[data-testid="login-button"]').click();
  });

  it("should display user number", () => {
    cy.get('[data-testid="home-user-number"]').should("exist");
  });

  it("should be able to update user profile", () => {
    cy.get('[data-testid="userpage"]').click();
    cy.get('[data-testid="update-user-profile"]').click();
    cy.get('[data-testid="update-user-nickname"]').clear();
    cy.get('[data-testid="update-user-nickname"]').type("nickname");
    cy.get('[data-testid="update-user-email"]').clear();
    cy.get('[data-testid="update-user-email"]').type("updated@gmail.com");
    cy.get('[data-testid="update-user-button"]').click();
    cy.get('[data-testid="user-email"]').should(
      "have.text",
      "updated@gmail.com"
    );
    cy.get('[data-testid="user-name"]').should("have.text", "nickname");

    // test cancle update user button
    cy.get('[data-testid="update-user-profile"]').click();
    cy.get('[data-testid="update-user-nickname"]').clear();
    cy.get('[data-testid="update-user-nickname"]').type("nickname2");
    cy.get('[data-testid="update-user-email"]').clear();
    cy.get('[data-testid="update-user-email"]').type("updated2@gmail.com");
    cy.get('[data-testid="cancel-update-user-button"]').click();
    // user name and email should not change
    cy.get('[data-testid="user-email"]').should(
      "have.text",
      "updated@gmail.com"
    );
    cy.get('[data-testid="user-name"]').should("have.text", "nickname");
  });
});
