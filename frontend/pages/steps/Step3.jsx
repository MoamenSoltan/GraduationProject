import React, { useEffect, useState } from "react";
import ProgressBar from "../../components/ProgressBar";
import { useStateContext } from "../../contexts/ContextProvider";
import { motion } from "motion/react";
import { useNavigate } from "react-router";

const Step3 = () => {
  const { user, setUser, incrementMeter, decrementMeter } = useStateContext();
  useEffect(() => {
    console.log(user);
  }, []);
  const handleFile = (e) => {
    const file = e.target.files[0]
    if (file)
    {
      setUser({...user, highSchoolCertificate: e.target.files[0] });
      console.log(file);
      
    }
  };

  const [error, setError] = useState("")

  const fileRegex = /^.*\.(jpg|jpeg|png)$/i
  const facultyRegex = /^[A-Za-z\s]{1,20}$/
  const GPARegex = /^([0-3]\.\d{1,2}|4\.0{0,2}|[0-4])$/
  
  const navigate = useNavigate()

  const validate = ()=>{
    if(!user.highSchoolName||!user.highSchoolGPA||!user.highSchoolCertificate)
    {
      setError("All fields are required");
      return false;
    }
    if(!fileRegex.test(user.highSchoolCertificate.name)) {
      setError("only png , jpeg and jpg files are allowed");
      return false;
    }
    if(!GPARegex.test(user.highSchoolGPA)) {
      setError("Invalid GPA , must be between 0.0 and 4.0");
      return false;
    }
    if(!facultyRegex.test(user.highSchoolName)) {
      setError("too much characters in High School Name ");
      return false;
    }
    return true;  
  }
  const handleSubmit = (e)=>{
    e.preventDefault()
    if(!validate()){
      return;
    }
    incrementMeter()
    navigate("/registration/step4")

  }
  const handlePrevious = ()=>{
    decrementMeter()
    navigate("/registration/step2")
  }

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

      <form className="md:w-[50%] md:p-0 p-4 m-auto" onSubmit={handleSubmit}>
        <h1 className="mt-[50px] main-text"> Education Background</h1>
        <p className="sub-text mb-10">
          in this section , you can provide previous academic records
        </p>

        <div className="w-full grid grid-cols-2 gap-5">

          <div className="flex flex-col w-full mt-5">
            <label htmlFor="school" className="text-lg">High school name</label>
            <input type="text" className="textField" value={user.highSchoolName} onChange={(e)=>{setUser({...user,highSchoolName:e.target.value})}} />
          </div>

          <div className="flex flex-col w-full mt-5">
            <label htmlFor="gpa" className="text-lg">High school GPA</label>
            <input type="text" name="gpa" className="textField" value={user.highSchoolGPA} onChange={(e)=>{setUser({...user,highSchoolGPA:e.target.value})}} />
          </div>

          <div className="flex flex-col  w-full mt-5">
            <label htmlFor="file" className="">High school Certificate</label>
            <input type="file" name="file" className="textField file:mr-4 file:rounded-full file:border-0 file:bg-[] file:px-4 file:py-2 file:text-sm file:font-semibold file:text-black hover:file:text-white hover:file:scale-105 hover:file:transition-all hover:file:cursor-pointer hover:file:bg-[#0096C1]"  onChange={handleFile} />

          
          </div>
          <div className="flex flex-col w-full mt-5">
            <label htmlFor="faculty" className="text-lg">Faculty applied</label>
            <input type="text" name="faculty" disabled className="textField" value={user.facultyApplied} onChange={(e)=>{setUser({...user,facultyApplied:e.target.value})}} />
          </div>

          <div className="flex flex-col w-full mt-5">
            <label htmlFor="major" className="text-lg">Preferred Major</label>
            <select name="major" id="major" className="textField" value={user.preferredMajor} onChange={(e)=>{setUser({...user,preferredMajor:e.target.value})}}>
            <option value="">Choose Major</option>
              <option value="CS">Computer Science</option>
              <option value="IT">Information Technology</option>
              <option value="IS">Information Systems</option>
            </select>
          </div>

            {/* <h1 className="sub-text">your uploaded file : </h1>
            {user.highSchoolCertificate && <img src={URL.createObjectURL(user.highSchoolCertificate)} alt="certificate"  />} */}
          {/*TODO: limit file to png or jpg */}

        </div>
        {error && <p className="error-text mt-5">{error}</p>}
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

export default Step3;

/**
 * uploading file steps : 
 * make a state 
 * 2- set the file using e.target.files[0]
 * 3- to view the file   {/* <h1 className="sub-text">your uploaded file : </h1>
            {user.highSchoolCertificate && <img src={URL.createObjectURL(user.highSchoolCertificate)} alt="certificate"  />} 
   4- use form data to send it to api
   --
   customization using tailwind hoverr:file:etc 
   
*/
 