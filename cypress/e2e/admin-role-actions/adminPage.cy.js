describe("AdminPage E2E Tests", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000/login");
    cy.get('[data-testid="login-username"]').type("admin");
    cy.get('[data-testid="login-password"]').type("admin");
    cy.get('[data-testid="login-button"]').click();
  });

  it("should display user number", () => {
    cy.get('[data-testid="home-user-number"]').should("exist");
  });

  it("should search for a user", () => {
    cy.get('[data-testid="adminpage"]').click();
    cy.get('[data-testid="user-list"]').should("exist");
    cy.get('[data-testid="user-username-search"]').type("user");
    cy.get('[data-testid="search-button"]').click();
    cy.get('[data-testid="user-username-0"]').should("have.text", "user");
  });

  it("should have delete a user button", () => {
    cy.get('[data-testid="adminpage"]').click();
    cy.get('[data-testid="delete-user-1"]').should("exist");
  });
});
