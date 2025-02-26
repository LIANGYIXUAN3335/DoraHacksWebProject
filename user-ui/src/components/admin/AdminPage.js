import React, { useEffect, useState } from "react";
import { Container } from "semantic-ui-react";
import { useAuth } from "../context/AuthContext";
import AdminTab from "./AdminTab";
import { userApi } from "../misc/UserApi";
import { handleLogError } from "../misc/Helpers";

function AdminPage() {
  const Auth = useAuth();
  const user = Auth.getUser();

  const [users, setUsers] = useState([]);
  const [userUsernameSearch, setUserUsernameSearch] = useState("");
  const [isUsersLoading, setIsUsersLoading] = useState(false);

  useEffect(() => {
    handleGetUsers();
  }, []);

  const handleInputChange = (e, { name, value }) => {
    if (name === "userUsernameSearch") {
      setUserUsernameSearch(value);
    }
  };

  const handleGetUsers = async () => {
    setIsUsersLoading(true);
    try {
      const response = await userApi.getUsers(user);
      setUsers(response.data);
    } catch (error) {
      handleLogError(error);
    } finally {
      setIsUsersLoading(false);
    }
  };

  const handleDeleteUser = async (username) => {
    try {
      await userApi.deleteUser(user, username);
      handleGetUsers();
    } catch (error) {
      handleLogError(error);
    }
  };

  const handleSearchUser = async () => {
    const username = userUsernameSearch;
    try {
      const response = await userApi.getUsers(user, username);
      const data = response.data;
      const users = data instanceof Array ? data : [data];
      setUsers(users);
    } catch (error) {
      handleLogError(error);
      setUsers([]);
    }
  };

  return (
    <Container>
      <AdminTab
        isUsersLoading={isUsersLoading}
        users={users}
        userUsernameSearch={userUsernameSearch}
        handleDeleteUser={handleDeleteUser}
        handleSearchUser={handleSearchUser}
        handleInputChange={handleInputChange}
      />
    </Container>
  );
}

export default AdminPage;
