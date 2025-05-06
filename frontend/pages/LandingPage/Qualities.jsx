import React from "react";
import icon1 from "../../data/icon-1.svg";
import icon2 from "../../data/icon-2.svg";
import icon3 from "../../data/icon-3.svg";
import { motion } from "motion/react";

const Qualities = () => {
  return (
    <div
     className="min-h-[843px] py-10 overflow-y-auto w-full flex flex-col justify-center items-center bg-[#06625f]">
      <motion.div initial={{ transform: "translateY(-100px)",opacity:0 }}
            whileInView={{ transform: "translateY(0px)" ,opacity:1}}
            viewport={{ once: true, amount: 0.3 }}
            transition={{ type: "tween", duration: 1 }} className="flex justify-center items-center flex-col">
        <p className="text-white text-lg mb-10">
          WHY CHOOSE MENOUFIA UNIVERSITY
        </p>
        <h2 className="max-w-[932px] text-[40px] leading-[57px] text-white font-semibold mb-10 text-center">
          One of Egypt’s leading universities, fostering excellence in education
          and research
        </h2>
      </motion.div>

      <div className="flex flex-row justify-center items-center flex-wrap gap-16 ">
        <motion.div
        initial={{ transform: "translateX(-100px)",opacity:0 }}
        whileInView={{ transform: "translateX(0px)" ,opacity:1}}
        viewport={{ once: true, amount: 0.3 }}
        transition={{ type: "tween", duration: 1 }}
        className="flex flex-col gap-5 justify-center items-center w-[390px] border-x-[1px] border-y-4 border-gray-400 border-opacity-45 p-8 text-center min-h-[364px]">
          <img src={icon1} alt="icon1" />
          <h2 className="w-[275px] text-[26px] leading-[33px] text-white font-semibold">
            Accessible Quality Education
          </h2>
          <p className=" text-[16px] leading-[24px] text-white opacity-70 w-[344px] text-center">
            We are committed to providing high-quality education at an
            affordable cost, ensuring equal learning opportunities for students
            from all backgrounds.
          </p>
        </motion.div>

        <div className="flex flex-col gap-5 justify-center items-center w-[390px] border-x-[1px] border-y-4 border-gray-400 border-opacity-45 p-8 text-center min-h-[364px]">
          <img src={icon2} alt="icon2" />
          <h2 className="w-[275px] text-[26px] leading-[33px] text-white font-semibold">
            Advanced Academic Programs
          </h2>
          <p className="w-[344px] text-[16px] leading-[24px] text-white opacity-70 text-center">
            Our university continuously develops innovative and research-driven
            academic programs to enhance educational outcomes and meet global
            standards.
          </p>
        </div>

        <motion.div
         initial={{ transform: "translateX(+100px)",opacity:0 }}
         whileInView={{ transform: "translateX(0px)" ,opacity:1}}
         viewport={{ once: true, amount: 0.3 }}
         transition={{ type: "tween", duration: 1 }}
        className="flex flex-col gap-5 justify-center items-center w-[390px] border-x-[1px] border-y-4 border-gray-400 border-opacity-45 p-8 text-center min-h-[364px]">
          <img src={icon3} alt="icon3" />
          <h2 className="w-[275px] text-[26px] leading-[33px] text-white font-semibold">
            Vibrant Student Life
          </h2>
          <p className="w-[344px] text-[16px] leading-[24px] text-white opacity-70 text-center">
            We offer a dynamic campus environment with diverse student
            activities, cultural events, and extracurricular programs that
            enrich the university experience.
          </p>
        </motion.div>
      </div>
    </div>
  );
};

export default Qualities;
