import { useEffect, useState } from "react";
import { useStateContext } from "../contexts/ContextProvider";
import { motion } from "motion/react";

import Hero from "../data/Hero.jpg";
import { useNavigate } from "react-router";
import axios from "../api/axios";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { PulseLoader } from "react-spinners";
const Registration = () => {
  // const [email, setEmail] = useState("")
  // const [password, setPassword] = useState("")
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const axiosPrivate = useAxiosPrivate();

  const { Placeholder, setUser, user, auth, setAuth } = useStateContext();
  const navigate = useNavigate();
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;

  const validate = () => {
    if (!user.email || !user.password) {
      setError("All fields must be provided");
      return false;
    } else if (!emailRegex.test(user.email)) {
      setError("Enter a valid email");
      return false;
    } else if (!passwordRegex.test(user.password)) {
      setError(
        "Enter a valid password, atleast 8 characters containing numbers , Uppercase letters, and lowercase letters"
      );
      return false;
    }
    setError(null);
    return true;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // const valid = validate()
    // if(!valid) return;
    console.log(user);

    const login = async () => {
      try {
        if (loading) return;
        setLoading(true);
        // use normal axios instead of private because we dont need to attch jwt
        const response = await axios.post("/auth/login", {
          email: user.email,
          password: user.password,
        });

        const accessToken = response.data.token;
        const message = response.data.message;

        //TODO: replace with user with real data
        setAuth({ user: { role: "student" }, accessToken, message });

        console.log(auth);

        navigate("/studentDashboard");

        // navigate(`/${response?.data?.user?.role}Dashboard`);
        // TODO: replace with real data
      } catch (error) {
        if (!error.response) {
          console.error("Network error or server is down:", error);
          setError("Network error, please try again.");
        } else if (
          error.response.status === 403 ||
          error.response.status === 401
        ) {
          console.warn("User not found, redirecting to registration...");
          navigate("/registration/splash");
        } else if (error.response.status === 400) {
          console.error("Bad request:", error);
          setError("Invalid request, please check your input.");
        } else {
          console.error("Unexpected error:", error);
          setError("Something went wrong. Please try again later.");
        }

        console.log("unknown error", error, user.email, user.password);
      } finally {
        setLoading(false);
      }
    };
    login();
  };
  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.9 }}
      animate={{ opacity: 1, scale: 1 }}
      exit={{ opacity: 0, scale: 0.9 }}
      transition={{ duration: 1 }}
      className=" flex w-full h-screen items-center justify-center gap-10 flex-row"
    >
      <div className="w-[402px] p-4 md:p-0 h-[570px]">
        <h1 className="main-text mb-3">Welcome to {Placeholder}</h1>
        <p className="sub-text">All your educational needs in one place</p>
        <form onSubmit={handleSubmit} className="flex flex-col gap-2 mt-10">
          <label htmlFor="email" className="text-lg">
            E-mail
          </label>
          <input
            className="textField"
            type="text"
            placeholder="Enter Your E-mail"
            value={user.email}
            onChange={(e) => {
              setUser({ ...user, email: e.target.value });
            }}
          />

          <label htmlFor="password" className="text-lg">
            Password
          </label>
          <input
            className="textField"
            type="password"
            placeholder="Enter Your Password"
            value={user.password}
            onChange={(e) => {
              setUser({ ...user, password: e.target.value });
            }}
          />
          {error && <p className="error-text">{error}</p>}

          <button
            className="custom-button hover:scale-105 transition-all"
            type="submit"
          >
            {loading ? (
              <PulseLoader color="#ffffff" margin={3} size={10} />
            ) : (
              "sign up"
            )}
          </button>
        </form>
      </div>

      <div className="">
        <img src={Hero} className="md:w-[402px] md:h-[570px] w-0 rounded-2xl" />
      </div>
    </motion.div>
  );
};

export default Registration;
/**
 * validation steps :
 * 1- error state
 * 2- conditional rendering for error state
 * 3- regex
 * 4- function that tests regex validity
 * 5- in submit apply function
 */
