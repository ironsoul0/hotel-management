import axios from "axios";

export const base = "http://localhost:8080";

export default axios.create({
  baseURL: `http://localhost:8080/api/`,
  headers: {
    Authorization: `Bearer ${localStorage.getItem("token")}`,
  },
});
