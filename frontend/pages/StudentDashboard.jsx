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
import StudentQuizzes from "../components/StudentQuizzes";
import StudentDetailedQuizzes from "../components/StudentDetailedQuizzes";
import QuizStart from "../components/QuizStart";
import QuizSummary from "../components/QuizSummary";
import QuizResults from "../components/QuizResults";
import StudentTasks from "./StudentPages/StudentTasks";
import DetailedStudentTasks from "./StudentPages/DetailedStudentTasks";
import TasksforaCourse from "./StudentPages/TasksforaCourse";
import QuizzesForaCourse from "./StudentPages/QuizzesForaCourse";
import Tools from "./StudentPages/Tools";
import TextSummarization from "./StudentPages/Tools/TextSummarization";
import ImageToText from "./StudentPages/Tools/ImageToText";
import SummarizeVideo from "./StudentPages/Tools/SummarizeVideo";

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
        <Route path="/Quizzes"  element={<StudentQuizzes/>}/>
        <Route path="/Quizzes/all/:courseId"  element={<QuizzesForaCourse/>}/>

        <Route path="/Quizzes/:quizId/course/:courseId"  element={<StudentDetailedQuizzes/>}/>
        <Route path="/Quizzes/:quizId/:courseId/start"  element={<QuizStart/>}/>
        <Route path="/Quizzes/:quizId/:courseId/summary"  element={<QuizSummary/>}/>
        <Route path="/Quizzes/result/:id"  element={<QuizResults/>}/>

        <Route path="/Tasks"  element={<StudentTasks/>}/>
        <Route path="/Tasks/:id"  element={<DetailedStudentTasks/>}/>
        <Route path="/Tasks/all/:courseId"  element={<TasksforaCourse/>}/>
        <Route path="/Tools"  element={<Tools/>}>
        <Route path='/Tools/' element={<TextSummarization/>}/>
            <Route path='/Tools/Text-summarization' element={<TextSummarization/>}/>
            <Route path='/Tools/Image-to-text' element={<ImageToText/>}/>
            <Route path='/Tools/Summarize-video' element={<SummarizeVideo/>}/>

        </Route>





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