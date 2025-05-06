import React from 'react'
import { motion } from "motion/react";
import figure from "../../data/BG Shape.svg";
import frame from "../../data/Frame 1000007003.svg";
import person from "../../data/image-1742414625841 1.svg";

const Hero = () => {
  return (
    <div className='w-full h-auto lg:h-[1004px] relative bg-[#e8f1f1] flex flex-col lg:flex-row justify-between px-6 sm:px-10 md:px-20 py-12 items-center overflow-hidden'>
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
  )
}

export default Hero