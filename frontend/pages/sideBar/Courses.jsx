import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import CoursesCard from "../../components/CoursesCard";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import toast from "react-hot-toast";

/**
 * steps to use url parameters for data fetching
 * 1- add /:year?
 * 2- destructure using useParams
 * 3- useNavigate and filter the array to be equal to the year , note : if its a number parse it after destructuring 
 * 4- onchange of dropdown , navigate to value of dropdown (the year , which would be checked against the url to match data)
 * 5- always filtering data based on url allows for dynamic fetching of data , meaning the comparison against year is what does the magic 
 * 
 * 6- note to self : use slice(0,1) to limit map , use filter , use map for repetitive tasks
 */

const Courses = () => {
  const { year } = useParams(); // Get year from URL
  const navigate = useNavigate();

  const axiosPrivate = useAxiosPrivate()

  const [courses, setcourses] = useState([])
  useEffect(()=>{
    const fetchCourses = async ()=>{
      try {
        const response = await axiosPrivate.get("student/courses")
        setcourses(response.data)
        console.log("fetched courses = :",response.data);
        
      } catch (error) {
        toast.error(`an error occurred : ${error}`)
      }
    }
    fetchCourses()
  },[])
  const filteredCourses = year
    ? courses?.filter((course) => course.year === year)
    : courses; // Show all if no year is selected

  return (
    <div className="flex flex-col  w-[80%]   m-auto">
      {/* Dropdown to Change Year */}
      <div className="absolute mt-10 right-[10%] rounded-sm">
        <select
          className="outline-none border-gray-200 border-2 rounded-sm"
          value={year || ""}
          onChange={(e) => navigate(`/studentDashboard/courses/${e.target.value}`)}
        >
          <option value="">All Years</option>
          <option value="FIRST_YEAR">First Year</option>
          <option value="SECOND_YEAR">Second Year</option>
          <option value="THIRD_YEAR">Third Year</option>
          <option value="FOURTH_YEAR">Fourth Year</option>
        </select>
      </div>

      {/* Filtered Course List */}
      {["Required", "Recommended", "Optional"].map((type) => (
        <div key={type}>
          <h2 className="text-xl font-semibold text-left mt-10">{type}</h2>
          <div className="mt-5 flex flex-row gap-1 flex-wrap">
            {filteredCourses.filter((course) => course.type.toUpperCase() === type.toUpperCase()).length > 0 ? (
              filteredCourses
                .filter((course) => course.type.toUpperCase() === type.toUpperCase())
                .map((course) => (
                  <CoursesCard
                    key={course.courseId}
                    name={course.courseName}
                    code={course.courseCode}
                    schedule={course.schedule}
                    credits={course.credit}
                    instructor={course?.instructor?.firstName + " " + course?.instructor?.lastName}
                    studentsEnrolled={course.studentEnrolled}
                    type={course.type}
                  />
                ))
            ) : (
              <p className="text-gray-500 min-h-[15vh]">No {type} courses available.</p>
            )}
          </div>
          <div className="w-[80%] h-[1px] bg-slate-200 mt-10 m-auto"></div>
        </div>
      ))}
    </div>
  );
};

export default Courses;
