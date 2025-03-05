import React from "react";
import person from "../data/avatar.jpg";
import { FaCameraRotate } from "react-icons/fa6";
import { BsFillPersonFill } from "react-icons/bs";
import { useStateContext } from "../contexts/ContextProvider";
import { LuBookText } from "react-icons/lu";
import { div, form } from "motion/react-client";
import { trimText } from "../utils/trim";

const Profile = () => {
  // arbitrary values for breakpoints on the fly using min-[] or max-[]
  const { user, setUser } = useStateContext();
  //TODO:   replace with currenUser , containing img and array of courses so on

  // const dummyCourses= ["Distributed systems","Robotics","Game dev","NLP","IR","Artificial intelligence"]

  const handleChange = (e) => {
    const { value, name } = e.target;
    setUser((prev) => ({ ...prev, [name]: value }));
  };
  return (
    <form>
      <div className="w-full md:w-[80%] flex flex-col xl:flex-row gap-10 justify-center p-4 m-auto">
        {/* personal */}
        <div className="w-full xl:w-[45%] ">
          {/* photo */}
          <div className="p-6 bg-[#f6f8fa] flex flex-row space-y-3 space-x-8 rounded-lg">
            <img
              src={person}
              className="rounded-full w-24 h-24 border-gray-300 border-4"
              alt="profile photo"
            />

            <div className="">
              <h2 className="sub-text-2">Your Photo</h2>
              <h3 className="sub-text">
                this will be displayed on your profile
              </h3>

              <button className="hover:scale-105 transition-all mt-5 w-[60%] p-2 rounded-md border-[#0096c1] border-2 text-[#0096c1] bg-white flex flex-row space-x-2 items-center">
                <FaCameraRotate className="text-2xl" />

                <span className="">Change Photo</span>
              </button>
            </div>
          </div>

          {/* info */}

          <div className=" mt-10 p-6 bg-[#f6f8fa] flex flex-col space-y-3  rounded-lg">
            <div className="flex flex-row gap-4">
              <BsFillPersonFill className="text-2xl" />
              <h2 className="sub-text-2">Personal info</h2>
            </div>

            <div>
              <label htmlFor="Fname" className="sub-text ">
                First name
              </label>
              <input
                type="text"
                name="firstName"
                value={user.firstName}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Fname"
              />

              <label htmlFor="Lname" className="sub-text ">
                Last name
              </label>
              <input
                type="text"
                name="lastName"
                value={user.lastName}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Lname"
              />

              <label htmlFor="Country" className="sub-text ">
                Country
              </label>
              <input
                type="text"
                name="country"
                value={user.country}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Country"
              />
              <label htmlFor="City" className="sub-text ">
                City
              </label>
              <input
                type="text"
                name="city"
                value={user.city}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="City"
              />
              <label htmlFor="Address" className="sub-text ">
                Address
              </label>
              <input
                type="text"
                name="address"
                value={user.address}
                onChange={handleChange}
                className="my-3 textField w-full"
                id="Address"
              />
            </div>
          </div>
        </div>

        {/* educational */}

        <div className="w-full xl:w-[45%]">
          <div className="p-6 bg-[#f6f8fa] flex flex-col space-y-12  rounded-lg">
            <div className="flex flex-row gap-4">
              <LuBookText className="text-2xl" />
              <h2 className="sub-text-2">Educational info</h2>
              <h3 className="sub-text">*Immutable</h3>
            </div>

            <div>
              <label htmlFor="Fname" className="sub-text  ">
                Grade
              </label>
              <input
              disabled
                type="text"
                name="grade"
                value={user.grade}
                onChange={handleChange}
                className="my-3 textField w-full disabled:bg-white"
              />

              <label htmlFor="Lname" className="sub-text ">
                Completed Hours
              </label>
              <input
              disabled
                type="text"
                name="completedHours"
                value={user.completedHours}
                onChange={handleChange}
                className="my-3 textField w-full disabled:bg-white"
              />

              <label htmlFor="Country" className="sub-text ">
                Current GPA
              </label>
              <input
              disabled
                type="text"
                name="currentGPA"
                value={user.currentGPA}
                onChange={handleChange}
                className="my-3 textField w-full disabled:bg-white"
              />
              <label htmlFor="City" className="sub-text ">
                Current Joined Courses
              </label>
             <div className="textfield w-full  bg-white my-3 border-gray-300 border-[1px] rounded-md flex flex-wrap flex-row gap-2 p-2 ">
                {
                    user.currentCourses.map((course, index) => (
                        <div key={index} className=" px-2 rounded-lg bg-blue-100 gap-2">
                            <span className="font-light">{trimText(course,15)}</span>
                        </div>
                    ))
                }

             </div>
              <label htmlFor="Address" className="sub-text ">
                University
              </label>
              <input
              disabled
                type="text"
                name="university"
                value={user.university}
                onChange={handleChange}
                className="my-3 textField w-full disabled:bg-white"
              />

              <label htmlFor="Address" className="sub-text ">
                Major
              </label>
              <input
                type="text"
                disabled
                name="major"
                value={user.major}
                onChange={handleChange}
                className="my-3 textField w-full disabled:bg-white"
              />

              <label htmlFor="Address" className="sub-text ">
                Student Code
              </label>
              <input
                type="text"
                disabled
                name="studentCode"
                value={user.studentCode}
                onChange={handleChange}
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
