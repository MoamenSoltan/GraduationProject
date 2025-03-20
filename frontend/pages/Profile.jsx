import React, { useEffect, useState } from "react";
import person from "../data/avatar.jpg";
import { FaCameraRotate } from "react-icons/fa6";
import { BsFillPersonFill } from "react-icons/bs";
import { useStateContext } from "../contexts/ContextProvider";
import { LuBookText } from "react-icons/lu";
import { trimText } from "../utils/trim";
import toast from "react-hot-toast";
import useAxiosPrivate from "../hooks/useAxiosPrivate";


const Profile = () => {

  const axiosPrivate = useAxiosPrivate()
  const { user, setUser ,auth } = useStateContext();
  const [isChanged, setIsChanged] = useState(false);

  const [loading, setloading] = useState(false)
  const [profileData, setprofileData] = useState([])

  const handleChange = (e) => {
    const { value, name } = e.target;
    setUser((prev) => ({ ...prev, [name]: value }));
    setIsChanged(true);
  };

  const handleSave = () => {
    console.log("Changes saved!");
    setIsChanged(false); // Reset state after saving
  };

  useEffect(()=>{
    if (loading)
      return
    setloading(true)
    const fetchProfile = async () => {

      try {
        const response = await axiosPrivate.get("/student/profile")
        setprofileData(response.data)
        console.log(response.data);
        
      } catch (error) {
        toast.error("an error occurred");
        
      } finally {
        setloading(false)
      }
    }

    fetchProfile()
  },[])

  return (
   <h1>hello</h1>
  );
};

export default Profile;

{/* <form>
<div className="w-full md:w-[80%] flex flex-col xl:flex-row gap-10 justify-center p-4 m-auto">
  
  <div className="w-full xl:w-[45%] ">
    
    <div className="p-6 bg-[#f6f8fa] flex flex-row space-y-3 space-x-8 rounded-lg">
      <img
        src={person}
        className="rounded-full w-24 h-24 border-gray-300 border-4"
        alt="profile photo"
      />

      <div>
        <h2 className="sub-text-2">Your Photo</h2>
        <h3 className="sub-text">This will be displayed on your profile</h3>

        <button className="hover:scale-105 transition-all mt-5 w-[70%] p-2 rounded-md border-[#0096c1] border-2 text-[#0096c1] bg-white flex flex-row space-x-2 items-center">
          <FaCameraRotate className="text-2xl" />
          <span>Change Photo</span>
        </button>
      </div>
    </div>

   
    <div className="mt-10 p-6 bg-[#f6f8fa] flex flex-col space-y-3 rounded-lg">
      <div className="flex flex-row gap-4">
        <BsFillPersonFill className="text-2xl" />
        <h2 className="sub-text-2">Personal Info</h2>
      </div>

      <div>
        <label htmlFor="Fname" className="sub-text">First name</label>
        <input type="text" name="firstName" value={user.firstName} onChange={handleChange} className="my-3 textField w-full" id="Fname" />

        <label htmlFor="Lname" className="sub-text">Last name</label>
        <input type="text" name="lastName" value={user.lastName} onChange={handleChange} className="my-3 textField w-full" id="Lname" />

        <label htmlFor="Country" className="sub-text">Country</label>
        <input type="text" name="country" value={user.country} onChange={handleChange} className="my-3 textField w-full" id="Country" />

        <label htmlFor="City" className="sub-text">City</label>
        <input type="text" name="city" value={user.city} onChange={handleChange} className="my-3 textField w-full" id="City" />

        <label htmlFor="Address" className="sub-text">Address</label>
        <input type="text" name="address" value={user.address} onChange={handleChange} className="my-3 textField w-full" id="Address" />
      </div>

     
      <button
        onClick={handleSave}
        disabled={!isChanged}
        className={`mt-4 p-3 rounded-lg w-full font-semibold transition-all ${
          isChanged ? "bg-blue-500 text-white hover:bg-blue-600" : "bg-gray-300 text-gray-500 cursor-not-allowed"
        }`}
      >
        Save Changes
      </button>
    </div>
  </div>

  
  <div className="w-full xl:w-[45%]">
    <div className="p-6 bg-[#f6f8fa] flex flex-col space-y-12 rounded-lg">
      <div className="flex flex-row gap-4">
        <LuBookText className="text-2xl" />
        <h2 className="sub-text-2">Educational Info</h2>
        <h3 className="sub-text">*Immutable</h3>
      </div>

      <div>
        <label htmlFor="Grade" className="sub-text">Grade</label>
        <input disabled type="text" name="grade" value={user.grade} className="my-3 textField w-full disabled:bg-white" />

        <label htmlFor="CompletedHours" className="sub-text">Completed Hours</label>
        <input disabled type="text" name="completedHours" value={user.completedHours} className="my-3 textField w-full disabled:bg-white" />

        <label htmlFor="CurrentGPA" className="sub-text">Current GPA</label>
        <input disabled type="text" name="currentGPA" value={user.currentGPA} className="my-3 textField w-full disabled:bg-white" />

        <label htmlFor="CurrentCourses" className="sub-text">Current Joined Courses</label>
        <div className="textfield w-full bg-white my-3 border-gray-300 border-[1px] rounded-md flex flex-wrap gap-2 p-2">
          {user.currentCourses.map((course, index) => (
            <div key={index} className="px-2 rounded-lg bg-blue-100 gap-2">
              <span className="font-light">{trimText(course, 15)}</span>
            </div>
          ))}
        </div>

        <label htmlFor="University" className="sub-text">University</label>
        <input disabled type="text" name="university" value={user.university} className="my-3 textField w-full disabled:bg-white" />

        <label htmlFor="Major" className="sub-text">Major</label>
        <input disabled type="text" name="major" value={user.major} className="my-3 textField w-full disabled:bg-white" />

        <label htmlFor="StudentCode" className="sub-text">Student Code</label>
        <input disabled type="text" name="studentCode" value={user.studentCode} className="my-3 textField w-full disabled:bg-white" />
      </div>
    </div>
  </div>
</div>
</form> */}
