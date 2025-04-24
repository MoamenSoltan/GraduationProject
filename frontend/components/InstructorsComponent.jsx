import React, { useEffect, useState } from "react";
import Slider from "react-slick";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { trimText } from "../utils/trim";
import toast from "react-hot-toast";
import useAxiosPrivate from "../hooks/useAxiosPrivate";


const InstructorsComponent = ({ preview }) => {
  const [loading, setLoading] = useState(false);

  const [instructorsData, setinstructorsData] = useState([])

  const axiosPrivate = useAxiosPrivate()


  

  const sliderSettings = {
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 3000,
    responsive: [
      { breakpoint: 1024, settings: { slidesToShow: 2, slidesToScroll: 1 } },
      { breakpoint: 768, settings: { slidesToShow: 1, slidesToScroll: 1 } },
    ],
  };

  useEffect(() => {
    const getInstructors = async () => {
      try {
        if (loading) return;
        setLoading(true);
        const response = await axiosPrivate.get("/student/instructors");
        setinstructorsData(response.data);
        console.log("Instructors data :",response.data);
        
      } catch (error) {
        console.error("Error fetching instructors:", error.response.data.detail);
        toast.error(`Error fetching instructors: ${error?.response?.data?.detail}`);
      } finally {
        setLoading(false);
      }
    };
    getInstructors();
  }, []);

  return preview ? (
    <div className="flex flex-col w-full bg-white shadow-lg rounded-xl p-6 mt-5 relative overflow-hidden">
      {/* Gradient Header */}
      <div className="absolute top-0 left-0 w-full h-2 bg-gradient-to-r from-purple-500 to-indigo-500"></div>

      {/* Section Title */}
      <h2 className="text-xl font-semibold text-gray-700 mb-4 text-center">
        Top Instructors
      </h2>

      {/* Show spinner while loading */}
      {loading ? (
        <div className="flex justify-center my-5">
          <div className="w-10 h-10 border-4 border-gray-300 border-t-purple-500 rounded-full animate-spin"></div>
        </div>
      ) : instructorsData?.length === 0 ? (
        <p className="text-center text-gray-500 mt-3">No instructors found.</p>
      ) : (
        <>
          {/* Overlapping Images */}
          <div className="flex justify-center -space-x-4 mt-2">
            {instructorsData.slice(0, 5).map((instructor, index) => (
              <img
                key={instructor.instructorId}
                src={instructor.personalImage}
                alt={`Instructor ${index + 1}`}
                className="w-16 h-16 rounded-full border-2 border-white shadow-lg transition-transform hover:scale-110"
                style={{ zIndex: instructorsData?.length - index }}
              />
            ))}
          </div>

          <p className="text-gray-500 text-sm mt-3 text-center">
            Top instructors in your College
          </p>
        </>
      )}
    </div>
  ) : (
    <div className="m-auto w-[90%] mt-10">
      <h1 className="text-2xl font-bold text-center mb-10 text-gray-800">
        Instructors in Your College
      </h1>

      {/* Show spinner while loading */}
      {loading ? (
        <div className="flex justify-center">
          <div className="w-10 h-10 border-4 border-gray-300 border-t-purple-500 rounded-full animate-spin"></div>
        </div>
      ) : instructorsData?.length === 0 ? (
        <p className="text-center text-gray-500">No instructors found.</p>
      ) : (
        <Slider {...sliderSettings}>
          {instructorsData.map((instructor) => (
            <div key={instructor.instructorId} className="p-4">
              <div className="bg-white shadow-md rounded-xl overflow-hidden hover:scale-105 transition-all transform duration-300">
                <div className="relative">
                  <div className="w-full h-2 bg-gradient-to-r from-purple-500 to-indigo-500"></div>
                  <img
                    src={instructor.personalImage}
                    alt={instructor.firstName}
                    className="w-48 h-48 object-cover border-2 border-black  rounded-full m-auto"
                  />
                </div>

                <div className="p-6 text-center">
                  <h2 className="text-lg font-semibold text-gray-900">
                    {instructor.firstName} {instructor.lastName}
                  </h2>
                  <h3 className="text-gray-600 text-sm">
                    {instructor.managedDepartment
                      ? `${instructor?.managedDepartment?.departmentName} Department Manager`
                      : `${instructor?.department?.departmentName} Department`}
                  </h3>
                  <p className="text-gray-500 min-h-[100px] text-sm mt-3">
                  {trimText(instructor?.bio ?? "", 200)}

                  </p>
                </div>
              </div>
            </div>
          ))}
        </Slider>
      )}
    </div>
  );
};

export default InstructorsComponent;
