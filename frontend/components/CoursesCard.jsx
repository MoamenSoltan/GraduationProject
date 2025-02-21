import { div } from 'motion/react-client';
import React from 'react';

const CoursesCard = ({ name, code, instructor, schedule, credits, studentsEnrolled ,type}) => {
  return (
    <div className="border  border-gray-300 rounded-lg p-4 shadow-md w-[300px] bg-white">
      <h2 className="text-lg font-semibold">{name}</h2>
      <p className="text-gray-500 text-sm">{code}</p>
      <p className="text-gray-700 text-sm">Instructor: {instructor}</p>
      <p className="text-gray-700 text-sm">Schedule: {schedule}</p>
      <p className="text-gray-700 text-sm">Credits: {credits}</p>
      
      <div className='flex flex-row justify-between'>
      <p className="text-gray-500 text-sm">Students: {studentsEnrolled}</p>
      {
        type ==="Required" ? <div className=' p-[5px] text-[#1165ef] bg-[#ebf2ff] rounded-lg'>Required</div> :
        type ==="Recommended" ? <div className=' p-[5px] text-[#08875d] bg-[#edfdf8] rounded-lg'>Recommended</div> :
        type ==="Optional" && <div className=' p-[5px] text-[#b25e09] bg-[#f2ebdf] rounded-lg'>Optional</div>
        
      }
      </div>
    </div>
  );
};

export default CoursesCard;
