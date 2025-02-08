import React, { useEffect, useState } from "react";
import ProgressBar from "../../components/ProgressBar";
import { useNavigate } from "react-router-dom";
import { useStateContext } from "../../contexts/ContextProvider";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import dayjs from "dayjs"; // Required for date formatting (because we have it as a string )

import { motion } from "motion/react";
/**
 * 
 * date formatting with MUI 
 * the value needs to be in a specific format to be shown or itll crash
 * onchange , we can use the raw format , but we need to convert it for better api handling for backend
 */

const Step1 = () => {
  const navigate = useNavigate();
  const { incrementMeter,decrementMeter,setUser,user } = useStateContext();

  // const [firstForm, setFirstForm] = useState({
  //   firstName: "",
  //   lastName: "",
  //   dateOfBirth: "", // Ensure it's initially empty
  //   gender: "",
  //   phoneNumber: "+20",
  // }); // not needing a local state because we need to save the data when navigating between steps , therefore use context api state instead

  const [error, setError] = useState(null)
  const phoneRegex = /^\+20\d{10}$/;
   const validate = ()=>{
    if(!user.firstName||!user.lastName||!user.phoneNumber||!user.gender ||!user.gender)
    {
      setError("All fields are required");
      return false;
    }
    if(!phoneRegex.test(user.phoneNumber)) {
      setError("Invalid Phone Number");
      return false;
    }
    setError(null);

    return true;
   }

  const handleTelChange = (e) => {
    let value = e.target.value;

    // Remove non-numeric characters except for the "+20" prefix
    value = value.replace(/[^0-9]/g, "");

    // Ensure the prefix "+20" is always present
    if (!value.startsWith("20")) {
      value = "20";
    }

    setUser({...user,phoneNumber:"+" + value});
  };

  const handleSubmit = (e)=>{
    e.preventDefault()
    if(!validate()){
      return;
    }
    
    incrementMeter()
    navigate("/registration/step2")
    
    
  }
  const handlePrevious = (e)=>{
    e.preventDefault()
    decrementMeter()
    navigate("/registration")
  }

  useEffect(() => {
    console.log(user.dateOfBirth);
  }, [user.dateOfBirth]); // Fix dependency array (should be an array)

  return (
    <motion.div  
    initial={{ x:"-5%", }}
    animate={{ x:0,  }}
    exit={{ x:"-5%",  }}
    transition={{ duration: 0.5 }}
    className="">
      <div className="w-full mt-10 flex justify-center">
        <ProgressBar />
      </div>

      <div className="md:ml-[25%] mx-auto w-[80%]  mt-[50px]">
        <h1 className="main-text">Account Information</h1>
        <p className="sub-text">
          In this section, you can provide personal information for your
          profile.
        </p>
      </div>

      <form onSubmit={handleSubmit} className="flex flex-col md:ml-[25%] md:w-[50%] mx-auto w-[80%] gap-2 mt-10">
        <div className="flex flex-row gap-10 w-full">
          <div className="w-[40%]">
            <label htmlFor="firstName" className="text-lg">
              First Name
            </label>
            <input
              className="textField w-full"
              type="text"
              placeholder="Enter Your First Name"
              value={user.firstName}
              onChange={(e) =>
                setUser({ ...user, firstName: e.target.value })
              }
            />
          </div>

          <div className="w-[40%]">
            <label htmlFor="lastName" className="text-lg">
              Last name
            </label>
            <input
              className="textField w-full"
              type="text"
              placeholder="Enter Your Last Name"
              value={user.lastName}
              onChange={(e) =>
                setUser({ ...user, lastName: e.target.value })
              }
            />
          </div>
        </div>

        <div className="w-full flex flex-row gap-10 items-center">
          <div className="flex flex-col w-[40%]">
            <label htmlFor="dateOfBirth" className="text-lg">
              Date of Birth
            </label>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                value={
                  user.dateOfBirth ? dayjs(user.dateOfBirth) : null
                } // Convert string to dayjs for display
                onChange={(newValue) =>
                  setUser({
                    ...user,
                    dateOfBirth: newValue ? newValue.format("YYYY-MM-DD") : "", // Ensure safe formatting
                  })
                }
                format="YYYY-MM-DD"
              />
            </LocalizationProvider>
          </div>

          <div className="flex flex-col w-[40%]">
            <label htmlFor="gender">Gender</label>
            <select className="textField"
              name="gender"
              id="gender"
              value={user.gender}
              onChange={(e) => {
                setUser({ ...user, gender: e.target.value });
              }}
            >
              <option value="">Select Gender</option>
              <option value="male">Male</option>
              <option value="female">Female</option>
            </select>
          </div>
        </div>

        <div className="flex flex-col w-[40%]">
          <label htmlFor="phoneNumber" className="text-lg">
            Phone Number
          </label>
          <input
            className="textField w-full"
            type="tel"
            placeholder="Enter Your Phone Number"
            value={user.phoneNumber}
            onChange={handleTelChange}
          />
        </div>
        {error && <p className="error-text">{error}</p>}

        <div className="w-[100%] flex gap-10 items-center justify-center p-4">
          <button  type="submit" className="custom-button w-full flex-1">
            Next
          </button>
          <button onClick={handlePrevious} className="  secondary-button w-full flex-1">Previous</button>
        </div>
      </form>
    </motion.div>
  );
};

export default Step1;
