import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "../../api/axios";
import toast from "react-hot-toast";

const ResetPassword = () => {
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();
  const email = location.state?.email


  const handleSubmit = (e) => {
    e.preventDefault();
    if (!newPassword || !confirmPassword) {
      setError("Please fill in both fields.");
      return
    } else if (newPassword !== confirmPassword) {
      setError("Passwords do not match.");
      return
    } else if (newPassword.length < 8) {
      setError("Password must be at least 8 characters long.");
      return
    } 
      
      const resetPassword = async () => {
        try {
            const response = await axios.post(`/forgot-password/reset-password`,{password:newPassword,confirmPassword,email})
            toast.success("password reset successfully!")
            navigate("/registration");
        } catch (error) {
            toast.error(`an error occurred , ${error}`)
        }
      }
      resetPassword()
      
    
  };

  return (
    <div className="flex h-screen w-full items-center justify-center">
      <div className="w-[400px] p-6 shadow-lg border rounded-xl">
        <h1 className="text-2xl font-semibold text-center mb-4 text-[#0096C1]">
          Reset Your Password
        </h1>
        <p className="text-sm text-gray-600 text-center mb-6">
          Enter your new password and confirm it below.
        </p>

        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <label htmlFor="new-password" className="text-lg">
            New Password
          </label>
          <input
            type="password"
            id="new-password"
            className="textField"
            placeholder="Enter new password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
          />

          <label htmlFor="confirm-password" className="text-lg">
            Confirm Password
          </label>
          <input
            type="password"
            id="confirm-password"
            className="textField"
            placeholder="Confirm new password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />

          {error && <p className="text-red-500 text-sm">{error}</p>}

          <button
            type="submit"
            className="custom-button hover:scale-105 transition-all"
          >
            Reset Password
          </button>
        </form>
      </div>
    </div>
  );
};

export default ResetPassword;
