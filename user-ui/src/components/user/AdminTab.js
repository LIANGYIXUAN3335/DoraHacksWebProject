import React from "react";
import PropTypes from "prop-types";
import { Tab } from "semantic-ui-react";
import UserTable from "./UserTable";

function AdminTab(props) {
  const { handleInputChange } = props;
  const {
    isUsersLoading,
    users,
    userUsernameSearch,
    handleDeleteUser,
    handleSearchUser,
  } = props;

  const panes = [
    {
      menuItem: { key: "users", icon: "users", content: "Users" },
      render: () => (
        <Tab.Pane loading={isUsersLoading}>
          <UserTable
            users={users}
            userUsernameSearch={userUsernameSearch}
            handleInputChange={handleInputChange}
            handleDeleteUser={handleDeleteUser}
            handleSearchUser={handleSearchUser}
          />
        </Tab.Pane>
      ),
    },
  ];

  return <Tab menu={{ attached: "top" }} panes={panes} />;
}
AdminTab.propTypes = {
  handleInputChange: PropTypes.func.isRequired,
  isUsersLoading: PropTypes.bool.isRequired,
  users: PropTypes.array.isRequired,
  userUsernameSearch: PropTypes.string.isRequired,
  handleDeleteUser: PropTypes.func.isRequired,
  handleSearchUser: PropTypes.func.isRequired,
};

export default AdminTab;
