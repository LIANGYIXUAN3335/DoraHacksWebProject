import React, { useState } from "react";
import { Button, Table, Modal, Form, Header, Icon } from "semantic-ui-react";

function UpdateUserTable({
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
            <Table.Cell>{user.name}</Table.Cell>
            <Table.Cell>{user.email}</Table.Cell>
            <Table.Cell>{user.role}</Table.Cell>
            <Table.Cell>
              <Button
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
              label="nickname"
              name="nickname"
              value={nickname}
              onChange={handleUserProfileInputChange}
            />
            <Form.Input
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
          <Button onClick={handleClose}>Cancel</Button>
          <Button color="blue" onClick={handleSubmit}>
            Update
          </Button>
        </Modal.Actions>
      </Modal>
    </>
  );
}

export default UpdateUserTable;
