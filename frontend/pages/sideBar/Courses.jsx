import React from "react";
import { useParams, useNavigate } from "react-router-dom";
import CoursesCard from "../../components/CoursesCard";
import { coursesData } from "../../data/dummy";
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

  const filteredCourses = year
    ? coursesData.filter((course) => course.year === year)
    : coursesData; // Show all if no year is selected

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
          <option value="FirstYear">First Year</option>
          <option value="SecondYear">Second Year</option>
          <option value="ThirdYear">Third Year</option>
          <option value="FourthYear">Fourth Year</option>
        </select>
      </div>

      {/* Filtered Course List */}
      {["Required", "Recommended", "Optional"].map((type) => (
        <div key={type}>
          <h2 className="text-xl font-semibold text-left mt-10">{type}</h2>
          <div className="mt-5 flex flex-row gap-1 flex-wrap">
            {filteredCourses.filter((course) => course.type === type).length > 0 ? (
              filteredCourses
                .filter((course) => course.type === type)
                .map((course) => (
                  <CoursesCard
                    key={course.id}
                    name={course.name}
                    code={course.code}
                    schedule={course.schedule}
                    credits={course.credits}
                    instructor={course.instructor}
                    studentsEnrolled={course.studentsEnrolled}
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
