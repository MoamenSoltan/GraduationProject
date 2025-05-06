import React from 'react'
import { motion } from "motion/react";
import aboutImage from "../../data/About Image.png";
import container from "../../data/Container.png";
import profile from "../../data/profile.png";
import signature from "../../data/Signature.svg";
const Message = () => {
  return (
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
  )
}

export default Message