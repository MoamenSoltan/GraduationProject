import React, { useEffect } from "react";
import Hero from "../../components/Hero";
import Announcements from "../../components/Announcements";
import Courses from "../../components/CoursesComponent";
import InstructorsComponent from "../../components/InstructorsComponent";
import Payment from "../../components/Payment";
import { useStateContext } from "../../contexts/ContextProvider";

const Analytics = () => {
  // TODO: Fetch and display announcements from the API
  // if data will be used once , i dont need to fetch it every time the component is rendered , so store in a state , maybe in the registration page , and use it when needed , meaning dont make a get call every time analytics is rendered 
  const {auth}=useStateContext()
  
  useEffect(()=>{
    console.log("authentication state :",auth);
    
  },[])
  return (
    <div className="flex flex-col items-center justify-center w-[80%] m-auto">
      <div className="">
      <Hero />
      </div>
      <div className="flex md:flex-row flex-col items-center justify-between w-full  mt-5">
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

       <div className="mb-auto mr-auto  md:w-[30%] bg-red ">
         {/* instructors and events  */}
         <div className="flex flex-col gap-10 ml-10">
          {/* instructors */}
          <div className="">
            <InstructorsComponent preview={true}/>
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
