import { useEffect, useState } from "react";
import { useStateContext } from "../contexts/ContextProvider";

import Hero from "../data/Hero.jpg";
import { useNavigate } from "react-router";
const Registration = () => {
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")

  const { Placeholder,setUser,user } = useStateContext();
  const navigate = useNavigate()

  const handleSubmit=(e)=>{
    e.preventDefault()
    setUser({...user, email, password })//spread former user and add current additions
    console.log(user);
    navigate("/registration/splash")
    

  }
 
  return (
    <div className=" flex w-full h-screen items-center justify-center gap-10 flex-row">
      <div className="w-[402px] h-[570px]">
        <h1 className="main-text mb-3">Welcome to {Placeholder}</h1>
        <p className="sub-text">All your educational needs in one place</p>
        <form onSubmit={handleSubmit} className="flex flex-col gap-2 mt-10">
          <label htmlFor="email" className="text-lg">E-mail</label>
          <input className="textField" type="text" placeholder="Enter Your E-mail" value={email} onChange={(e)=>{setEmail(e.target.value)}}/>

          <label htmlFor="password" className="text-lg">Password</label>
          <input className="textField" type="password" placeholder="Enter Your Password" value={password} onChange={(e)=>{setPassword(e.target.value)}}/>

          <button className="custom-button hover:scale-105 transition-all" type="submit">Sign in</button>

        </form>

      </div>

      <div className="">

        <img src={Hero} className="w-[402px] h-[570px] rounded-2xl" />
      </div>
    </div>
  );
};

export default Registration;
