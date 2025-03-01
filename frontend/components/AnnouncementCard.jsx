import React, { useState } from "react";
import { trimText } from "../utils/trim";
import Modal from "./Modal";

const AnnouncementCard = ({ title, description, date, time, instructor, course, type }) => {
  const [open, setOpen] = useState(false)

  return (
    <div onClick={()=>setOpen(true)} className="border cursor-pointer border-gray-300 rounded-lg p-4 shadow-md w-[300px] bg-white">
      
      <h2 className="text-lg font-semibold">{trimText(title, 25)}</h2>
      <p className="text-gray-500 text-sm">{trimText(description, 50)}</p>
      <p className="text-gray-700 text-sm">Instructor: {instructor}</p>
      <p className="text-gray-700 text-sm">Course: {course}</p>
      <p className="text-gray-700 text-sm">Date: {date} at {time}</p>

      <div className="flex flex-row justify-between mt-2">
        <p className="text-gray-500 text-sm">Time: {time}</p>
        {type === "Assignment" ? (
          <div className="p-[5px] text-[#1165ef] bg-[#ebf2ff] rounded-lg">Assignment</div>
        ) : type === "Event" ? (
          <div className="p-[5px] text-[#08875d] bg-[#edfdf8] rounded-lg">Event</div>
        ) : type === "Exam" ? (
          <div className="p-[5px] text-[#b25e09] bg-[#f2ebdf] rounded-lg">Exam</div>
        ) : null}
      </div>

      <Modal open={open} onClose={()=>setOpen(false)}>
        
      <h2 className="text-lg font-semibold">{title}</h2>
      <p className="text-gray-500 text-sm">{description}</p>
      <p className="text-gray-700 text-sm">Instructor: {instructor}</p>
      <p className="text-gray-700 text-sm">Course: {course}</p>
      <p className="text-gray-700 text-sm">Date: {date} at {time}</p>

      <div className="flex flex-row justify-between mt-2">
        <p className="text-gray-500 text-sm">Time: {time}</p>
        {type === "Assignment" ? (
          <div className="p-[5px] text-[#1165ef] bg-[#ebf2ff] rounded-lg">Assignment</div>
        ) : type === "Event" ? (
          <div className="p-[5px] text-[#08875d] bg-[#edfdf8] rounded-lg">Event</div>
        ) : type === "Exam" ? (
          <div className="p-[5px] text-[#b25e09] bg-[#f2ebdf] rounded-lg">Exam</div>
        ) : null}
      </div>
        
        </Modal>
    </div>
  );
};

export default AnnouncementCard;
