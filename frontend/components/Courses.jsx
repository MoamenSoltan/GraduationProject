import React from 'react';
import { Link } from 'react-router-dom'; // Fixed import
import { coursesData } from '../data/dummy';
import CoursesCard from './CoursesCard';
import { MdLibraryBooks } from 'react-icons/md'; // New Icon for Empty State

const Courses = () => {
  return (
    <div className="flex flex-col items-center justify-center w-full mt-5">
      {/* Header */}
      <div className="flex flex-row justify-between mr-auto w-full">
        <h2 className="text-xl font-semibold">Enrolled Courses</h2>
        <Link to="/studentDashboard/Courses" className="text-[#0096C1] sub-text-2">
          See More
        </Link>
      </div>

      {/* TODO: implement pagination with API */}
      {/* Courses List */}
      <div className="flex flex-wrap gap-4 w-full mr-auto mt-5">
        {coursesData && coursesData.length > 0 ? (
          coursesData.map((course) => (
            <CoursesCard
              key={course.id}
              name={course.name}
              code={course.code}
              schedule={course.schedule}
              credits={course.credits}
              instructor={course.instructor}
              studentsEnrolled={course.studentsEnrolled}
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
