import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import AnnouncementCard from './AnnouncementCard';
import { IoIosNotificationsOff } from "react-icons/io";
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';

const Announcements = ({preview}) => {
  const [announcementsData, setannouncementsData] = useState([])
  const axiosPrivate = useAxiosPrivate()

  useEffect(()=>{
    const fetchAnnouncements = async ()=>{
      try {
        const response = await axiosPrivate.get("student/announcements")
        setannouncementsData(response.data)
        console.log("fetched announcements :",response.data);
        
      } catch (error) {
        toast.error(`an error occurred : ${error}`)
      }
    }
    fetchAnnouncements()
  },[])

  return (
    <div className="flex flex-col items-center justify-center w-full">
      <div className="flex flex-row w-[85%] justify-between mr-auto ">
        <h2 className="text-xl font-semibold">Announcements</h2>
        <Link to="/studentDashboard/Announcements" className="text-[#0096C1] sub-text-2">
          See More
        </Link>
      </div>
      {/* TODO: implement pagination with API 
  //TODO: API Call here , or called before and fetched from context
      */}
      {/* in api call , use a state to store the data and then use that state to render the cards , otherwise the fetched data will only be available on parent function */}

      {/* Announcements List */}
      <div className="flex flex-wrap gap-4 w-full mr-auto mt-5">
        {announcementsData && announcementsData.length > 0 &&preview ? (
          announcementsData.slice(0,3).map((announcement) => (
            <AnnouncementCard
              key={announcement.announcementId} title={announcement.title} course={announcement.courseName} date={announcement.announcementDate.toLocaleString()} description={announcement.description} instructor={announcement.instructorName} type={announcement.type}
            />
          ))
        ) :announcementsData && announcementsData.length > 0 &&preview===false ? (
          announcementsData.map((announcement) => (
            <AnnouncementCard
            key={announcement.announcementId} title={announcement.title} course={announcement.courseName} date={announcement.announcementDate.toLocaleString()} description={announcement.description} instructor={announcement.instructorName} type={announcement.type}
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
