import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { Container } from "semantic-ui-react";
import OrderTable from "./OrderTable";
import UpdateUserTable from "./UpdateUserTable";
import { useAuth } from "../context/AuthContext";
import { orderApi } from "../misc/OrderApi";
import { handleLogError } from "../misc/Helpers";

function UserPage() {
  const Auth = useAuth();
  const user = Auth.getUser();
  const isUser = user.data.rol[0] === "USER";

  const [userMe, setUserMe] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [orderDescription, setOrderDescription] = useState("");
  const [nickname, setNickname] = useState("");
  const [email, setEmail] = useState("");
  const [profilePicture, setProfilePicture] = useState("");

  useEffect(() => {
    async function fetchData() {
      setIsLoading(true);

      try {
        const response = await orderApi.getUserMe(user);
        setUserMe(response.data);
        setNickname(response.data.name ?? "");
        setEmail(response.data.email ?? "");
        setProfilePicture(response.data.profilePicture ?? "");
      } catch (error) {
        handleLogError(error);
      } finally {
        setIsLoading(false);
      }
    }

    fetchData();
  }, []);

  const handleInputChange = (e, { name, value }) => {
    if (name === "orderDescription") {
      setOrderDescription(value);
    }
  };

  const handleUserProfileInputChange = (e, { name, value }) => {
    if (name === "nickname") {
      setNickname(value);
    }
    if (name === "email") {
      setEmail(value);
    }
    if (name === "profilePicture") {
      setProfilePicture(value);
    }
  };

  const handleUpdateUser = async () => {
    let trimmedNickname = nickname?.trim();
    let trimmedEmail = email?.trim();
    let trimmedProfilePicture = profilePicture?.trim();

    const updatedUserRequest = {
      nickname: trimmedNickname,
      email: trimmedEmail,
      profilePicture: trimmedProfilePicture,
    };

    try {
      await orderApi.updateUser(user, userMe.username, updatedUserRequest);
      fetchUserMeData(user);
    } catch (error) {
      handleLogError(error);
    }
  };

  const handleCreateOrder = async () => {
    let trimmedDescription = orderDescription.trim();
    if (!trimmedDescription) {
      return;
    }

    const order = { description: trimmedDescription };
    try {
      await orderApi.createOrder(user, order);
      await fetchUserMeData();
      setOrderDescription("");
    } catch (error) {
      handleLogError(error);
    }
  };

  const fetchUserMeData = async () => {
    setIsLoading(true);

    try {
      const response = await orderApi.getUserMe(user);
      setUserMe(response.data);
    } catch (error) {
      handleLogError(error);
    } finally {
      setIsLoading(false);
    }
  };

  if (!isUser) {
    return <Navigate to="/" />;
  }

  return (
    <Container>
      {userMe && (
        <UpdateUserTable
          user={userMe}
          nickname={nickname}
          email={email}
          profilePicture={profilePicture}
          handleUpdateUser={handleUpdateUser}
          handleUserProfileInputChange={handleUserProfileInputChange}
        />
      )}
      <OrderTable
        orders={userMe && userMe.orders}
        isLoading={isLoading}
        orderDescription={orderDescription}
        handleCreateOrder={handleCreateOrder}
        handleInputChange={handleInputChange}
      />
    </Container>
  );
}

export default UserPage;
