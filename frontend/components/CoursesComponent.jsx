import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom'; // Fixed import

import CoursesCard from './CoursesCard';
import { MdLibraryBooks } from 'react-icons/md'; // New Icon for Empty State
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';

const Courses = ({preview}) => {
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
  return (
    <div className="flex flex-col items-center justify-center w-full mt-5">
      {/* Header */}
      <div className="flex flex-row justify-between mr-auto w-[85%]">
        <h2 className="text-xl font-semibold">Enrolled Courses</h2>
        <Link to="/studentDashboard/Courses" className="text-[#0096C1] sub-text-2">
          See More
        </Link>
      </div>

      {/* TODO: implement pagination with API 
  //TODO: API Call here , or called before and fetched from context
      */}
      {/*  get call  , store in state here or as needed , if the method is to be used multiple time , define it in context , and use it here in useEffect  */}
      {/* Courses List */}
      <div className="flex flex-wrap gap-4 w-full mr-auto mt-5">
        {courses && courses.length > 0&& preview ? (
          courses.slice(0,3).map((course) => (
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
        ) :courses && courses.length > 0&& preview===false ? (
          courses.map((course) => (
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
          <div className="flex flex-col items-center justify-center w-full py-6 text-gray-500">
            <MdLibraryBooks size={40} className="text-gray-400" /> {/* New Icon */}
            <p className="text-sm mt-2">No courses available.</p> {/* Updated Message */}
          </div>
        )}
      </div>
    </div>
  );
};

export default Courses;
