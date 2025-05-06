import React from "react";
import funFact from "../../data/fun fact.svg";
const FunFacts = () => {
  return (
    <div className="min-h-[742px] w-full items-center justify-center flex flex-col  p-10 gap-10 text-center">
      <img src={funFact} alt="fun fact" />
      <p className="text-[#06625f] font-bold  border-b-[1px] border-[#06625f] w-fit">
        FUN FACTS ABOUT MENOUFIA UNIVERSITY
      </p>

      <p className="text-[48px] max-w-[870px] font-semibold">
        Education that empowers future{" "}
        <span className="text-[#06625f]">leaders</span> to innovate, excel, and
        contribute to <span className="text-[#06625f]">society</span>.
      </p>

      {/* facts */}

      <div className="flex flex-row flex-wrap md:flex-nowrap">
        <div className="flex flex-col text-center w-full  min-w-[300px] items-center">
          <p className="text-[#06625f]">Student Population</p>
          <p className="text-[64px] font-bold tracking-[-1.9px] text-white text-stroke">
            250,000+
          </p>
        </div>

        <div className="flex flex-col px-10 text-center w-full  min-w-[300px] items-center">
          <p className="text-[#06625f]">International Collaborations</p>
          <p className="text-[64px] font-bold tracking-[-1.9px] text-white text-stroke">
            30+
          </p>
        </div>

        <div className="flex flex-col px-10 text-center w-full min-w-[300px] items-center">
          <p className="text-[#06625f]">Faculties & Institutes</p>
          <p className="text-[64px] font-bold tracking-[-1.9px] text-white text-stroke">
            22
          </p>
        </div>
      </div>
    </div>
  );
};

export default FunFacts;
