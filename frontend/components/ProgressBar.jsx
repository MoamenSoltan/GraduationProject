import React from "react";
import { useStateContext } from "../contexts/ContextProvider";
import { motion } from "framer-motion";  // Make sure to use the correct framer-motion import

const ProgressBar = () => {
  const { meter } = useStateContext();

  return (
    <motion.div className="flex md:w-[50%] md:p-0 p-4 w-full gap-2">
      {[...Array(5)].map((_, index) => (
        <React.Fragment key={index}>
          {/* Progress Bar */}
          <motion.div
            className={`h-[6px] mt-[12px] w-[157px] rounded-full transition-colors duration-300 ${
              meter > index ? "bg-[#0096C1]" : "bg-[#F5F5F5]"
            }`}
            initial={{ width: 0 }}
            animate={{ width:  "100%" }} 
            transition={{ duration: 0.5 }}
          />
          
          {/* Circle */}
          <motion.span
            className={`rounded-full p-4 flex justify-center items-center w-5 h-5 ${
              meter > index ? "bg-[#0096C1] text-white" : "bg-[#F5F5F5]"
            }`}
            initial={{ scale: 0 }}
            animate={{ scale : 1 }}
            transition={{ duration: 0.7 }}
          >
            {index + 1}
          </motion.span>
        </React.Fragment>
      ))}
    </motion.div>
  );
};

export default ProgressBar;
