// useApi.js
import { useState, useEffect } from "react";

export const useApi = (endpoint, method = "GET", options = {}) => {
  // Base URL setup
  const URL = process.env.NEXT_PUBLIC_BACK_END_URL || "http://localhost";
  const PORT = process.env.NEXT_PUBLIC_BACK_END_PORT || "8080";

  const [data, setData] = useState(null); // State to store API response data
  const [loading, setLoading] = useState(false); // State to manage loading status
  const [error, setError] = useState(null); // State to store any error messages
  const [responseStatus, setResponseStatus] = useState(null); // State to store the HTTP response status

  // Function to fetch data from the API
  const fetchData = async (fetchOptions = {}) => {
    if (!endpoint) return;
    try {
      setLoading(true);
      const response = await fetch(`${URL}:${PORT}/${endpoint}`, {
        method, // HTTP method (GET, POST, etc.)
        headers: {
          "Content-Type": "application/json",
          ...fetchOptions.headers, // Use headers from fetchOptions if provided
        },
        body: method !== "GET" ? JSON.stringify(fetchOptions.body) : null, // Request body if method is not GET
      });

      setResponseStatus(response.status);

      if (!response.ok) {
        throw new Error(`Failed to fetch: ${response.statusText}`);
      }

      const result = await response.json();
      setData(result);
      setError(null);
    } catch (err) {
      setError(err.message);
      setData(null);
    } finally {
      setLoading(false);
    }
  };

  // Effect to automatically fetch data for GET requests
  useEffect(() => {
    if (method === "GET") {
      fetchData();
    }
  }, [endpoint, method]);

  return { data, loading, error, responseStatus, fetchData };
};
