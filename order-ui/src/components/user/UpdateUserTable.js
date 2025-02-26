import React, { useState } from "react";
import PropTypes from "prop-types";
import { Button, Table, Modal, Form, Header, Icon } from "semantic-ui-react";

function UpdateUserTable({
  isLoading,
  user,
  handleUpdateUser,
  handleUserProfileInputChange,
  nickname,
  email,
  profilePicture,
}) {
  const [open, setOpen] = useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    handleUpdateUser(user);
    handleClose();
  };

  return (
    !isLoading && (
      <>
        <Header as="h2">
          <Icon name="user" />
          <Header.Content>User Profile</Header.Content>
        </Header>
        <Table compact striped selectable>
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell>Username</Table.HeaderCell>
              <Table.HeaderCell>Name</Table.HeaderCell>
              <Table.HeaderCell>Email</Table.HeaderCell>
              <Table.HeaderCell>Role</Table.HeaderCell>
              <Table.HeaderCell>Actions</Table.HeaderCell>
            </Table.Row>
          </Table.Header>
          <Table.Body>
            <Table.Row>
              <Table.Cell>{user.username}</Table.Cell>
              <Table.Cell data-testid="user-name">{user.name}</Table.Cell>
              <Table.Cell data-testid="user-email">{user.email}</Table.Cell>
              <Table.Cell>{user.role}</Table.Cell>
              <Table.Cell>
                <Button
                  data-testid="update-user-profile"
                  circular
                  color="blue"
                  size="small"
                  icon="edit"
                  onClick={handleOpen}
                />
              </Table.Cell>
            </Table.Row>
          </Table.Body>
        </Table>

        <Modal open={open} onClose={handleClose}>
          <Modal.Header>Update User</Modal.Header>
          <Modal.Content>
            <Form>
              <Form.Input
                label="Username"
                name="username"
                value={user.username}
                onChange={handleUserProfileInputChange}
                disabled
              />
              <Form.Input
                data-testid="update-user-nickname"
                label="nickname"
                name="nickname"
                value={nickname}
                onChange={handleUserProfileInputChange}
              />
              <Form.Input
                data-testid="update-user-email"
                label="email"
                name="email"
                value={email}
                onChange={handleUserProfileInputChange}
              />{" "}
              <Form.Input
                label="profilePicture"
                name="profilePicture"
                value={profilePicture}
                onChange={handleUserProfileInputChange}
              />
            </Form>
          </Modal.Content>
          <Modal.Actions>
            <Button
              data-testid="cancel-update-user-button"
              onClick={handleClose}
            >
              Cancel
            </Button>
            <Button
              color="blue"
              onClick={handleSubmit}
              data-testid="update-user-button"
            >
              Update
            </Button>
          </Modal.Actions>
        </Modal>
      </>
    )
  );
}
UpdateUserTable.propTypes = {
  isLoading: PropTypes.bool.isRequired,
  user: PropTypes.shape({
    username: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    email: PropTypes.string.isRequired,
    role: PropTypes.string.isRequired,
  }).isRequired,
  handleUpdateUser: PropTypes.func.isRequired,
  handleUserProfileInputChange: PropTypes.func.isRequired,
  nickname: PropTypes.string.isRequired,
  email: PropTypes.string.isRequired,
  profilePicture: PropTypes.string.isRequired,
};

export default UpdateUserTable;
