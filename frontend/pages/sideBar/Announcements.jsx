// Announcements.jsx
import React from "react";
import { useParams, useNavigate } from "react-router-dom";
import AnnouncementCard from "../../components/AnnouncementCard";
import { announcementsData } from "../../data/dummy";
import { h2 } from "motion/react-client";


const Announcements = () => {
  const { type } = useParams(); // Get type from URL
  const navigate = useNavigate();
  

  const filteredAnnouncements = type
    ? announcementsData.filter((announcement) => announcement.type === type)
    : announcementsData;

  return (
    <div className="flex flex-col w-[80%] m-auto">
      {!type
      ? <h2 className="mt-10 font-semibold text-lg"> All Announcements</h2>
      :<h2 className="mt-10 font-semibold text-lg">{type&&`${type}s`}</h2>
       }
      {/* Dropdown to Filter by Type */}
      <div className="absolute mt-10 right-[10%] rounded-sm">
        <select
          className="outline-none border-gray-200 border-2 rounded-sm"
          value={type || ""}
          onChange={(e) => navigate(`/studentDashboard/announcements/${e.target.value}`)}
        >
          <option value="">All Announcements</option>
          <option value="Assignment">Assignments</option>
          <option value="Event">Events</option>
          <option value="Exam">Exams</option>
        </select>
      </div>

      {/* Filtered Announcements List */}
      {filteredAnnouncements.length > 0 ? (
        <div className="mt-5 flex flex-row gap-1 flex-wrap">
          {filteredAnnouncements.map((announcement) => (
            <AnnouncementCard key={announcement.id} {...announcement} />
          ))}
        </div>
      ) : (
        <p className="text-gray-500 min-h-[15vh] mt-10">No announcements available.</p>
      )}

      
    </div>
  );
};

export default Announcements;