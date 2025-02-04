import React, { useEffect } from 'react';
import ProgressBar from '../../components/ProgressBar';
import { useNavigate } from 'react-router';
import { useStateContext } from '../../contexts/ContextProvider';

const Step2 = () => {
 
const navigate = useNavigate()
const {incrementMeter,decrementMeter}=useStateContext()
  return (
    <div>
      <div className="w-full mt-10 flex justify-center">
        <ProgressBar />
      </div>
      <button
        className="custom-button"
        onClick={() => {
          navigate("/registration/step3");
          incrementMeter();
        }}
      >
        Next Step
      </button>
      <button
        className="custom-button"
        onClick={() => {
          navigate("/registration/step1");
          decrementMeter();
        }}
      >
        previous Step
      </button>
    </div>
  );
};

export default Step2;
