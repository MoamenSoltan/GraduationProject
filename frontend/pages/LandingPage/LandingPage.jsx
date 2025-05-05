import React from "react";
import UniLogo from "../../data/uni_en1.svg";
import figure from "../../data/BG Shape.svg";
import { i } from "motion/react-client";

import frame from "../../data/Frame 1000007003.svg";
import person from "../../data/image-1742414625841 1.svg";
import { motion } from "motion/react";
import aboutImage from "../../data/About Image.png";
import container from "../../data/Container.png";
import profile from "../../data/profile.png";
import signature from "../../data/Signature.svg";
const LandingPage = () => {
  return (
    <div>
     <nav className="w-full h-[50px] flex justify-between items-center px-6 sm:px-10 md:px-20 p-4 bg-[#06625f]">
  <p className="text-gray-300 text-sm sm:text-base">
    Welcome to <span className="text-white">Menofia University</span>
  </p>
  <p className="text-white text-sm sm:text-lg">contactInfo@gmail.com</p>
</nav>

<nav className="w-full h-auto flex flex-col sm:flex-row justify-between items-center px-6 sm:px-10 md:px-20 p-4 bg-white gap-4">
  <img src={UniLogo} alt="logo" className="w-[150px] sm:w-auto" />
  <div className="flex gap-3 sm:gap-5">
    <button className="px-5 sm:px-8 py-2 sm:py-3 text-sm sm:text-base text-black border-2 bg-gray-200 hover:border-gray-400">
      Login
    </button>
    <button className="px-5 sm:px-8 py-2 sm:py-3 text-sm sm:text-base text-white border-[#06625f] bg-[#06625f] border-2 hover:border-[#04514f]">
      Signup
    </button>
  </div>
</nav>


      {/* hero */}
      <div className="w-full h-auto lg:h-[1004px] relative bg-[#e8f1f1] flex flex-col lg:flex-row justify-between px-6 sm:px-10 md:px-20 py-12 items-center overflow-hidden">
        {/* left side */}
        <div>
          {/* Background Image */}
          <motion.img
            initial={{ transform: "translateX(-1000px)" }}
            animate={{ transform: "translateX(0px)" }}
            transition={{ type: "tween", duration: 1 }}
            src={figure}
            alt="figure"
            className="absolute left-0 top-0 z-0 w-[400px] h-full"
          />

          {/* Text content */}
          <motion.div
            initial={{ transform: "translateX(-1000px)" }}
            animate={{ transform: "translateX(0px)" }}
            transition={{ type: "tween", duration: 1 }}
            className="relative z-10"
          >
            <p className="text-[#06625f] text-sm sm:text-base mb-8 font-bold">
              MEET WITH #13 UNIVERSITY IN EGYPT
            </p>

            <p className="text-[32px] sm:text-[48px] lg:text-[64px] font-semibold leading-tight text-black max-w-[650px]">
              A top-ranked educational institution in{" "}
              <span className="text-[#06625f]">Egypt</span> and{" "}
              <span className="text-[#06625f]">Africa</span>
            </p>
          </motion.div>
        </div>

        {/* right side */}
        <div className=" h-[1004px] hidden lg:flex items-end w-[40%]  min-w-[500px]">
          {/* Container to stack images */}
          <div className="absolute w-[550px] h-auto">
            {/* Frame image */}
            <motion.img
              initial={{ transform: "translateX(+1000px)" }}
              animate={{ transform: "translateX(0px)" }}
              transition={{ type: "tween", duration: 1 }}
              src={frame}
              alt="frame"
              className=" z-0 relative bottom-[200px] w-full h-auto object-contain"
            />

            {/* Person image over the frame */}
            <motion.img
              initial={{ transform: "translateY(+1000px)" }}
              animate={{ transform: "translateY(0px)" }}
              transition={{ type: "tween", duration: 1 }}
              src={person}
              alt="person"
              className="absolute bottom-0  z-10 w-full h-auto object-contain"
            />
          </div>
        </div>
      </div>

      {/* message */}
      <motion.div
        initial={{ opacity: 0 }}
        whileInView={{ opacity: 1 }} // When in view, it becomes visible and moves to its position
        viewport={{ once: true, amount: 0.7 }} // Trigger animation once when it enters the viewport
        transition={{ type: "tween", duration: 1 }} // Transition effect duration and type
      >
        <div className="w-full bg-white h-[840px] py-[120px] px-[70px] flex flex-row justify-center">
          {/* images */}
          <div className="relative hidden xl:inline min-w-[40%]">
            {/* include images in divs to ensure proper stacking */}
            <div>
              <motion.img
                src={aboutImage}
                alt="About"
                initial={{ transform: "translateX(-100px)" }}
                whileInView={{ transform: "translateX(0px)" }}
                viewport={{ once: true, amount: 1 }}
                transition={{ type: "tween", duration: 1 }}
              />
            </div>
            <div>
              <motion.img
                src={container}
                className="z-10 absolute top-[311px] left-[250px]"
                alt="container"
                initial={{ transform: "translateY(+100px)" }}
                whileInView={{ transform: "translateY(0px)" }}
                viewport={{ once: true, amount: 0.4 }}
                transition={{ type: "tween", duration: 1 }}
              />
            </div>
          </div>

          {/* message paragraph */}
          <div className="w-[494px] z-50 bg-white h-[70vh]">
            {/* animate from right */}
            <motion.div initial={{ transform: "translateX(+200px)" }}
                whileInView={{ transform: "translateX(0px)" }}
                viewport={{ once: true, amount: 0.3 }}
                transition={{ type: "tween", duration: 1 }}>
              <span className="text-[#06625F] border-b-2 border-[#06625F] font-semibold text-lg">
                SINCE 1976
              </span>
              <h1 className="mt-5 text-[48px] leading-[57px] font-semibold mb-5">
                Message from the University{" "}
                <span className="text-[#06625F]">President</span>
              </h1>
            </motion.div>

            {/* animate from right */}
            <motion.div initial={{ transform: "translateY(+200px)" }}
                whileInView={{ transform: "translateY(0px)" }}
                viewport={{ once: true, amount: 0.3 }}
                transition={{ type: "tween", duration: 1 }}>
              <p className="text-gray-600 leading-[25px] text-[16px] mb-5">
                For nearly 50 years, Menoufia University has been a beacon of
                academic excellence and innovation in Egypt, focusing on
                fostering knowledge, critical thinking, and research, while
                empowering thousands of students to contribute to society and
                global progress.
              </p>
              <p className="border-l-4 p-4 border-[#06625F] ">
                Since its establishment in 1976, Menoufia University has grown
                into one of the most reputable educational institutions in Egypt
                and Africa.
              </p>
              <div className="flex p-4 justify-between">
                <div className="flex flex-row gap-2 justify-between w-[280px]">
                  <img src={profile} alt="" className="w-10 h-10 sm:w-auto sm:h-auto" />
                  <div>
                    <p className="text-[#06625F]">
                      President, Menoufia University
                    </p>
                    <p className="font-bold">Dr. Ahmed El-Kased</p>
                  </div>
                </div>
                <div>
                  <img src={signature} alt="" />
                </div>
              </div>
            </motion.div>
          </div>
        </div>
      </motion.div>
    </div>
  );
};

export default LandingPage;
