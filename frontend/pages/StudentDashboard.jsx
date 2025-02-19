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
import NavBar from "../components/NavBar";




const StudentDashboard = () => {
  const {activeMenu, setActiveMenu} = useStateContext()
  return (
    <div>
         {
                (activeMenu)&&
                
                  <div className='w-[300px] fixed sidebar bg-white'>
                    <SideBar/>
                  </div>
              
              }
               <div className='fixed md:static bg-[#FAFBFB]  navbar w-full'>
                  <NavBar/>
                </div>


      <Routes>
      <Route path="/"  element={<Analytics/>}/>

        <Route path="/Analytics"  element={<Analytics/>}/>
        <Route path="/Registration"  element={<Registration/>}/>
        <Route path="/Courses"  element={<Courses/>}/>
        <Route path="/Payment"  element={<Payment/>}/>
        <Route path="/Events"  element={<Events/>}/>
        <Route path="/Instructors"  element={<Instructors/>}/>
        <Route path="/Help"  element={<Help/>}/>

      </Routes>
    </div>
  );
};

export default StudentDashboard;
