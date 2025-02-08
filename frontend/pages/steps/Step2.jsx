import React, { useEffect, useState } from "react";
import ProgressBar from "../../components/ProgressBar";
import { replace, useNavigate } from "react-router";
import { useStateContext } from "../../contexts/ContextProvider";
import { motion } from "motion/react";

const Step2 = () => {
  const { user, setUser, incrementMeter, decrementMeter } = useStateContext();
  useEffect(() => {
    console.log(user);
  }, []);

  const Countries = [
    "","Egypt", "Saudi Arabia", "United Arab Emirates", "Qatar", "Kuwait", "Oman", 
    "Bahrain", "Iraq", "Jordan", "Lebanon", "Syria", "Yemen", "Palestine", "Iran", "Turkey"
  ];

  const Cities = [
   "", "Alexandria",
  "Ismailia",
  "Aswan",
  "Assiut",
  "Luxor",
  "Red Sea",
  "Beheira",
  "Beni Suef",
  "Port Said",
  "South Sinai",
  "Giza",
  "Dakahlia",
  "Damietta",
  "Sohag",
  "Suez",
  "Sharqia",
  "North Sinai",
  "Gharbia",
  "Fayoum",
  "Cairo",
  "Qalyubia",
  "Qena",
  "Kafr El Sheikh",
  "Matruh",
  "Menoufia",
  "Minya",
  "New Valley"
  ];

  const [error, setError] = useState(null)
  const validate = ()=>{
    if(!user.country||!user.city||!user.address)
    {
      setError("Please fill all fields")
      return false
    }
    if(!user.country ||!user.city){
      setError("Please select country and city")
      return false
    }
    setError(null)
    return true
  }

  const navigate = useNavigate();

  const handleSubmit = (e)=>{
      e.preventDefault()
      if(!validate()){
        return;
      }
      
      incrementMeter()
      navigate("/registration/step3")
      
      
    }
    const handlePrevious = (e)=>{
      e.preventDefault()
      decrementMeter()
      navigate("/registration/step1")
    }
  
    useEffect(() => {
      console.log(user.dateOfBirth);
    }, [user.dateOfBirth]); // Fix dependency array (should be an array)
  return (
    <motion.div initial={{ x:"-5%", }}
    animate={{ x:0,  }}
    exit={{ x:"-5%",  }}
    transition={{ duration: 0.5 }}>
      <div className="w-full mt-10 flex justify-center">
        <ProgressBar />
      </div>

      <form onSubmit={handleSubmit} className="md:w-[50%] md:p-0 p-4 m-auto">
        <h1 className="mt-[50px] main-text"> Contact & Address Details</h1>
        <p className="sub-text mb-10">
          in this section , you can provide your contact and address details
        </p>
        <div className="w-full flex flex-col">
          <div className="flex flex-col w-full">
          

            <div className="flex flex-col w-full mt-5">
            <label htmlFor="countries" className="text-lg">Country</label>
            <select name="countries" value={user.country} onChange={(e)=>{
              setUser({...user, country: e.target.value })
            }} className="textField" id="countries">
              {Countries.map((country, index) => (
                <option key={index} value={country}>
                  {country}
                </option>
              ))}
            </select>
            </div>

            <div className="flex flex-col w-full mt-5">
            <label htmlFor="cities" className="text-lg">City of residence in egypt</label>
            <select value={user.city} onChange={(e)=>{setUser({...user,city:e.target.value})}} name="cities" className="textField " id="cities">
              {Cities.map((city, index) => (
                <option  key={index} value={city}>
                  {city}
                </option>
              ))}
            </select>
            <label htmlFor="address" className="text-lg mt-5">
              Address
            </label>
            <input
              className="textField "
              type="text"
              placeholder="Enter Your Address"
              value={user.address}
              onChange={(e) =>
                setUser({...user, address: e.target.value })
              }
            />
            </div>
            
          </div>
        

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

export default Step2;
