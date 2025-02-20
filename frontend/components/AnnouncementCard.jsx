import React from "react";
import { trimText } from "../utils/trim";

const AnnouncementCard = ({ title, description, date, time, instructor, course, type }) => {
  return (
    <div className="border border-gray-300 rounded-lg p-4 shadow-md w-[300px] bg-white">
      <h2 className="text-lg font-semibold text-gray-800">{trimText(title, 25)}</h2>
      <p className="text-sm text-gray-600">{trimText(description, 50)}</p>
      <div className="mt-2 text-xs text-gray-500">
        <p><strong>Course:</strong> {course}</p>
        <p><strong>Instructor:</strong> {instructor}</p>
        <p><strong>Date:</strong> {date} at {time}</p>
        <span className="inline-block mt-2 px-2 py-1 text-xs font-semibold text-white bg-blue-500 rounded">{type}</span>
      </div>
    </div>
  );
};

export default AnnouncementCard;
