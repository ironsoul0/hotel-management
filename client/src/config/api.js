import axios from "axios";

export const base =
  process.env.NODE_ENV === "development"
    ? "http://localhost:8080"
    : "https://hms-backend.ironsoul.me";

export default axios.create({
  baseURL: `${base}/api/`,
  headers: {
    Authorization: `Bearer ${localStorage.getItem("token")}`,
  },
});
