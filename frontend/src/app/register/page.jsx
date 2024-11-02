"use client";
import { useState } from "react";
import Link from "next/link";
import RegisterLayout from "./layout"; // Adjust the path if necessary
import { useRouter } from "next/navigation";
import { doCreateUserWithEmailAndPassword } from "../firebase/auth"; // Import Firebase Auth function
import { auth } from "../firebase/auth"; // Adjust the path to your Firebase configuration

export default function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [alert, setAlert] = useState({ show: false, message: "", variant: "" });
  const router = useRouter();

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Attempt to sign up the user through Firebase
    try {
      // Firebase authentication
      const userCredential = await doCreateUserWithEmailAndPassword(email, password);
      console.log("User signed up!");

      // Get the user's token after successful signup
      const token = await userCredential.user.getIdToken();
      const userId = userCredential.user.uid; // Get the userId (uid)

      // You can trigger an API request here to verify or save user data if needed

      // Save token and userId to local storage
      console.log("Session established:", { token, userId });
      localStorage.setItem("token", String(token));
      localStorage.setItem("uuid", userId);

      // Navigate to the user-specific dashboard using the userId
      router.push("/dashboard");
    } catch (error) {
      setAlert({
        show: true,
        message: error.message,
        variant: "danger",
      });
      console.log("Sign up failed:", error);
    }
  };

  return (
    <RegisterLayout>
      <form onSubmit={handleSubmit} className="space-y-4">
        <h2 className="text-2xl text-center">Sign Up</h2>
        <div>
          <label className="block" htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
            className="border rounded px-3 py-2 w-full"
          />
        </div>
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
          Sign Up
        </button>
        <p className="text-center">
          Already have an account? <Link href="/login">Login here</Link>
        </p>
        {alert.show && (
          <div className={`alert alert-${alert.variant}`}>
            {alert.message}
          </div>
        )}
      </form>
    </RegisterLayout>
  );
}
