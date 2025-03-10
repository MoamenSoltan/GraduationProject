import React from "react";
import { BrowserRouter, Route, Routes } from "react-router";
import SideBar from "../components/SideBar";
import { div } from "motion/react-client";
import Analytics from "./sideBar/Analytics";
import { useStateContext } from "../contexts/ContextProvider";
import Registration from "./sideBar/Registration";
import Courses from "./sideBar/Courses";
import Events from "./sideBar/Events";
import Instructors from "./sideBar/Instructors";
import Help from "./sideBar/Help";
import Payment from "./sideBar/Payment";
import Announcements from "./sideBar/Announcements";

import NavBar from "../components/NavBar";

import { useLocation } from "react-router";
import Profile from "./Profile";

// conditional rendering based on route 


const StudentDashboard = () => {
  const {activeMenu, setActiveMenu} = useStateContext()
  const location = useLocation();
  
  
  // const hiddenRoutes=["/StudentDashboard/profile"]//array so that we can use.includes
  // put full route because profile is nested
  return (
    <div>
        {

          // wrap in fragment
          // !hiddenRoutes.includes(location.pathname) &&
          <>
           {
                (activeMenu)&&
                
                  <div className='md:w-[300px] fixed sidebar bg-white'>
                    <SideBar/>
                  </div>
              
              }
               <div className=' top-0  bg-[#FAFBFB]  navbar w-full'>
                  <NavBar/>
                </div>
          </>
        }


      <Routes>
      <Route path="/"  element={<Analytics/>}/>

        <Route path="/Analytics"  element={<Analytics/>}/>
        {/* : means dynamic route , ? means optional , we could make a dynamic non optional */}
        {/* in the url itself , eg /StudentDashboard/courses?type=default&order=asc , these are query parameters (key and value pairs) */}
        <Route path="/Announcements/:type?"  element={<Announcements/>}/>
        <Route path="/Profile" element = {<Profile/>} />

        <Route path="/Registration"  element={<Registration/>}/>
        <Route path="/Courses/:year?"  element={<Courses/>}/>
        <Route path="/Payment"  element={<Payment/>}/>
        <Route path="/Events"  element={<Events/>}/>
        <Route path="/Instructors"  element={<Instructors/>}/>
        <Route path="/Help"  element={<Help/>}/>

      </Routes>
    </div>
  );
};

export default StudentDashboard;


/**url patameters steps
 * 1- add /:year?  to route
 * 2- useParams
 * 3-
 */