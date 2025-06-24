import React from "react";
import UniLogo from "../../data/uni_en1.svg";
import Hero from "./Hero";
import Message from "./Message";
import Qualities from "./Qualities";
import Departments from "./Departments";
import FunFacts from "./FunFacts";
import facebook from "../../data/facebook.svg";

import twitter from "../../data/twitter.svg";
import youtube from "../../data/youtube.svg";
import { useNavigate } from "react-router";


const LandingPage = () => {
  const navigate = useNavigate()
  return (
    <div>
     <nav className="w-full h-[50px] flex justify-between items-center px-6 sm:px-10 md:px-20 p-4 bg-[#06625f]">
  <p className="text-gray-300 text-sm sm:text-base">
    Welcome to <span className="text-white">Menofia University</span>
  </p>
  <p className="text-white text-sm sm:text-lg">edumate@gmail.com</p>
</nav>

<nav className="w-full h-auto flex flex-col sm:flex-row justify-between items-center px-6 sm:px-10 md:px-20 p-4 bg-white gap-4">
  <img src={UniLogo} alt="logo" className="w-[150px] sm:w-auto" />
  <div className="flex gap-3 sm:gap-5">
    <button onClick={()=>navigate('/registration')} className="px-5 sm:px-8 py-2 sm:py-3 text-sm sm:text-base text-black border-2 bg-gray-200 hover:border-gray-400">
      Login
    </button>
    <button onClick={()=>navigate("registration")} className="px-5 sm:px-8 py-2 sm:py-3 text-sm sm:text-base text-white border-[#06625f] bg-[#06625f] border-2 hover:border-[#04514f]">
      Signup
    </button>
  </div>
</nav>


      {/* hero */}
      <Hero/>

      {/* message */}
      
      <Message/>

      {/* qualities */}


      <Qualities/>

      {/* departments */}

      <Departments/>

      {/* fun facts */}

      <FunFacts/>

      <footer className="min-h-[532px] box flex flex-row  px-20 items-start justify-between py-20 border-t-2">
        <div className="flex flex-col max-w-[284px] justify-center  gap-5 overflow-hidden">
          <img src={UniLogo} className="w-40" alt="unilogo" />
          <p className="text-[#06625f] ">Menoufia University, founded in 1976, is a top Egyptian institution with 22 faculties, excelling in education, research, and community service.</p>

          <div className="flex flex-row gap-5">
          <a href="https://www.facebook.com/MenofiaUniv/" target="blank"><img src={facebook} alt="" /></a>
          <a href="https://x.com/en_uni" target="blank"><img src={twitter} alt="" /></a>
          <a href="https://www.youtube.com/channel/UCcoPxoor5XEnac34BwEI_9w" target="blank"><img src={youtube} alt="" /></a>

          
        </div>
        </div>

        <div className="flex flex-col gap-10">
          <h1 className="text-[#06625f] font-bold text-3xl">GET IN TOUCH</h1>

          <div className="flex flex-col">
            <p className="text-[#06625f] font-bold text-xl">+201006156131</p>
            <p className="text-[#06625f] font-bold text-lg">Phone number</p>
          </div>

          <div className="flex flex-col">
  <p className="text-[#06625f] font-bold text-xl">
    <a href="mailto:info@monufia.com">info@monufia.com</a>
  </p>
  <p className="text-[#06625f] font-bold text-lg">Email address</p>

  {/* Gmail fallback */}
  <p className="text-sm mt-2 text-[#06625f] underline">
    <a
      href="https://mail.google.com/mail/?view=cm&to=info@monufia.com"
      target="_blank"
      rel="noopener noreferrer"
    >
      Or click here to open Gmail
    </a>
  </p>
</div>

        </div>
      
      </footer>


    </div>
  );
};

export default LandingPage;
