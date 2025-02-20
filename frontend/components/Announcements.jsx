import React from 'react';
import { Link } from 'react-router-dom';
import { announcementsData } from '../data/dummy';
import AnnouncementCard from './AnnouncementCard';
import { IoIosNotificationsOff } from "react-icons/io";

const Announcements = () => {
  return (
    <div className="flex flex-col items-center justify-center w-full">
      <div className="flex flex-row justify-between mr-auto w-full">
        <h2 className="text-xl font-semibold">Announcements</h2>
        <Link to="/studentDashboard/Announcements" className="text-[#0096C1] sub-text-2">
          See More
        </Link>
      </div>
      {/* TODO: implement pagination with API */}

      {/* Announcements List */}
      <div className="flex flex-wrap gap-4 w-full mr-auto mt-5">
        {announcementsData && announcementsData.length > 0 ? (
          announcementsData.map((item) => (
            <AnnouncementCard
              key={item.id}
              title={item.title}
              description={item.description}
              date={item.date}
              time={item.time}
              instructor={item.instructor}
              course={item.course}
              type={item.type}
            />
          ))
        ) : (
          <div className="flex flex-col items-center justify-center w-full py-6 text-gray-500">
            <IoIosNotificationsOff size={40} className="text-gray-400" />
            <p className="text-sm mt-2">No announcements available.</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default Announcements;
