
"use client";
import { useState } from "react";
import Link from "next/link";
import LoginLayout from "./layout";
import { useRouter } from "next/navigation";
import { doSignInUserWithEmailAndPassword } from "../firebase/auth";




export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const router = useRouter();


  const handleSubmit = async  (e) => {
    e.preventDefault();
    
    // Attempt loggin in user through Firebase
    try {
      // Firebase authentication
      const userCredential = await doSignInUserWithEmailAndPassword(
        email,
        password
      );
      console.log("User logged in!");

      // Get the user's token and uid after successful login
      const token = await userCredential.user.getIdToken();
      const userId = userCredential.user.uid; // Get the userId (uid)

      // Trigger the API request to verify the token

      // If verification is successful, save token and userId to local storage
      console.log("Session established:", { token, userId });
      localStorage.setItem("token", String(token));
      localStorage.setItem("uuid", userId);

      // Dynamically navigate to the user-specific dashboard using the userId
      router.push("../dashboard");
    } catch (error) {
      console.log("Login failed:", error);
    }
  };

  return (
    <LoginLayout>
      <form onSubmit={handleSubmit} className="space-y-4">
        <h2 className="text-2xl text-center">Login</h2>
        <div>
          <label className="block" htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="border rounded px-3 py-2 w-full"
          />
        </div>
        <div>
          <label className="block" htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="border rounded px-3 py-2 w-full"
          />
        </div>
        <button
          type="submit"
          className="bg-blue-500 text-white rounded px-4 py-2 w-full"
        >
          Login
        </button>
        <p className="text-center">
          Don't have an account? <Link href="/register">Register here</Link>
        </p>
      </form>
    </LoginLayout>
  );
}
