import React, { useEffect, useState } from "react";
import person from "../data/avatar.jpg";
import { FaCameraRotate } from "react-icons/fa6";
import { BsFillPersonFill } from "react-icons/bs";
import { useStateContext } from "../contexts/ContextProvider";
import { LuBookText } from "react-icons/lu";
import { trimText } from "../utils/trim";
import toast from "react-hot-toast";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useLocation } from "react-router";

const Profile = () => {
  const axiosPrivate = useAxiosPrivate();
  const { setAuth ,auth,updatedProfileData,setupdatedProfileData } = useStateContext();
  const [isChanged, setIsChanged] = useState(false);
  

  const [loading, setloading] = useState(false);
  const [profileData, setprofileData] = useState({});
// TODO: very important , an object , not an array 
 
  // TODO: remember to match names exactly with backend

  const requiredPatch = [
    "firstName","lastName","city","country","address"
  ]

  let image = updatedProfileData?.imagePreview 
  ? updatedProfileData.imagePreview
  :  auth.personalImage 

  const handleChange = (e) => {
    const { value, name } = e.target;
    setupdatedProfileData((prev) => ({ ...prev, [name]: value }));
    setIsChanged(true);
  };

  const handleFileChange = (e)=>{
    const file = e.target.files[0];
    setupdatedProfileData((prev)=>({...prev,
      personalImage:file,
      imagePreview:URL.createObjectURL(file)}))
    setIsChanged(true)
    }

  const handleSave = (e) => {
    e.preventDefault()
    // console.log("Changes saved!", updatedProfileData);

    const formdata = new FormData();
    for (let key in requiredPatch) {
      formdata.append(requiredPatch[key], updatedProfileData[requiredPatch[key]])
    }

    if (updatedProfileData.personalImage)
      formdata.append("personalImage", updatedProfileData.personalImage)

    for (let [key, value] of formdata.entries()) {
      console.log(key, value);
  }

  console.log("updated data without formdata : ",updatedProfileData);
  
    

    const updateProfile = async () => {
      try {
        const response = await axiosPrivate.patch("/student/profile",formdata,{
          headers: { "Content-Type": "multipart/form-data" },
          withCredentials: true
      });
      // TODO: dont forget headers here
        toast.success("Profile updated successfully")
        console.log("patch request data : ",response.data);
        // dont forget to set profiledata , to get last information
        setprofileData(response.data)
        setAuth((prev)=>({...prev,...updatedProfileData}))
        // to trigger navbar profile to re render , with latest data
      } catch (error) {
        toast.error("Failed to update profile")
      }
    };

     updateProfile();

    setIsChanged(false); // Reset state after saving
  };

  

  useEffect(() => {
    if (loading) return;
    setloading(true);
    const fetchProfile = async () => {
      try {
        const response = await axiosPrivate.get("/student/profile");
        setprofileData(response.data);
        // // TODO: very important to initialize the updated data
        setupdatedProfileData((prev)=>({...prev,...response.data}))

        console.log("Profile info : ", response.data);
      } catch (error) {
        toast.error("an error occurred");
      } finally {
        setloading(false);
      }
    };

    fetchProfile();
  }, []);

  return (
    <form onSubmit={handleSave}>
      <div className="w-full md:w-[80%] flex flex-col xl:flex-row gap-10 justify-center p-4 m-auto">
        <div className="w-full xl:w-[45%] ">
          <div className="p-6 bg-[#f6f8fa] flex flex-row space-y-3 space-x-8 rounded-lg">
            <img
              src={image}
              className="rounded-full w-24 h-24 border-gray-300 border-4"
              alt="profile photo"
            />

            <div>
              <h2 className="sub-text-2">Your Photo</h2>
              <h3 className="sub-text">
                This will be displayed on your profile
              </h3>

              <input type="file" name="personalImage" className="textField mt-3 file:mr-4 file:rounded-full file:border-0 file:bg-[] file:px-4 file:py-2 file:text-sm file:font-semibold file:text-black hover:file:text-white hover:file:scale-105 hover:file:transition-all hover:file:cursor-pointer hover:file:bg-[#0096C1]"  onChange={handleFileChange} />

              {/* <button className="hover:scale-105 transition-all mt-5 w-[70%] p-2 rounded-md border-[#0096c1] border-2 text-[#0096c1] bg-white flex flex-row space-x-2 items-center">
                <FaCameraRotate className="text-2xl" />
                <span>Change Photo</span>
              </button> */}
            </div>
          </div>

          <div className="mt-10 p-6 bg-[#f6f8fa] flex flex-col space-y-3 rounded-lg">
            <div className="flex flex-row gap-4">
              <BsFillPersonFill className="text-2xl" />
              <h2 className="sub-text-2">Personal Info</h2>
            </div>

            <div>
              <label htmlFor="Fname" className="sub-text">
                First name
              </label>
              <input
                type="text"
                name="firstName"
                value={updatedProfileData?.firstName ?? profileData?.firstName ?? ""}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Fname"
              />
              {/* TODO: ?? nullish coalescing , handles null or undefined , unlike ? falsy coalescing */}

              <label htmlFor="Lname" className="sub-text">
                Last name
              </label>
              <input
                type="text"
                name="lastName"
                value={updatedProfileData?.lastName ?? profileData?.lastName ?? ""}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Lname"
              />

              <label htmlFor="Country" className="sub-text">
                Country
              </label>
              <input
                type="text"
                name="country"
                value={updatedProfileData?.country ?? profileData?.country ?? ""}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Country"
              />

              <label htmlFor="City" className="sub-text">
                City
              </label>
              <input
                type="text"
                name="city"
                value={updatedProfileData?.city ?? profileData?.city ?? ""}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="City"
              />

              <label htmlFor="Address" className="sub-text">
                Address
              </label>
              <input
                type="text"
                name="address"
                value={updatedProfileData?.address ?? profileData?.address ?? ""}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Address"
              />
            </div>

            <button
              type="submit"
              disabled={!isChanged}
              className={`mt-4 p-3 rounded-lg w-full font-semibold transition-all ${
                isChanged
                  ? "bg-blue-500 text-white hover:bg-blue-600"
                  : "bg-gray-300 text-gray-500 cursor-not-allowed"
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
              <label htmlFor="Grade" className="sub-text">
                Grade
              </label>
              <input
                disabled
                type="text"
                name="grade"
                value={profileData?.grade || ""}
                className="my-3 textField w-full disabled:bg-white"
              />

              <label htmlFor="CompletedHours" className="sub-text">
                Completed Hours
              </label>
              <input
                disabled
                type="text"
                name="totalCredit"
                value={profileData?.totalCredit ||"0"}
                className="my-3 textField w-full disabled:bg-white"
              />

              <label htmlFor="CurrentGPA" className="sub-text">
                Current GPA
              </label>
              <input
                disabled
                type="text"
                name="gpa"
                value={profileData?.gpa ||"0"}
                className="my-3 textField w-full disabled:bg-white"
              />

              <label htmlFor="CurrentCourses" className="sub-text">
                Current Joined Courses
              </label>
              <div className="textfield w-full min-h-11 bg-white my-3 border-gray-300 border-[1px] rounded-md flex flex-wrap gap-2 p-2">
                {profileData?.courses?.length > 0 ? (
                  profileData.courses.map((course, index) => (
                    <div
                      key={index}
                      className="px-2 rounded-lg bg-blue-100 gap-2"
                    >
                      <span className="font-light truncate">{course.courseName}</span>
                    </div>
                  ))
                ) : (
                  <h1 className="sub-text">none</h1>
                )}
              </div>

              <label htmlFor="University" className="sub-text">
                University
              </label>
              <input
                disabled
                type="text"
                name="university"
                value={"FCI"}
                className="my-3 textField w-full disabled:bg-white"
              />

              <label htmlFor="Major" className="sub-text">
                Major
              </label>
              <input
                disabled
                type="text"
                name="department"
                value={profileData?.department||""}
                className="my-3 textField w-full disabled:bg-white"
              />

              <label htmlFor="StudentCode" className="sub-text">
                Student Code
              </label>
              <input
                disabled
                type="text"
                name="studentCode"
                value={profileData?.id ||""}
                className="my-3 textField w-full disabled:bg-white"
              />
            </div>
          </div>
        </div>
      </div>
    </form>
  );
};

export default Profile;
