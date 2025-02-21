import React from "react";
import Hero from "../../components/Hero";
import Announcements from "../../components/Announcements";
import Courses from "../../components/CoursesComponent";
import Instructors from "../../components/Instructors";
import Payment from "../../components/Payment";

const Analytics = () => {
  // TODO: Fetch and display announcements from the API
  return (
    <div className="flex flex-col items-center justify-center w-[80%] m-auto">
      <Hero />
      <div className="flex flex-row items-center justify-between w-full  mt-5">
        {/* announcements and courses*/}
        <div className="flex flex-col items-center justify-center w-[70%] ">
          {/* announcements */}
          <div className="flex gap-1 w-full  ">
            <Announcements preview={true}/>
          </div>

          {/* courses */}

          <div className="flex gap-1 w-full">
            
            <Courses preview={true}/>
          </div>
        </div>

       <div className="mb-auto mr-auto  w-[30%] bg-red ">
         {/* instructors and events  */}
         <div className="flex flex-col gap-10 ml-10">
          {/* instructors */}
          <div className="">
            <Instructors/>
          </div>

          {/* Payment */}

          <div className="">
            <Payment/>
          </div>
        </div>
       </div>
      </div>
    </div>
  );
};

export default Analytics;
