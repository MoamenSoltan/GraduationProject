import React, { useState } from "react";
import { useNavigate } from "react-router";
import { motion } from "motion/react";
import Hero from "../../data/Hero.jpg";
import { useStateContext } from "../../contexts/ContextProvider";
import axios from "../../api/axios";
import toast from "react-hot-toast";

const Email = () => {
  const { Placeholder } = useStateContext();
  const [email, setEmail] = useState("");
  const [error, setError] = useState(null);
  const [loading, setloading] = useState(false);
  const navigate = useNavigate();

  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!email) {
      setError("Email is required.");
      return;
    } else if (!emailRegex.test(email)) {
      setError("Please enter a valid email.");
      return;
    }
    const sendEmail = async () => {
      try {
        if (loading) return;
        setloading(true);

        const response = await axios.post(
          `/forgot-password/verify-email/`,
          {email}
        );
        navigate("/OTP", { state: { email: email } });
      } catch (error) {
        toast.error(`an error occurred , ${error.response.data.detail}`);
      } finally {
        setloading(false);
      }
    };
    sendEmail();
  };

  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.9 }}
      animate={{ opacity: 1, scale: 1 }}
      exit={{ opacity: 0, scale: 0.9 }}
      transition={{ duration: 1 }}
      className="flex w-full h-screen items-center justify-center gap-10 flex-row"
    >
      <div className="w-[402px] p-4 md:p-0 h-[570px]">
        <h1 className="main-text mb-3">Reset Password</h1>
        <p className="sub-text">
          Follow these steps to reset your password:
          <br />
          <strong className="text-[#0096C1]">
            After entering your email, you'll receive a message with a
            verification code.
          </strong>
        </p>

        <form
          onSubmit={(e) => handleSubmit(e)}
          className="flex flex-col gap-2 mt-10"
        >
          <label htmlFor="email" className="text-lg">
            Enter Your E-mail
          </label>
          <input
            className="textField"
            type="text"
            placeholder="example@email.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          {error && <p className="error-text">{error}</p>}

          <button
            className="custom-button hover:scale-105 transition-all"
            type="submit"
          >
            {loading ? "Loading..." : "Continue"}
          </button>
        </form>
      </div>

      <div className="">
        <img src={Hero} className="md:w-[402px] md:h-[570px] w-0 rounded-2xl" />
      </div>
    </motion.div>
  );
};

export default Email;
