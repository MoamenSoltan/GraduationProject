import React from "react";
import { useStateContext } from "../contexts/ContextProvider";

const ProgressBar = () => {
  const { meter } = useStateContext();

  return (
    <div className="flex w-[50%] gap-1">
      {[...Array(5)].map((_, index) => (
        <>
        
         
          <div
            key={index}
            className={`h-[6px] mt-[12px] w-[157px] rounded-full transition-colors duration-300 ${
              meter > index ? "bg-[#0096C1]" : "bg-[#F5F5F5]"
            }`}
          ></div>
           <span className={`rounded-full p-4  flex justify-center items-center w-5 h-5 ${
              meter > index ? "bg-[#0096C1] text-white" : "bg-[#F5F5F5]"
            }`}>
            {index+1}
            </span>
        </>
      ))}
    </div>
  );
};

export default ProgressBar;
