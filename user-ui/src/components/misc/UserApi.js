import axios from "axios";
import { config } from "../../Constants";
import { parseJwt } from "./Helpers";

export const userApi = {
  authenticate,
  signup,
  numberOfUsers,
  getUsers,
  deleteUser,
  getUserMe,
  updateUser,
};

function authenticate(username, password) {
  return instance.post(
    "/auth/authenticate",
    { username, password },
    {
      headers: { "Content-type": "application/json" },
    }
  );
}

function signup(user) {
  return instance.post("/auth/signup", user, {
    headers: { "Content-type": "application/json" },
  });
}

function numberOfUsers() {
  return instance.get("/public/numberOfUsers");
}

function getUsers(user, username) {
  const url = username ? `/api/users/${username}` : "/api/users";
  return instance.get(url, {
    headers: { Authorization: bearerAuth(user) },
  });
}

function deleteUser(user, username) {
  return instance.delete(`/api/users/${username}`, {
    headers: { Authorization: bearerAuth(user) },
  });
}

function getUserMe(user) {
  return instance.get("/api/users/me", {
    headers: { Authorization: bearerAuth(user) },
  });
}

function updateUser(user, username, updateUserRequest) {
  return instance.put(`/api/users/${username}`, updateUserRequest, {
    headers: {
      Authorization: bearerAuth(user),
      "Content-Type": "application/json",
    },
  });
}

// -- Axios

const instance = axios.create({
  baseURL: config.url.API_BASE_URL,
});

instance.interceptors.request.use(
  function (config) {
    // If token is expired, redirect user to login
    if (config.headers.Authorization) {
      const token = config.headers.Authorization.split(" ")[1];
      const data = parseJwt(token);
      if (Date.now() > data.exp * 1000) {
        window.location.href = "/login";
      }
    }
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

// -- Helper functions

function bearerAuth(user) {
  return `Bearer ${user.accessToken}`;
}
