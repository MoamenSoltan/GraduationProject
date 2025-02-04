import React, { useEffect } from "react";
import ProgressBar from "../../components/ProgressBar";
import { useNavigate } from "react-router-dom";
import { useStateContext } from "../../contexts/ContextProvider";

const Step1 = () => {
  const navigate = useNavigate();
  const { incrementMeter } = useStateContext();



  return (
    <div>
      <div className="w-full mt-10 flex justify-center">
        <ProgressBar />
      </div>
      <button
        className="custom-button"
        onClick={() => {
          navigate("/registration/step2");
          incrementMeter();
        }}
      >
        Next Step
      </button>
    </div>
  );
};

export default Step1;
