const { defineConfig } = require("cypress");

module.exports = defineConfig({
  projectId: '6as93o',
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});
