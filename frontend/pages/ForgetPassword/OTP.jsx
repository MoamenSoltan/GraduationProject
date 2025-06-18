import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "../../api/axios";
import toast from "react-hot-toast";

const OTP = () => {
  const [otp, setOtp] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const location = useLocation()
  const email = location.state?.email

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!otp || otp.length !== 6) {
      setError("Please enter a valid 6-digit code.");
      return;
    }

    setError(null);
    console.log("Submitted OTP:", otp);
    const sendOTP = async ()=>{
        try {
            //TODO: check if backend expects an object of objects or array
            const response = await axios.post(`/forgot-password/verify-otp/`,{otp,email})
            navigate("/reset-password",{state:{email:email}})
        } catch (error) {
            toast.error(`an error occurred ${error.response.data.detail}`)
        }
    }
    sendOTP()

  };

  return (
    <div className="flex h-screen w-full items-center justify-center">
      <div className="w-[400px] p-6 shadow-lg border rounded-xl">
        <h1 className="text-2xl font-semibold text-center mb-4 text-[#0096C1]">
          Verify OTP
        </h1>
        <p className="text-sm text-gray-600 text-center mb-6">
          We've sent a 6-digit verification code to your email. Enter it below to continue.
        </p>

        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <label htmlFor="otp" className="text-lg">
            Enter OTP
          </label>
          <input
            type="text"
            id="otp"
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
            maxLength={6}
            className="textField"
            placeholder="e.g., 123456"
          />
          {error && <p className="text-red-500 text-sm">{error}</p>}

          <button
            type="submit"
            className="custom-button hover:scale-105 transition-all"
          >
            Verify Code
          </button>
        </form>
      </div>
    </div>
  );
};

export default OTP;
