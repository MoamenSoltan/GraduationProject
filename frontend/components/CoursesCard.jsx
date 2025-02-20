import React from 'react';

const CoursesCard = ({ name, code, instructor, schedule, credits, studentsEnrolled }) => {
  return (
    <div className="border border-gray-300 rounded-lg p-4 shadow-md w-[300px] bg-white">
      <h2 className="text-lg font-semibold">{name}</h2>
      <p className="text-gray-500 text-sm">{code}</p>
      <p className="text-gray-700 text-sm">Instructor: {instructor}</p>
      <p className="text-gray-700 text-sm">Schedule: {schedule}</p>
      <p className="text-gray-700 text-sm">Credits: {credits}</p>
      <p className="text-gray-500 text-sm">Students: {studentsEnrolled}</p>
    </div>
  );
};

export default CoursesCard;
