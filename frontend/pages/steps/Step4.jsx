import React, { useState } from "react";
import ProgressBar from "../../components/ProgressBar";
import { motion } from "motion/react";
import { useStateContext } from "../../contexts/ContextProvider";
import { useNavigate } from "react-router";

const Step4 = () => {
  const fileRegex = /^.*\.(jpg|jpeg|png)$/i;
  const { user, setUser, incrementMeter, decrementMeter } = useStateContext();

  const navigate = useNavigate();

  const [error, setError] = useState("");

  const validate = () => {
    if (
      !user.IDNumber ||
      !user.IDPhoto ||
      !user.personalPhoto
    ) {
      setError("All fields are required");
      return false;
    }
    if ( !fileRegex.test(user.IDPhoto.name)|| !fileRegex.test(user.personalPhoto.name)) {
      setError("only png , jpeg and jpg files are allowed");
      return false;
    }

    return true;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validate()) {
      return;
    }
    incrementMeter();
    navigate("/registration/step5");
  };
  const handlePrevious = () => {
    decrementMeter();
    navigate("/registration/step3");
  };
  return (
    <motion.div
      initial={{ x: "-5%" }}
      animate={{ x: 0 }}
      exit={{ x: "-5%" }}
      transition={{ duration: 0.5 }}
    >
      <div className="w-full mt-10 flex justify-center">
        <ProgressBar />
      </div>

      <form className="md:w-[50%]  md:p-0 w-full p-4 m-auto" onSubmit={handleSubmit}>
        <h1 className="mt-[50px] main-text"> Required Document Upload</h1>
        <p className="sub-text mb-10">
          in this section , you can upload all essential documents
        </p>

        <div className="w-full grid  gap-5">
          <div className="flex flex-col w-full mt-5">
            <label htmlFor="ID" className="text-lg">
              ID Number
            </label>
            <input
              type="text"
              className="flex-1 textField "
              value={user.IDNumber}
              onChange={(e) => {
                setUser({ ...user, IDNumber: e.target.value });
              }}
            />

            <div className="flex flex-col  w-full mt-5">
              <label htmlFor="file" className="text-lg">
                ID Photo
              </label>
              <input
                type="file"
                name="file"
                className="textField file:mr-4 file:rounded-full file:border-0 file:bg-[] file:px-4 file:py-2 file:text-sm file:font-semibold file:text-black hover:file:text-white hover:file:scale-105 hover:file:transition-all hover:file:cursor-pointer hover:file:bg-[#0096C1]"
                onChange={(e) => {
                  const file = e.target.files[0];
                  if (file) {
                    setUser({ ...user, IDPhoto: file });
                  }
                }}
              />

              <label htmlFor="file2" className="text-lg mt-5">
                Personal Photo 
              </label>
              <input
                type="file"
                name="file2"
                className="textField file:mr-4 file:rounded-full file:border-0 file:bg-[] file:px-4 file:py-2 file:text-sm file:font-semibold file:text-black hover:file:text-white hover:file:scale-105 hover:file:transition-all hover:file:cursor-pointer hover:file:bg-[#0096C1]"
                onChange={(e) => {
                  const file = e.target.files[0];
                  if (file) {
                    setUser({ ...user, personalPhoto: file });
                  }
                }}
              />
            </div>
          </div>
        </div>
        {error && <p className="error-text mt-5">{error}</p>}
        <div className="w-[100%] flex gap-10 items-center justify-center p-4">
          <button type="submit" className="custom-button w-full flex-1">
            Next
          </button>
          <button
            onClick={handlePrevious}
            className="  secondary-button w-full flex-1"
          >
            Previous
          </button>
        </div>
      </form>
    </motion.div>
  );
};

export default Step4;
